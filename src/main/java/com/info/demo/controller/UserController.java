package com.info.demo.controller;

import com.info.demo.constant.AppConstants;
import com.info.demo.dto.Response;
import com.info.demo.model.User;
import com.info.demo.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping(value = "api/" + AppConstants.API_VERSION + "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Response<List<User>>> list() throws Exception {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.USER_FETCH_SUCCESS, userService.getList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<User>> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.USER_FETCH_SUCCESS, userService.findById(id)));
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<Response<User>> save(@RequestBody User user) throws Exception {
        if (userService.findByUsernameEqualsOrEmailEquals(user.getUsername(), user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new Response<>(HttpStatus.BAD_REQUEST.value(),
                    AppConstants.USER_NAME_OR_EMAIL_ALREADY_EXIST, null));
        }
        return ResponseEntity.ok().body(new Response<>(HttpStatus.CREATED.value(),
                AppConstants.USER_REGISTERED_SUCCESS, userService.save(user)));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Response<User>> update(@PathVariable Long id, @RequestBody User user) throws Exception {
        Optional<User> optionalUser = userService.findByUserName(user.getUsername());
        if (optionalUser.isPresent()) {
            if (!Objects.equals(user.getId(), optionalUser.get().getId())) {
                return ResponseEntity.badRequest().body(new Response<>(HttpStatus.BAD_REQUEST.value(),
                        AppConstants.USER_NAME_ALREADY_EXIST + user.getUsername(), null));
            }
        }
        Optional<User> OptionalUserByEmail = userService.findByEmail(user.getUsername());

        if (OptionalUserByEmail.isPresent()) {
            if (user.getId() != OptionalUserByEmail.get().getId()) {
                return ResponseEntity.badRequest().body(new Response<>(HttpStatus.BAD_REQUEST.value(),
                        AppConstants.USER_EMAIL_ALREADY_EXIST + user.getEmail(), null));
            }
        }
        return ResponseEntity.ok().body(new Response<>(HttpStatus.CREATED.value(),
                AppConstants.USER_REGISTERED_SUCCESS, userService.update(id, user)));
    }

    @GetMapping(value = "/organizations/{id}")
    public ResponseEntity<Response<List<User>>> findByOrganizationId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.USER_FETCH_SUCCESS, userService.findByOrganization(id)));
    }

}
