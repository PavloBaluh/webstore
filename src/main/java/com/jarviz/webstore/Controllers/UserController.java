package com.jarviz.webstore.Controllers;
import com.jarviz.webstore.Models.*;
import com.jarviz.webstore.Service.BasketService;
import com.jarviz.webstore.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private BasketService basketService;

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

    @PostMapping("/addProductsToCart")
    public Boolean addProductsToCart (@RequestBody BasketEntity basketEntry) throws IOException {
        return  userService.addToBasket(basketEntry);
    }
    @GetMapping("/getAllProductsFromCart")
    public List<BasketEntity> getAllProductsFromCart() throws IOException {
        return userService.getAllProductsFromCart();
    }
    @DeleteMapping("/deleteFromBasket/{id}")
    public Boolean deleteFromBasket(@PathVariable("id") Integer id) throws IOException {
        return this.basketService.deleteFromBasket(id);
    }
}
