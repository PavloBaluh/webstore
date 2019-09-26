package com.jarviz.webstore.Controllers;
import com.jarviz.webstore.Models.User;
import com.jarviz.webstore.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/register")
    public Boolean registerUser(User user) throws IOException {
        return this.userService.registerNewUser(user);
    }

    @GetMapping("/getAuthentication")
    public User getAuthentication() throws IOException {
        return userService.getAuthentication();
    }

}
