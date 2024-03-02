package org.microservices.user;

import org.junit.jupiter.api.Test;
import org.microservices.user.entity.Ratings;
import org.microservices.user.external.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private RatingService ratingService;
	@Test
	void createRatings(){
		Ratings ratings = Ratings.builder().rating(10).userId("").organizationId("").feedback("this is created to test feign client").build();
		Ratings savedRating = ratingService.createRating(ratings);
		System.out.println("New Rating created : " + savedRating);
	}

}
