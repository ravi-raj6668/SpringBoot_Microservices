package org.microservices.user.external;

import org.microservices.user.entity.Ratings;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {


    //post
    @PostMapping("/rating/generateRating")
    Ratings createRating(Ratings ratingValue);
}
