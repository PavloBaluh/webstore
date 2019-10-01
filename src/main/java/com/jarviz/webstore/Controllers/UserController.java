package com.jarviz.webstore.Controllers;
import com.jarviz.webstore.Models.Address;
import com.jarviz.webstore.Models.PersonalData;
import com.jarviz.webstore.Models.User;
import com.jarviz.webstore.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/enableUser/{username}")
    public void confirmRegistration(@PathVariable("username") String username) {
        this.userService.enableUser(username);
    }
    @PostMapping("/addUserData")
    public boolean addUserData (Address address, PersonalData personalData,  MultipartFile userPicture) throws IOException {
     return userService.addUserData(personalData,address,userPicture);
    }

}
