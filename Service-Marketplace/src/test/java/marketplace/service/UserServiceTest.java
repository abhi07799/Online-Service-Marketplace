package marketplace.service;

import marketplace.dto.request.UserRequestDto;
import marketplace.dto.response.UserResponseDto;
import marketplace.exception.CustomException;
import marketplace.exception.ResourceAlreadyExistException;
import marketplace.exception.ResourceNotFoundException;
import marketplace.model.UserModel;
import marketplace.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest
{
    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private UserService userService;

    private UserModel userModel;
    private UserModel savedUserModel;
    private UserRequestDto userRequestDto;
    private UserResponseDto userResponseDto;
    private List<UserModel> userModels;
    private List<UserResponseDto> userResponseDtoList;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);

        userRequestDto = new UserRequestDto().builder().fullName("john").userMail("abc@test.com").password("124").userAddress("india").role("user").build();
        userModel = new UserModel().builder().id(1).fullName("john").userMail("abc@test.com").password("124").userAddress("india").role("user").AccountCreatedOn(LocalDateTime.now()).build();
        savedUserModel = new UserModel().builder().id(1).fullName("john").userMail("abc@test.com").password("124").userAddress("india").role("user").AccountCreatedOn(LocalDateTime.now()).build();
        userResponseDto = new UserResponseDto().builder().id(1).fullName("john").userMail("abc@test.com").password("124").userAddress("india").role("user").AccountCreatedOn(LocalDateTime.now()).build();
        userModels = new ArrayList<>();
        userModels.add(userModel);
        userResponseDtoList = new ArrayList<>();
        userResponseDtoList.add(userResponseDto);
    }

    @Test
    void testAddUser_Success()
    {
        // Arrange
        when(mapper.map(userRequestDto, UserModel.class)).thenReturn(userModel);
        when(userRepository.findByUserMail(userModel.getUserMail())).thenReturn(Optional.empty());
        when(userRepository.save(userModel)).thenReturn(savedUserModel);
        when(mapper.map(savedUserModel, UserResponseDto.class)).thenReturn(userResponseDto);

        // Act
        UserResponseDto result = userService.addUser(userRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals("abc@test.com", result.getUserMail());
        assertEquals("john", result.getFullName());

        verify(userRepository, atLeastOnce()/*times(1)*/).save(any());
//        assertEquals("test@example.com", );
    }

    @Test
    void testAddUser_WhenUserAlreadyExists()
    {
        // Arrange
        when(mapper.map(userRequestDto, UserModel.class)).thenReturn(userModel);
        when(userRepository.findByUserMail(userModel.getUserMail())).thenReturn(Optional.of(userModel));

        // Act & Assert
        ResourceAlreadyExistException exception = assertThrows(ResourceAlreadyExistException.class, () ->
        {
            userService.addUser(userRequestDto);
        });

        assertEquals("User already exists with this mail", exception.getMessage());
    }

    @Test
    void testAddUser_WhenExceptionOccurs()
    {
        // Arrange
        when(mapper.map(userRequestDto, UserModel.class)).thenReturn(userModel);
        when(userRepository.findByUserMail(userModel.getUserMail())).thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () ->
        {
            userService.addUser(userRequestDto);
        });

        assertEquals("Something went wrong!!", exception.getMessage());
    }

    @Test
    void testGetUserById_UserFound()
    {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(userModel));
        when(mapper.map(userModel, UserResponseDto.class)).thenReturn(userResponseDto);

        UserResponseDto result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals("abc@test.com", result.getUserMail());
        assertEquals(userId, result.getId());
        verify(userRepository, atLeastOnce()).findById(userId);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        // Arrange
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(userId);
        });
        assertEquals("User does not exist with this id: " + userId, exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    public void testGetUserById_ExceptionThrown() {
        // Arrange
        long userId = 1L;
        when(userRepository.findById(userId)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.getUserById(userId);
        });
        assertEquals("Something went wrong!!", exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    public void testGetAllUsers_Success() {
        // Arrange
        when(userRepository.findAll()).thenReturn(userModels);
        when(mapper.map(userModel, UserResponseDto.class)).thenReturn(userResponseDto);

        // Act
        List<UserResponseDto> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userResponseDto.getId(), result.get(0).getId());
        verify(userRepository).findAll();
        verify(mapper).map(userModel, UserResponseDto.class);
    }

    @Test
    public void testGetAllUsers_NoUsersFound() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getAllUsers();
        });
        assertEquals("No user found", exception.getMessage());
        verify(userRepository).findAll();
    }

    @Test
    public void testGetAllUsers_ExceptionThrown() {
        // Arrange
        when(userRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.getAllUsers();
        });
        assertEquals("Something went wrong!!", exception.getMessage());
        verify(userRepository).findAll();
    }
}