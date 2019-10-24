package com.jarviz.webstore.Controllers;

import com.jarviz.webstore.Models.User;
import com.jarviz.webstore.Service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;

    @GetMapping("/gerAllUsers")
    public List<User> getAllUsers (){
        return adminService.getAllUsers();
    }

    @GetMapping("/lockUnlockUser/{id}")
    public User lockUnlockUser (@PathVariable("id") Integer id){
        return adminService.lockUnlock(id);
    }
}
