package com.info.demo.service.impl;

import com.info.demo.constant.AppConstants;
import com.info.demo.model.Organization;
import com.info.demo.model.User;
import com.info.demo.repository.UserRepository;
import com.info.demo.service.email.EmailService;
import com.info.demo.service.organization.OrganizationService;
import com.info.demo.service.user.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrganizationService organizationService;
    private final EmailService emailService;
    private KafkaTemplate<String, User> userKafkaTemplate;


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, OrganizationService organizationService, EmailService emailService, KafkaTemplate<String, User> userKafkaTemplate) {
        this.userRepository = userRepository;
        this.organizationService = organizationService;
        this.emailService = emailService;
        this.userKafkaTemplate = userKafkaTemplate;
    }


    @Override
    public List<User> getList() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) throws Exception {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            user.setPassword(null);
            userKafkaTemplate.send(AppConstants.NOTIFY_USER_TOPIC, user);
            emailService.sendSimpleMessage(user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not exist with the id: " + id));
    }

    @Override
    public List<User> findByOrganization(Long organizationId) throws Exception {
        Organization organization = organizationService.findById(organizationId);
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsernameEqualsOrEmailEquals(String username, String email) {
        return userRepository.findByUsernameEqualsOrEmailEquals(username, email);
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User update(Long id, User user) throws Exception {
        try {
            User existUser = findById(id);
            existUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user = userRepository.save(existUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
