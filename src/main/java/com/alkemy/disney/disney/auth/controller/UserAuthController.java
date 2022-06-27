package com.alkemy.disney.disney.auth.controller;

import com.alkemy.disney.disney.auth.dto.AuthenticationRequest;
import com.alkemy.disney.disney.auth.dto.AuthenticationResponse;
import com.alkemy.disney.disney.auth.dto.UserDTO;
import com.alkemy.disney.disney.auth.service.JwtUtils;
import com.alkemy.disney.disney.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    private UserDetailsCustomService userDetailsService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtTokenUtil;
    @Autowired
    public UserAuthController(
            UserDetailsCustomService userDetailsService,
            AuthenticationManager authenticationManager,
            JwtUtils jwtTokenUtil){
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @PostMapping("/singup")
    public ResponseEntity<AuthenticationResponse> singUp(@Valid @RequestBody UserDTO user) throws Exception{
        this.userDetailsService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/singin")
    public ResponseEntity<AuthenticationResponse> singIn(@RequestBody AuthenticationRequest authRequest) throws Exception{
        UserDetails userDetails;
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            userDetails = (UserDetails) auth.getPrincipal();
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or Password", e);
        }
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
