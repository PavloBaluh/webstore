package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.UserDao;
import com.jarviz.webstore.Models.*;
import com.jarviz.webstore.tools.ExceptionWriter;
import com.jarviz.webstore.tools.NotAuthenticatedException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService {
    private ExceptionWriter exceptionWriter;
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;
    private ProductService productService;

    public User getByUsername(String name) {
        return userDao.getByUsername(name);
    }

    public Boolean registerNewUser(User user) throws IOException {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.save(user);
            mailService.send(user);
        } catch (Exception e) {
            exceptionWriter.write(e.getLocalizedMessage());
            return false;
        }
        return true;
    }


    public User getByUsernameOrEmail(String userNameOrEmail) {
        if (userNameOrEmail.contains("@")) return userDao.getByEmail(userNameOrEmail);
        else return userDao.getByUsername(userNameOrEmail);
    }

    public void enableUser(String username) {
        User byUsername = this.userDao.getByUsername(username);
        byUsername.setEnabled(true);
        userDao.save(byUsername);
    }

    public boolean addUserData(PersonalData personalData, Address address, MultipartFile picture) throws IOException {
        personalData.setAddress(address);
        User user = getAuthentication();
        if (savePicture(picture, user)) {
            user.getPersonalData().setPicture(picture.getOriginalFilename());
        }
        if (user.getPersonalData() == null) {
            user.setPersonalData(personalData);
            userDao.save(user);
        } else {
            PersonalData newPersonalData = updateUserInfo(user, personalData);
            user.setPersonalData(newPersonalData);
            userDao.save(user);
        }
        return true;
    }

    public boolean addToBasket(BasketEntity basketEntry) throws IOException {
        User user = getAuthentication();
        Basket basket = user.getBasket();
        if (basket == null) basket = new Basket();
        basket.getBasketEntities().add(new BasketEntity(productService.get(basketEntry.getProduct().getId()), basketEntry.getQuantity(), basket));
        user.setBasket(basket);
        basket.setUser(user);
        userDao.save(user);
        return true;
    }

    public List<BasketEntity> getAllProductsFromCart() throws IOException {
        User user = getAuthentication();
        if (user.getBasket() == null) return null;
        if (user.getBasket().getBasketEntities().isEmpty()) return null;
        return user.getBasket().getBasketEntities();
    }


    public boolean addToWishes(Product product) throws IOException {
        User user = getAuthentication();
        boolean isAdd = user.getWishes().add(productService.get(product.getId()));
        userDao.save(user);
        return isAdd;
    }

    public List<Product> getAllWishes() throws IOException {
        User user = getAuthentication();
        return new ArrayList<>(user.getWishes());
    }

    public Boolean deleteFromWishes(Integer id) throws IOException {
        User user = getAuthentication();
        boolean isDeleted = user.getWishes().removeIf(product -> product.getId() == id);
        userDao.save(user);
        return isDeleted;
    }


    public User getAuthentication() throws IOException {
        try {
            String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
            User byName = getByUsername(authentication);
            if (byName == null) throw new NotAuthenticatedException();
            return byName;
        } catch (NotAuthenticatedException e) {
            exceptionWriter.write(e.getClass().getName());
            return null;
        }
    }


    private PersonalData updateUserInfo(User user, PersonalData personalData) {
        PersonalData personalDataUser = user.getPersonalData();
        Address address = personalData.getAddress();
        personalDataUser.setName(personalData.getName());
        personalDataUser.setSurname(personalData.getSurname());
        personalDataUser.setPhoneNumber(personalData.getPhoneNumber());
        personalDataUser.getAddress().setCity(address.getCity());
        personalDataUser.getAddress().setNumber(address.getNumber());
        personalDataUser.getAddress().setStreet(address.getStreet());
        personalDataUser.getAddress().setRegion(address.getRegion());
        personalDataUser.getAddress().setCountry(address.getCountry());
        return personalDataUser;
    }

    private boolean savePicture(MultipartFile picture, User user) throws IOException {
        String path = System.getProperty("user.home") + "\\Desktop\\Front\\src\\assets\\Users\\";
        if (picture == null) {
            return false;
        }
        if (!(user.getPersonalData().getPicture() == null) && !(user.getPersonalData().getPicture().equals(""))) {
            File file = new File(path + user.getPersonalData().getPicture());
            if (file.exists()) {
                file.delete();
            }
        }
        File file = new File(path + picture.getOriginalFilename());
        if (!file.exists()) {
            file.createNewFile();
        }
        try {
            picture.transferTo(file);
        } catch (IOException e) {
            exceptionWriter.write(e.getLocalizedMessage());
            return false;
        }

        return true;
    }

}
