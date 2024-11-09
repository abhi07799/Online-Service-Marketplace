package marketplace.service;

import lombok.RequiredArgsConstructor;
import marketplace.dto.request.UserRequestDto;
import marketplace.dto.response.UserResponseDto;
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
            UserModel savedUser = userRepository.save(userModel);

            return mapper.map(savedUser, UserResponseDto.class);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public UserResponseDto getUserById(long userId)
    {
        try
        {
            Optional<UserModel> optionalUserModel = userRepository.findById(userId);
            if (optionalUserModel.isPresent())
            {
                return mapper.map(optionalUserModel.get(), UserResponseDto.class);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public List<UserResponseDto> getAllUsers()
    {
        try
        {
            List<UserModel> userModels = userRepository.findAll();

            if (!userModels.isEmpty())
            {
                List<UserResponseDto> dtoList = userModels.stream().map(user -> mapper.map(user, UserResponseDto.class)).toList();
                return dtoList;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
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
            ex.printStackTrace();
        }
        return null;
    }

    private UserModel getUserModel(Optional<UserModel> optionalUserModel, UserRequestDto userRequestDto)
    {
        if (optionalUserModel.isEmpty())
        {
            return null;
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
