package marketplace.controller;

import marketplace.dto.request.UserRequestDto;
import marketplace.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class UserController
{
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("addUser")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto userRequestDto)
    {
        log.info("Request to create a user");
        return new ResponseEntity<>(userService.addUser(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("getAllUsers")
    public ResponseEntity<?> getAllUsers()
    {
        log.info("Request to get all users");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("getUserById/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId)
    {
        log.info("Request to get user by id: {}",userId);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PutMapping("updateUser/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserRequestDto userRequestDto)
    {
        log.info("Request to update user by id: {}",userId);
        return new ResponseEntity<>(userService.updateUser(userId, userRequestDto), HttpStatus.OK);
    }
}
