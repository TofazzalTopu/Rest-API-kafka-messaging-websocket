package com.info.demo.service.user;


import com.info.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getList()  throws Exception;
    User save(User user)  throws Exception;
    User findById(Long id)  throws Exception;
    List<User> findByOrganization(Long organizationId) throws Exception;
    Optional<User> findByEmail(String email)  throws Exception;
    Optional<User> findByUsernameEqualsOrEmailEquals(String username, String email);

    Optional<User> findByUserName(String username) throws Exception;

    User update(Long id, User user) throws Exception;
}
