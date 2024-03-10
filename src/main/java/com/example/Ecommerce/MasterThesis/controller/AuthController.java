package com.example.Ecommerce.MasterThesis.controller;

import com.example.Ecommerce.MasterThesis.dto.AuthenticationRequest;
import com.example.Ecommerce.MasterThesis.dto.SignupRequest;
import com.example.Ecommerce.MasterThesis.dto.UserDTO;
import com.example.Ecommerce.MasterThesis.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthController {


    @Autowired
    private AuthService authenticationService;


    @PostMapping("/authenticate")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            String response = authenticationService.authenticateUser(authenticationRequest);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException | JSONException e) {
            HttpStatus status = e instanceof UsernameNotFoundException ? HttpStatus.NOT_FOUND : HttpStatus.UNAUTHORIZED;
            return ResponseEntity.status(status).body(e.getMessage());
        }
    }


    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        if (authenticationService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User already exist", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO userDTO = authenticationService.createUser(signupRequest);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
