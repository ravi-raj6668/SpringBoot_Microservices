package org.microservices.user.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/user")
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

    int retryCount = 1;

    //get single user by user id
    @GetMapping(value = "/getUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @CircuitBreaker(name = "ratingOrgBreaker", fallbackMethod = "ratingOrgFallback") // applying circuit breaker concept for fault tolerance
//    @Retry(name = "ratingOrgBreaker", fallbackMethod = "ratingOrgFallback")// applying retry concept for fault tolerance
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingOrgFallback") //implementing rate limiter concept
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        log.info("Get Single user by id {}: ", userId);
        log.info("Retry Count : {} ", retryCount);
        retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }


    //create fallback  method for circuit breaker or retry terminology  to implement fault tolerance
    public ResponseEntity<User> ratingOrgFallback(String userId, Exception e) {
        log.info("Fall back is executed service is down {}: ", e.getMessage());
        User dummyUser = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy User")
                .about("This is dummy user")
                .userId("123311")
                .build();
        return new ResponseEntity<>(dummyUser, HttpStatus.OK);
    }

    //get all user
    @GetMapping(value = "/getAllUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUser() {
        List<User> userList = userService.getAllUser();
        return ResponseEntity.ok(userList);
    }

    //delete user
    @DeleteMapping(value = "/deleteUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        String message = "Successfully delete user with id : " + userId;
        ApiResponse response = ApiResponse.builder().message(message).success(true).httpStatus(HttpStatus.OK).build();
        return ResponseEntity.ok(response);
    }

}
