package com.example.Ecommerce.MasterThesis.dto;

import com.example.Ecommerce.MasterThesis.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {

    private  long id;

    private String email;

    private String password;

    private String userName;

    private UserRole userRole;
}
