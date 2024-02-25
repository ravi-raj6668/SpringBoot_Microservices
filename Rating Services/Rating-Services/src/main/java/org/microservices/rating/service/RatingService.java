package org.microservices.rating.service;

import org.microservices.rating.entity.Rating;

import java.util.List;

public interface RatingService {

    //    create rating
    public Rating generateRating(Rating rating);


    //    get all rating
    public List<Rating> getAllRatings();

    //    get all by user id
    public List<Rating> getRatingByUserId(String userId);


    //    get all by organization
    public List<Rating> getRatingByOrganizationId(String orgId);
}
