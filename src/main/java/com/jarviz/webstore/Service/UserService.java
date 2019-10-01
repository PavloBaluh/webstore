package com.jarviz.webstore.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarviz.webstore.Dao.UserDao;
import com.jarviz.webstore.Models.AccountCredentials;
import com.jarviz.webstore.Models.Address;
import com.jarviz.webstore.Models.PersonalData;
import com.jarviz.webstore.Models.User;
import com.jarviz.webstore.tools.ExceptionWriter;
import com.jarviz.webstore.tools.NotAuthenticatedException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Service
@AllArgsConstructor
public class UserService {
    private ExceptionWriter exceptionWriter;
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;

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

    public User getAuthentication() throws IOException {
        String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        User byName = getByUsername(authentication);
        if (byName == null) try {
            throw new NotAuthenticatedException();
        } catch (NotAuthenticatedException e) {
            exceptionWriter.write(e.getClass().getName());
            return null;
        }
        return byName;
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
        if (savePicture(picture)) {
            personalData.setPicture(picture.getOriginalFilename());
        }
        String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.getByUsername(authentication);
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

    private PersonalData updateUserInfo(User user, PersonalData personalData) {
        PersonalData personalDataUser = user.getPersonalData();
        Address address = personalData.getAddress();
        personalDataUser.setName(personalData.getName());
        personalDataUser.setSurname(personalData.getSurname());
        personalDataUser.setPicture(personalData.getPicture());
        personalDataUser.setPhoneNumber(personalData.getPhoneNumber());
        personalDataUser.getAddress().setCity(address.getCity());
        personalDataUser.getAddress().setNumber(address.getNumber());
        personalDataUser.getAddress().setStreet(address.getStreet());
        personalDataUser.getAddress().setRegion(address.getRegion());
        personalDataUser.getAddress().setCountry(address.getCountry());
        return personalDataUser;
    }

    private boolean savePicture(MultipartFile picture) throws IOException {
        if (picture == null){
            return false;
        }
        String path = System.getProperty("user.home") + "\\Desktop\\Front\\src\\assets\\Users\\" + picture.getOriginalFilename();
        File file = new File(path);
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
