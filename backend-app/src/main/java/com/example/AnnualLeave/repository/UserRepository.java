package com.example.AnnualLeave.repository;

import com.example.AnnualLeave.model.User;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserName(String userName);

    User findByEmail(String email);




    // User findUserRole(Integer userId);
}
