package org.example.controller;

import io.javalin.http.Context;
import org.example.model.User;
import org.example.service.UserService;

public class UserController {
    private final UserService userService = new UserService();

    public UserController() {
    }

    public void getAllUsers(Context ctx) {
        ctx.json(this.userService.getAllUsers());
    }

    public void createUser(Context ctx) {
        User user = (User)ctx.bodyAsClass(User.class);
        this.userService.createUser(user);
        ctx.status(201);
    }
}
