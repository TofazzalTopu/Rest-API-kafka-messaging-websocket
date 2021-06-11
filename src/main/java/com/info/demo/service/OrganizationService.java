package com.info.demo.service;

import com.info.demo.model.Organization;

import java.util.List;

public interface OrganizationService {
    Organization save(Organization organization)  throws Exception;
    Organization findById(Long id) throws Exception;
    List<Organization> getList() throws Exception;
}
