package org.microservices.organization.controller;

import org.microservices.organization.entity.Organization;
import org.microservices.organization.payload.ApiResponse;
import org.microservices.organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    //create org
    @PostMapping(value = "/createOrg", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Organization> createOrg(@RequestBody Organization organization) {
        Organization cratedOrg = organizationService.createOrg(organization);
        return ResponseEntity.status(HttpStatus.CREATED).body(cratedOrg);
    }

    //get Org by id
    @GetMapping(value = "/getOrg/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Organization>> getOrgById(@PathVariable String id) {
        Optional<Organization> org = organizationService.getOrg(id);
        return ResponseEntity.ok(org);
    }


    @GetMapping(value = "/getAllOrg", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Organization>> getAllOrg() {
        List<Organization> allOrg = organizationService.getAllOrg();
        return ResponseEntity.ok(allOrg);
    }

    @DeleteMapping(value = "/deleteOrg/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteOrg(@PathVariable String id) {
        organizationService.deleteOrg(id);
        String message = "Successfully delete organization with id : " + id;
        ApiResponse response = ApiResponse.builder().message(message).success(true).httpStatus(HttpStatus.OK).build();
        return ResponseEntity.ok(response);

    }
}