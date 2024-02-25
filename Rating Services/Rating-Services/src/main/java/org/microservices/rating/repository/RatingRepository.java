package org.microservices.rating.repository;

import org.microservices.rating.entity.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableMongoRepositories
@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    //custom finder method
    List<Rating> findByUserId(String userId);

    List<Rating> findByOrganizationId(String orgId);
}
