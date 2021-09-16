package com.example.AnnualLeave.controller;

import com.example.AnnualLeave.model.Role;
import com.example.AnnualLeave.model.User;
import com.example.AnnualLeave.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@RestController
@Controller
@RequestMapping(value = "/api")
@CrossOrigin
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }


    @RequestMapping(path = "/login", produces = "application/json")
    public String login(@RequestBody User user) throws JSONException {
        System.out.println(user.getUserName());
        JSONObject result = new JSONObject();
        User existinguser = userService.findUserByUserName(user.getUserName());

        // user does not exist
        if (existinguser == null) {
            return result.put("message", "Username not correct").toString();
        }
        if (userService.isValidPassword(existinguser, user.getPassword())) {
            result.put("user", user.getUserName());
            Set<Role> roles = existinguser.getRoles();

            for (Role role : roles) {
                System.out.println("role.getRole()");
                System.out.println(role.getRole());
                result.put("role", role.getRole());
            }
            return result.toString();
        }
        System.out.println(result.toString());
        return result.put("message", "Password not correct").toString();

        //password not correct
    }


    @PostMapping(path = "/getRole", produces = "application/json")
    public String getRole(@RequestBody String userName) {
        User existinguser = userService.findUserByUserName(userName);

        // user does not exist

        if (!(existinguser == null)) {
            Set<Role> result = existinguser.getRoles();
            for (Role role : result) {
                System.out.println("role.getRole()");
                System.out.println(role.getRole());
                return role.getRole();
            }
        }
        return null;
    }


    // get username and password from authorization header
    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();

        return () -> new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }


}
