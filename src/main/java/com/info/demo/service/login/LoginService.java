package com.info.demo.service.login;

import com.info.demo.dto.JwtRequest;
import com.info.demo.dto.JwtResponse;

public interface LoginService {

    JwtResponse login(JwtRequest authenticationRequest) throws Exception;
}
