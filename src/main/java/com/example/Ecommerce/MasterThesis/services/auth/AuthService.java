package com.example.Ecommerce.MasterThesis.services.auth;

import com.example.Ecommerce.MasterThesis.dto.AuthenticationRequest;
import com.example.Ecommerce.MasterThesis.dto.SignupRequest;
import com.example.Ecommerce.MasterThesis.dto.UserDTO;
import org.json.JSONException;

public interface AuthService {
    public UserDTO createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);

    String authenticateUser(AuthenticationRequest authenticationRequest) throws JSONException;
}
