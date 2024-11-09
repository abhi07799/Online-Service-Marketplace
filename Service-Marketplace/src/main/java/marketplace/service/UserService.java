package marketplace.service;

import lombok.RequiredArgsConstructor;
import marketplace.dto.request.UserRequestDto;
import marketplace.dto.response.UserResponseDto;
import marketplace.exception.CustomException;
import marketplace.exception.ResourceAlreadyExistException;
import marketplace.exception.ResourceNotFoundException;
import marketplace.model.UserModel;
import marketplace.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    public UserResponseDto addUser(UserRequestDto userRequestDto)
    {
        try
        {
            UserModel userModel = mapper.map(userRequestDto, UserModel.class);

            Optional<UserModel> optionalUserModel = userRepository.findByUserMail(userModel.getUserMail());
            if (optionalUserModel.isPresent())
            {
                throw new ResourceAlreadyExistException();
            }
            
            UserModel savedUser = userRepository.save(userModel);
            return mapper.map(savedUser, UserResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceAlreadyExistException)
            {
                throw new ResourceAlreadyExistException("User already exists with this mail",ex);
            }
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public UserResponseDto getUserById(long userId)
    {
        try
        {
            Optional<UserModel> optionalUserModel = userRepository.findById(userId);
            if (optionalUserModel.isEmpty())
            {
                throw new ResourceNotFoundException();
            }
            return mapper.map(optionalUserModel.get(), UserResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                throw new ResourceNotFoundException("User does not exist with this id: "+userId,ex);
            }
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public List<UserResponseDto> getAllUsers()
    {
        try
        {
            List<UserModel> userModels = userRepository.findAll();

            if (userModels.isEmpty())
            {
                throw new ResourceNotFoundException();                
            }
            List<UserResponseDto> dtoList = userModels.stream().map(user -> mapper.map(user, UserResponseDto.class)).toList();
            return dtoList;
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                throw new ResourceNotFoundException("No user found",ex);
            }
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public UserResponseDto updateUser(long userId, UserRequestDto userRequestDto)
    {
        try
        {
            Optional<UserModel> optionalUserModel = userRepository.findById(userId);

            UserModel userModel = getUserModel(optionalUserModel, userRequestDto);
            UserModel savedUser = userRepository.save(userModel);

            return mapper.map(savedUser, UserResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                throw new ResourceNotFoundException("User does not exist with this id: "+userId,ex);
            }
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    private UserModel getUserModel(Optional<UserModel> optionalUserModel, UserRequestDto userRequestDto)
    {
        if (optionalUserModel.isEmpty())
        {
            throw new ResourceNotFoundException();
        }

        UserModel userModel = optionalUserModel.get();

        // Check null first, then check for emptiness
        if (userRequestDto.getFullName() != null && !userRequestDto.getFullName().isEmpty())
        {
            userModel.setFullName(userRequestDto.getFullName());
        }
        if (userRequestDto.getUserMail() != null && !userRequestDto.getUserMail().isEmpty())
        {
            userModel.setUserMail(userRequestDto.getUserMail());
        }
        if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isEmpty())
        {
            userModel.setPassword(userRequestDto.getPassword());
        }
        if (userRequestDto.getUserMobile() != null && !userRequestDto.getUserMobile().isEmpty())
        {
            userModel.setUserMobile(userRequestDto.getUserMobile());
        }
        if (userRequestDto.getUserAddress() != null && !userRequestDto.getUserAddress().isEmpty())
        {
            userModel.setUserAddress(userRequestDto.getUserAddress());
        }
        return userModel;
    }
}
