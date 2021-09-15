package com.example.AnnualLeave.controller;

import com.example.AnnualLeave.model.Role;
import com.example.AnnualLeave.model.User;
import com.example.AnnualLeave.service.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


//Controller with connect with ServiceClass
@RequestMapping(value = "/user")
@RestController
@CrossOrigin
@Controller
public class UserController {

    @Autowired
    private UserService userService ;

    @PostMapping("/addUser")
    public User addUser(@RequestBody String user) throws JSONException {

        System.out.println(user);
        return userService.saveUser(user);
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody String data) throws JSONException {
        System.out.println("user");
        System.out.println(data);
        return userService.updateUser(data);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        System.out.println("user id");
        System.out.println(id);
        return userService.deleteUser(id);
    }

    @GetMapping("/getUser/{id}")
    public Optional<User> getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping(path ="/getUsers" , produces = "application/json")
    public String getUsers() throws JSONException {
        Iterable<User> userlist = userService.getUsers();
        JSONArray userObj = new JSONArray();
         for (User user : userlist)
        {
            System.out.println(user);
            JSONObject result = new JSONObject();
            result.put("id",user.getId());
            result.put("userName",  user.getUserName());
            result.put("password", user.getPassword());
            result.put("name", user.getName());
            result.put("lastName", user.getLastName());

            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                result.put("roles", role.getRole());
            }
            result.put("email", user.getEmail());
            result.put("active", user.getActive());
            userObj.put(result);
        }
        return userObj.toString();
    }

    @GetMapping("/getUserByName/{name}")
    public User getUser(@PathVariable String name){
        return userService.findUserByUserName(name);
    }

}
