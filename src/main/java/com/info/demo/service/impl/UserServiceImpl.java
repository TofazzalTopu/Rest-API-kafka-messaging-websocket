package com.info.demo.service.impl;

import com.info.demo.model.Organization;
import com.info.demo.model.User;
import com.info.demo.repository.UserRepository;
import com.info.demo.service.EmailService;
import com.info.demo.service.OrganizationService;
import com.info.demo.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrganizationService organizationService;
    private final EmailService emailService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, OrganizationService organizationService, EmailService emailService) {
        this.userRepository = userRepository;
        this.organizationService = organizationService;
        this.emailService = emailService;
    }


    @Override
    public List<User> getList() throws Exception{
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @Override
    public User save(User user)  throws Exception{
        try {
            Organization organization = Organization.builder()
                    .email(user.getEmail()).username(user.getUsername()).build();
            organizationService.save(organization);

            user.setOrganization(organization);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            user.setPassword(null);

            emailService.sendSimpleMessage(user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not exist with the id: "+ id));
    }

    @Override
    public List<User> findByOrganization(Long organizationId) throws Exception {
        Organization organization = organizationService.findById(organizationId);
        return userRepository.findByOrganization(organization);
    }

    @Override
    public Optional<User> findByEmail(String email) throws Exception {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUserName(String username) throws Exception {
        return userRepository.findByUsername(username);
    }

    @Override
    public User update(Long id, User user) throws Exception {
        try {
            User existUser = userRepository.getById(id);
            Organization organization = organizationService.findById(user.getOrganization().getId());

            existUser.setOrganization(organization);
            existUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user = userRepository.save(existUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
