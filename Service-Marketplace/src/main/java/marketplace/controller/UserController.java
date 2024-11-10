package marketplace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import marketplace.dto.request.UserRequestDto;
import marketplace.dto.response.UserResponseDto;
import marketplace.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "User", description = "User management APIs")
@RestController
@RequestMapping("api/v1/")
public class UserController
{
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new user", description = "This endpoint accepts user request dto and returns a user response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                ),
                @ApiResponse(responseCode = "400",description = "Invalid input data",
                        content = @Content(mediaType = "application/json")
                ),
                @ApiResponse(responseCode = "500",description = "Internal Server Error",
                        content = @Content(mediaType = "application/json")
                )
            })
    @PostMapping("addUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDto userRequestDto)
    {
        log.info("Request to create a user");
        return new ResponseEntity<>(userService.addUser(userRequestDto), HttpStatus.CREATED);
    }


    @Operation(summary = "Fetch all users", description = "This endpoint returns a list of user response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "All Users fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "404",description = "User Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("getAllUsers")
    public ResponseEntity<?> getAllUsers()
    {
        log.info("Request to get all users");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


    @Operation(summary = "Fetch a user by user id", description = "This endpoint accepts user id and returns a user response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "User fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404",description = "User Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("getUserById/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId)
    {
        log.info("Request to get user by id: {}",userId);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }


    @Operation(summary = "Update user details", description = "This endpoint accepts user request dto and userId and returns an updated user response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404",description = "User Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @PutMapping("updateUser/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserRequestDto userRequestDto)
    {
        log.info("Request to update user by id: {}",userId);
        return new ResponseEntity<>(userService.updateUser(userId, userRequestDto), HttpStatus.OK);
    }
}
