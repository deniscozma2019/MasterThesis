package com.example.Ecommerce.MasterThesis.services.auth;

import com.example.Ecommerce.MasterThesis.dto.SignupRequest;
import com.example.Ecommerce.MasterThesis.dto.UserDTO;

public interface AuthService {
    public UserDTO createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);
}
