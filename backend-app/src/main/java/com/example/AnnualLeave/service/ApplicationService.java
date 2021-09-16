package com.example.AnnualLeave.service;

import com.example.AnnualLeave.config.MailSenderConfig;
import com.example.AnnualLeave.model.Application;
import com.example.AnnualLeave.model.User;
import com.example.AnnualLeave.repository.ApplicationRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class ApplicationService {
    private ApplicationRepository applicationRepository;
    private UserService userService;
    private MailSenderConfig mailSender;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, UserService userService, MailSenderConfig mailSender) {
        this.applicationRepository = applicationRepository;
        this.userService = userService;
        this.mailSender = mailSender;
    }

    public Application saveApplication(String data) throws JSONException {
        JSONObject application = new JSONObject(data);
        Application newApplication = new Application();

        String type = application.optString("type");
        newApplication.setType(Application.TypeId.valueOf(type));
        newApplication.setTitle(application.optString("title"));
        newApplication.setDescription(application.optString("description"));
        newApplication.setStartDate(application.optLong("startDate"));
        newApplication.setEndDate(application.optLong("endDate"));
        User user = userService.findUserByUserName(application.optString("user"));
        newApplication.setUser(user);
        System.out.println("newApplication.toString()");
        System.out.println(user);
        mailSender.sendSimpleMessage("p.myftari12@gmail.com" , "New Application" , "Hello , a new application was added by " + user.getUserName());
        return applicationRepository.save(newApplication);
    }

    //to get application form DB
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    //to get all applications form DB
    public Iterable<Application> getApplications() {
        return applicationRepository.findAll();
    }

    //to get all applications by user id form DB
    public Iterable<Application> getApplicationsByUser(Long userId) {
        System.out.println("userId");
        System.out.println(userId);
        return applicationRepository.findByUser_id(userId);
    }

    //delete
    public String deleteApplication(Long id) {
        applicationRepository.deleteById(id);
        return "application removed " + id;
    }

    //update
    public Application updateApplication(String data) throws JSONException {
        JSONObject application = new JSONObject(data);

        System.out.println("ne update");
        Optional<Application> existingapplication = applicationRepository.findById(application.optLong("id"));
        String type = application.optString("type");
        existingapplication.get().setType(Application.TypeId.valueOf(type));
        existingapplication.get().setTitle(application.optString("title"));
        existingapplication.get().setDescription(application.optString("description"));
        existingapplication.get().setStartDate(application.optLong("startDate"));
        existingapplication.get().setEndDate(application.optLong("endDate"));
        User user = userService.findUserByUserName(application.optString("user"));
        existingapplication.get().setUser(user);
        // user1.setRoles();
        System.out.println(existingapplication.toString());
        return applicationRepository.save(existingapplication.get());
    }

}
