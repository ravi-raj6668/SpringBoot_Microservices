package org.microservices.rating.controller;

import org.microservices.rating.entity.Rating;
import org.microservices.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping(value = "/generateRating", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rating> generateRating(@RequestBody Rating rating) {
        Rating generateRating = ratingService.generateRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(generateRating);
    }

    @GetMapping(value = "/getAllRating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Rating>> getRating() {
        List<Rating> allRatings = ratingService.getAllRatings();
        return ResponseEntity.ok(allRatings);
    }


    @GetMapping(value = "/getRatingsByUserId/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Rating>> getRatingByUserId(@PathVariable String userId) {
        List<Rating> ratingByUserId = ratingService.getRatingByUserId(userId);
        return ResponseEntity.ok(ratingByUserId);
    }

    @GetMapping(value = "/getRatingByOrgId/{orgId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Rating>> getRatingByOrgId(@PathVariable String orgId) {
        List<Rating> ratingByOrganizationId = ratingService.getRatingByOrganizationId(orgId);
        return ResponseEntity.ok(ratingByOrganizationId);
    }
}
