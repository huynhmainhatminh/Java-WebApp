package com.restapi.api.controllers;
import com.restapi.api.model.requests.LoginRequests;
import com.restapi.api.services.LoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class LoginController {

    @Autowired
    LoginServices loginServices;

    @PostMapping("/login")
    public boolean login(@ModelAttribute LoginRequests loginRequests) {
        return loginServices.login(loginRequests.getUsername(), loginRequests.getPassword());
    }

}
