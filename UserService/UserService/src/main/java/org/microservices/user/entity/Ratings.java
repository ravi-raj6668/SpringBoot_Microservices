package org.microservices.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ratings {
    private String ratingId;
    private String userId;
    private String organizationId;
    private int rating;
    private String feedback;
    private Organization organization;
}
