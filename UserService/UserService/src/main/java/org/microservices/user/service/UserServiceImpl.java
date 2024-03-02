package org.microservices.user.service;

import lombok.extern.slf4j.Slf4j;
import org.microservices.user.entity.Organization;
import org.microservices.user.entity.Ratings;
import org.microservices.user.entity.User;
import org.microservices.user.exceptions.ResourceNotFoundException;
import org.microservices.user.external.OrganizationService;
import org.microservices.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    private final OrganizationService organizationService;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate, UserRepository userRepository, OrganizationService organizationService) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.organizationService = organizationService;
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

        Ratings[] ratings = restTemplate.getForObject("http://RATING-SERVICE/rating/getRatingsByUserId/"+ user.getUserId(), Ratings[].class);
        assert ratings != null;
        List<Ratings> listOfRatings = Arrays.stream(ratings).toList();
        log.info("{} ", listOfRatings);

        List<Ratings> ratingsList =  listOfRatings.stream().map(rating -> {

//            ResponseEntity<Organization> response = restTemplate.
//                    getForEntity("http://ORGANIZATION-SERVICE/organization/getOrg/"+rating.getOrganizationId(), Organization.class);
//            Organization responseBody = response.getBody();
            Organization responseBody = organizationService.getOrganization(rating.getOrganizationId());
//            log.info("response : {} ", response.getStatusCode());
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
