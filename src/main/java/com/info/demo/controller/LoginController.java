package com.info.demo.controller;

import com.info.demo.constant.AppConstants;
import com.info.demo.dto.JwtRequest;
import com.info.demo.dto.JwtResponse;
import com.info.demo.service.login.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/" + AppConstants.API_VERSION)
public class LoginController {

    private LoginService loginService;

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(loginService.login(authenticationRequest));
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<String> logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) new SecurityContextLogoutHandler().logout(request, response, auth);
        return ResponseEntity.ok(AppConstants.LOGOUT_SUCCESS);
    }
}
