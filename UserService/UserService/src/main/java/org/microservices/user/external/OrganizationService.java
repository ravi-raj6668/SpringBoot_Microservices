package org.microservices.user.external;

import org.microservices.user.entity.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ORGANIZATION-SERVICE")
public interface OrganizationService {

    @GetMapping("/organization/getOrg/{id}")
    Organization getOrganization(@PathVariable String id);
}
