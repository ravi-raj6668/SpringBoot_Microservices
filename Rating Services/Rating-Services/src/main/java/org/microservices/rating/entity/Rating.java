package org.microservices.rating.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rating")
public class Rating {
    @Id
    private String ratingId;
    private String userId;
    private String organizationId;
    private int ratings;
    private String feedback;
}
