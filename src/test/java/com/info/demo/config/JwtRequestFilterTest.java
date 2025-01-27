package com.info.demo.config;

import com.info.demo.config.secuirty.JwtRequestFilter;
import com.info.demo.config.secuirty.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;

import com.info.demo.config.secuirty.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtRequestFilterTest {

    @Mock
    private JwtTokenUtil jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    private MockHttpServletRequest request = new MockHttpServletRequest();
    private MockHttpServletResponse response = new MockHttpServletResponse();
    private FilterChain filterChain = mock(FilterChain.class);

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
    void testValidToken() throws Exception {
        UserDetails user = new org.springframework.security.core.userdetails.User(
                "testuser", "password", new ArrayList<>()
        );

        when(jwtUtils.getUsernameFromToken("valid-token")).thenReturn("testuser");
        when(jwtUtils.validateToken("valid-token", userDetails)).thenReturn(true);
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(user);

        request.addHeader("Authorization", "Bearer valid-token");
        jwtRequestFilter.doFilterInternal(request, response, filterChain);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("testuser", SecurityContextHolder.getContext().getAuthentication().getName());
    }

//    @Test
    void testInvalidToken() throws Exception {
        when(jwtUtils.validateToken("invalid-token", null)).thenReturn(false);
        request.addHeader("Authorization", "Bearer invalid-token");
        jwtRequestFilter.doFilterInternal(request, response, filterChain);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }


}
