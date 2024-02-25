package org.microservices.user.controller;

import org.microservices.user.entity.User;
import org.microservices.user.payload.ApiResponse;
import org.microservices.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    public final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //create user
    @PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
    }

    //get single user by user id
    @GetMapping(value = "/getUser/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //get all user
    @GetMapping(value = "/getAllUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUser() {
        List<User> userList = userService.getAllUser();
        return ResponseEntity.ok(userList);
    }

    //delete user
    @DeleteMapping (value = "/deleteUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        String message = "Successfully delete user with id : " + userId;
        ApiResponse response = ApiResponse.builder().message(message).success(true).httpStatus(HttpStatus.OK).build();
        return ResponseEntity.ok(response);
    }

}
