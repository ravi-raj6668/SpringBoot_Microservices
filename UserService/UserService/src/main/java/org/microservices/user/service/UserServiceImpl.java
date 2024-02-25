package org.microservices.user.service;

import lombok.extern.slf4j.Slf4j;
import org.microservices.user.entity.Organization;
import org.microservices.user.entity.Ratings;
import org.microservices.user.entity.User;
import org.microservices.user.exceptions.ResourceNotFoundException;
import org.microservices.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


//    private String url = http://localhost:8083/api/v1/rating/getRatingsByUserId/
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {

        //generating unique user id
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on system : " + userId));
        //fetch rating data from rating service

        Ratings[] ratings = restTemplate.getForObject("http://RATING-SERVICE/api/v1/rating/getRatingsByUserId/"+ user.getUserId(), Ratings[].class);
        assert ratings != null;
        List<Ratings> listOfRatings = Arrays.stream(ratings).toList();
        log.info("{} ", listOfRatings);

        List<Ratings> ratingsList =  listOfRatings.stream().map(rating -> {
            ResponseEntity<Organization> response = restTemplate.
                    getForEntity("http://ORGANIZATION-SERVICE/api/v1/organization/getOrg/"+rating.getOrganizationId(), Organization.class);
            Organization responseBody = response.getBody();
            log.info("response : {} ", response.getStatusCode());
            rating.setOrganization(responseBody);
            return rating;
        }).toList();
        user.setRatings(ratingsList);
        return user;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with the id : " + userId));
        userRepository.delete(user);
    }
}
