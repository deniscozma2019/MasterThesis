package com.example.Ecommerce.MasterThesis.services.auth;

import com.example.Ecommerce.MasterThesis.dto.AuthenticationRequest;
import com.example.Ecommerce.MasterThesis.dto.SignupRequest;
import com.example.Ecommerce.MasterThesis.dto.UserDTO;
import com.example.Ecommerce.MasterThesis.entity.User;
import com.example.Ecommerce.MasterThesis.enums.UserRole;
import com.example.Ecommerce.MasterThesis.repository.UserRepository;
import com.example.Ecommerce.MasterThesis.utils.JwtUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public  class AuthServiceImpl  implements  AuthService{

    private final AuthenticationConfiguration authenticationConfiguration;

    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String HEADER_STRING = "Authorization" ;
    private static final String TOKEN_PREFIX = "Bearer";

    @Autowired
    @Lazy
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationConfiguration authenticationConfiguration){
        this.userRepository = userRepository;

        this.authenticationConfiguration = authenticationConfiguration;

    }


    public UserDTO createUser(SignupRequest signupRequest){
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setUserName(signupRequest.getUserName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.Costumer);
        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());

        return userDTO;


    }



    @Override
    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }



    public String authenticateUser(AuthenticationRequest authenticationRequest/*, HttpServletResponse response*/) throws JSONException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(((UserDetails) userDetails).getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        if (optionalUser.isPresent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", optionalUser.get().getId());
            jsonObject.put("role", optionalUser.get().getRole().toString());
            String token = jwtUtil.generateToken(userDetails.getUsername());
            jsonObject.put("token", token);
            return jsonObject.toString();
        }
     //   response.addHeader(HEADER_STRING, TOKEN_PREFIX+jwt);
        throw new UsernameNotFoundException("User not found");
    }
    @PostConstruct
    public void  createAdminAdminAccount(){
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if (null==adminAccount){
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setUserName("Admin");
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("123456789"));
            userRepository.save(user);

        }
    }


}
