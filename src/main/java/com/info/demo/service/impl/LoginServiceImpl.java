package com.info.demo.service.impl;

import com.info.demo.config.secuirty.JwtTokenUtil;
import com.info.demo.config.secuirty.UserDetailsServiceImpl;
import com.info.demo.constant.AppConstants;
import com.info.demo.dto.JwtRequest;
import com.info.demo.dto.JwtResponse;
import com.info.demo.model.User;
import com.info.demo.service.login.LoginService;
import com.info.demo.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;
    private KafkaTemplate<String, String> kafkaTemplate;
    private AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public JwtResponse login(JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        Optional<User> user = userService.findByUserName(authenticationRequest.getUsername());
        user.get().setPassword(null);
        kafkaTemplate.send(AppConstants.MESSAGE_TOPIC, AppConstants.LOGIN_SUCCESS);
        return new JwtResponse(user.get(), token);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
