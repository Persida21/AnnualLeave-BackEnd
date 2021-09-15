package com.example.AnnualLeave.repository;

import com.example.AnnualLeave.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);
}
