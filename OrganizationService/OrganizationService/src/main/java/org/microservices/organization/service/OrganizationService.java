package org.microservices.organization.service;

import org.microservices.organization.entity.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {

    //create org
    public Organization createOrg(Organization organization);

    //getOrg
    public Optional<Organization> getOrg(String id);

    //getAllOrg
    public List<Organization> getAllOrg();

    //delete org
    public void deleteOrg(String id);


}
