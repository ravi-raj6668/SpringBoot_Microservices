package org.microservices.user.entity;

import lombok.Data;

@Data
public class Organization {
    private String id;
    private String name;
    private String location;
    private String about;
}
