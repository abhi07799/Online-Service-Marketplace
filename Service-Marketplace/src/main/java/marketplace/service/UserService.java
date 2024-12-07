package marketplace.service;

import lombok.RequiredArgsConstructor;
import marketplace.dto.request.UserRequestDto;
import marketplace.dto.response.UserResponseDto;
import marketplace.exception.CustomException;
import marketplace.exception.ResourceAlreadyExistException;
import marketplace.exception.ResourceNotFoundException;
import marketplace.model.Role;
import marketplace.model.UserModel;
import marketplace.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDto addUser(UserRequestDto userRequestDto)
    {
        try
        {
            log.info("Adding new user");
            UserModel userModel = mapper.map(userRequestDto, UserModel.class);

            Optional<UserModel> optionalUserModel = userRepository.findByUserMail(userModel.getUserMail());
            if (optionalUserModel.isPresent())
            {
                throw new ResourceAlreadyExistException();
            }

            userModel.setRole(Role.valueOf(userRequestDto.getRole().toUpperCase()));
            userModel.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            UserModel savedUser = userRepository.save(userModel);
            log.info("User added successfully");
            return mapper.map(savedUser, UserResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceAlreadyExistException)
            {
                log.error("User already exists with this mail",ex);
                throw new ResourceAlreadyExistException("User already exists with this mail",ex);
            }
            log.error("Error while adding user",ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public UserResponseDto getUserById(long userId)
    {
        try
        {
            log.info("Getting user by id: {}",userId);
            Optional<UserModel> optionalUserModel = userRepository.findById(userId);
            if (optionalUserModel.isEmpty())
            {
                throw new ResourceNotFoundException();
            }
            log.info("User retrieved successfully");
            return mapper.map(optionalUserModel.get(), UserResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("User does not exist with this id",ex);
                throw new ResourceNotFoundException("User does not exist with this id: "+userId,ex);
            }
            log.error("Error while getting user by id: {}",userId,ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public List<UserResponseDto> getAllUsers()
    {
        try
        {
            log.info("Getting all users");
            List<UserModel> userModels = userRepository.findAll();

            if (userModels.isEmpty())
            {
                throw new ResourceNotFoundException();                
            }
            List<UserResponseDto> dtoList = userModels.stream().map(user -> mapper.map(user, UserResponseDto.class)).toList();
            log.info("All users retrieved successfully");
            return dtoList;
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("No user found",ex);
                throw new ResourceNotFoundException("No user found",ex);
            }
            log.error("Error while getting all users",ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public UserResponseDto updateUser(long userId, UserRequestDto userRequestDto)
    {
        try
        {
            log.info("Updating user by id: {}",userId);
            Optional<UserModel> optionalUserModel = userRepository.findById(userId);

            UserModel userModel = getUserModel(optionalUserModel, userRequestDto);
            UserModel savedUser = userRepository.save(userModel);

            log.info("User updated successfully");
            return mapper.map(savedUser, UserResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("User does not exist with this id",ex);
                throw new ResourceNotFoundException("User does not exist with this id: "+userId,ex);
            }
            log.error("Error while updating user",ex);
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
        if (userRequestDto.getUserAddress() != null && !userRequestDto.getUserAddress().isEmpty())
        {
            userModel.setUserAddress(userRequestDto.getUserAddress());
        }
        return userModel;
    }
}
