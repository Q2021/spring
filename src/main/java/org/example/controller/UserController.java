package org.example.controller;

import org.example.annotation.AutoWired;
import org.example.service.UserService;

public class UserController {

    @AutoWired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
