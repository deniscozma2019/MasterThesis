package com.example.Ecommerce.MasterThesis.controller;

import com.example.Ecommerce.MasterThesis.dto.AuthenticationRequest;
import com.example.Ecommerce.MasterThesis.entity.User;
import com.example.Ecommerce.MasterThesis.repository.UserRepository;
import com.example.Ecommerce.MasterThesis.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@EnableWebSecurity

public class AuthController {

    private static final String HEADER_STRING = "Authorization" ;
    private static final String TOKEN_PREFIX = "Bearer";
    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public void CreateAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws JSONException, IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password.");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if(optionalUser.isPresent()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", optionalUser.get().getId());
            jsonObject.put("role", optionalUser.get().getRole().toString());
            response.getWriter().write(jsonObject.toString());
        }
        response.addHeader(HEADER_STRING, TOKEN_PREFIX+jwt);

    }


}
