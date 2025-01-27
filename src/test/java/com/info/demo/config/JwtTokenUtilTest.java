package com.info.demo.config;

import com.info.demo.config.secuirty.JwtTokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenUtilTest {

    @InjectMocks
    private JwtTokenUtil jwtUtils;

    private UserDetails userDetails;

    @BeforeEach
    void setup() {
        userDetails = new org.springframework.security.core.userdetails.User(
                "testuser",
                "password",
                new ArrayList<>()
        );
    }

    @Test
    void testGenerateAndValidateToken() {
        String token = jwtUtils.generateToken(userDetails);
        assertNotNull(token);
        assertTrue(jwtUtils.validateToken(token, userDetails));
        assertEquals("testuser", jwtUtils.getUsernameFromToken(token));
    }

//    @Test
    void testExpiredToken() {
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000))
                .setExpiration(new Date(System.currentTimeMillis() - 500))
                .signWith(SignatureAlgorithm.HS512, "your-secret-key-keep-it-safe-and-long-enough")
                .compact();

        assertFalse(jwtUtils.validateToken(token, userDetails));
    }
}
