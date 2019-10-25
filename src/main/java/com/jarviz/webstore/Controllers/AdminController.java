package com.jarviz.webstore.Controllers;

import com.jarviz.webstore.Models.OrderEntity;
import com.jarviz.webstore.Models.Orders;
import com.jarviz.webstore.Models.User;
import com.jarviz.webstore.Service.AdminService;
import com.jarviz.webstore.Service.OrderEntityService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    private OrderEntityService orderEntityService;

    @GetMapping("/gerAllUsers")
    public List<User> getAllUsers (){
        return adminService.getAllUsers();
    }

    @GetMapping("/lockUnlockUser/{id}")
    public User lockUnlockUser (@PathVariable("id") Integer id){
        return adminService.lockUnlock(id);
    }

    @GetMapping("/getAllOrders")
    public List<Orders> getAllOrders(){
        return this.adminService.getAllOrders();
    }
    @GetMapping("/changeOrderStatus/{info}")
    public void changeOrderStatus (@PathVariable("info") String info) {
        this.orderEntityService.changeOrderStatus(info);
    }
    @DeleteMapping("/deleteOrderEntity/{id}")
    public boolean deleteOrderEntity (@PathVariable("id") Integer id) throws IOException {
       return this.orderEntityService.deleteOrderEntity(id);
    }
}
