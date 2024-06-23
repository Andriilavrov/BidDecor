package org.example;

import io.javalin.Javalin;
import org.example.controller.UserController;

public class App 
{
    public static void main( String[] args )
    {
        Javalin app = Javalin.create().start(7000);
        UserController userController = new UserController();
    }
}
