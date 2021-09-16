package com.example.AnnualLeave.controller;

import com.example.AnnualLeave.model.Application;
import com.example.AnnualLeave.model.User;
import com.example.AnnualLeave.service.ApplicationService;
import com.example.AnnualLeave.service.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@CrossOrigin
@RestController
@Controller
@RequestMapping(value = "/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService ;
    private UserService userService;


    public ApplicationController(ApplicationService applicationService, UserService userService) {
        this.applicationService = applicationService;
        this.userService = userService;
    }

    @PostMapping("/addApplication")
    public Application addApplication(@RequestBody String data) throws JSONException {

        System.out.println(data);
        return applicationService.saveApplication(data);
    }

    @PutMapping("/updateApplication")
    public Application updateApplication(@RequestBody String data) throws JSONException {
        System.out.println("user");
        System.out.println(data);
        return applicationService.updateApplication(data);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        System.out.println("user id");
        System.out.println(id);
        return applicationService.deleteApplication(id);
    }

    @GetMapping("/getApplication/{id}")
    public Optional<Application> getApplication(@PathVariable Long id){

        return applicationService.getApplicationById(id);
    }

    @GetMapping(path ="/getApplications" , produces = "application/json")
    public String getApplications() throws JSONException {
        Iterable<Application> applist = applicationService.getApplications();
        JSONArray appObj = new JSONArray();
        for (Application app : applist)
        {
            System.out.println(app);
            JSONObject result = new JSONObject();
            result.put("id",app.getId());
            result.put("type",  app.getType());
            result.put("description", app.getDescription());
            result.put("title", app.getTitle());
            result.put("startDate", app.getStartDate());
            result.put("user", app.getUser().getUserName());
            appObj.put(result);
        }
        return appObj.toString();
    }


    @PostMapping(path ="/getApplicationsByUser" , produces = "application/json")
    public String getApplicationsByUser(@RequestBody String username) throws JSONException {
        System.out.println(username);
        User user = userService.findUserByUserName(username.toString());
        System.out.println("user");
        System.out.println(user);
        Iterable<Application> applist = applicationService.getApplicationsByUser(user.getId());
        System.out.println("applist");
        System.out.println(applist);
        JSONArray appObj = new JSONArray();
        for (Application app : applist)
        {
            System.out.println(app);
            JSONObject result = new JSONObject();
            result.put("id",app.getId());
            result.put("type",  app.getType());
            result.put("description", app.getDescription());
            result.put("title", app.getTitle());
            result.put("startDate", app.getStartDate());
            appObj.put(result);
        }
        return appObj.toString();
    }
}
