package com.example.Ecommerce.MasterThesis.entity;

import com.example.Ecommerce.MasterThesis.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


}
