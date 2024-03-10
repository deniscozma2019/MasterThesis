package com.example.Ecommerce.MasterThesis.services.auth;

import com.example.Ecommerce.MasterThesis.dto.AuthenticationRequest;
import com.example.Ecommerce.MasterThesis.dto.SignupRequest;
import com.example.Ecommerce.MasterThesis.dto.UserDTO;
import org.json.JSONException;
import org.springframework.context.annotation.Bean;

public interface AuthService {
     UserDTO createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);

    String authenticateUser(AuthenticationRequest authenticationRequest) throws JSONException;
}
