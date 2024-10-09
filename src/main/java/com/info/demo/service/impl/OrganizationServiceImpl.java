package com.info.demo.service.impl;

import com.info.demo.model.Organization;
import com.info.demo.repository.OrganizationRepository;
import com.info.demo.service.organization.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Organization save(Organization organization) throws Exception {
        return organizationRepository.save(organization);
    }

    @Override
    public Organization findById(Long id) {
        return organizationRepository.findById(id).orElseThrow(()-> new NullPointerException("Organization not found"));
    }

    @Override
    public List<Organization> getList()  throws Exception{
        return organizationRepository.findAll();
    }
}
