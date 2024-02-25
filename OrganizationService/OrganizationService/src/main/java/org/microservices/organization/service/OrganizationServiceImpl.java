package org.microservices.organization.service;

import org.microservices.organization.entity.Organization;
import org.microservices.organization.exceptions.ResourceNotFoundException;
import org.microservices.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Organization createOrg(Organization organization) {
        String id = UUID.randomUUID().toString();
        organization.setId(id);
        return organizationRepository.save(organization);
    }

    @Override
    public Optional<Organization> getOrg(String id) {
        return Optional.ofNullable(organizationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Organization not found with the id : " + id)));
    }

    @Override
    public List<Organization> getAllOrg() {
        return organizationRepository.findAll();
    }

    @Override
    public void deleteOrg(String id) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Organization not found with the id : " + id));
        organizationRepository.delete(organization);
    }
}
