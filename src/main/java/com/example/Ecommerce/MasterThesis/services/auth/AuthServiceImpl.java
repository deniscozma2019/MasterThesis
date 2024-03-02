package com.example.Ecommerce.MasterThesis.services.auth;

import com.example.Ecommerce.MasterThesis.dto.SignupRequest;
import com.example.Ecommerce.MasterThesis.dto.UserDTO;
import com.example.Ecommerce.MasterThesis.entity.User;
import com.example.Ecommerce.MasterThesis.enums.UserRole;
import com.example.Ecommerce.MasterThesis.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl  implements  AuthService{


    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
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
}
