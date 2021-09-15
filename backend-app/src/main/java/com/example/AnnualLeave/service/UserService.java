package com.example.AnnualLeave.service;

import com.example.AnnualLeave.model.Role;
import com.example.AnnualLeave.model.User;
import com.example.AnnualLeave.repository.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserService {


    private UserRepository userRepository;
    private AuthorityRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       AuthorityRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User saveUser(String data) throws JSONException {
        JSONObject user = new JSONObject(data);
        User newUser = new User();
        System.out.println(user.toString());

        newUser.setUserName(user.optString("userName"));
        newUser.setName(user.optString("name"));
        newUser.setLastName(user.optString("lastName"));
        newUser.setEmail(user.optString("email"));
        newUser.setPassword(bCryptPasswordEncoder.encode(user.optString("password")));
        newUser.setActive(true);
        Role userRole = roleRepository.findByRole(user.optString("roles"));
        newUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        System.out.println(newUser.toString());
        return userRepository.save(newUser);
    }

    public boolean isValidPassword(User user, String password) {
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }


    //to get user form DB
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    //to get all users form DB
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }



    //delete
    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "user removed" + id;
    }

    //update
    public User updateUser(String data) throws JSONException {
        JSONObject user = new JSONObject(data);
        System.out.println(user.toString());
        User user1 = userRepository.findByUserName(user.optString("userName"));
      //  User user1 = existingUser.get();
        user1.setUserName(user.optString("userName"));
        user1.setName(user.optString("name"));
        user1.setLastName(user.optString("lastname"));
        user1.setEmail(user.optString("email"));
        user1.setActive(true);
        user1.setPassword(user1.getPassword());
        user1.setId(user1.getId());
       // user1.setRoles();
        System.out.println(user1.toString());
        return userRepository.save(user1);
    }


}
