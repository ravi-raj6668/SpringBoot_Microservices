package org.microservices.user.entity;

import lombok.Data;

@Data
public class Ratings {
    private String ratingId;
    private String userId;
    private String organizationId;
    private int rating;
    private String feedback;
    private Organization organization;
}
