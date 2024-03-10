package com.example.Ecommerce.MasterThesis.repository;

import com.example.Ecommerce.MasterThesis.entity.User;
import com.example.Ecommerce.MasterThesis.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findFirstByEmail(String email);

    User findByRole(UserRole userRole);
}
