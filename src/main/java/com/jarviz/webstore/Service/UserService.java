package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.UserDao;
import com.jarviz.webstore.Models.User;
import com.jarviz.webstore.tools.ExceptionWriter;
import com.jarviz.webstore.tools.NotAuthenticatedException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    public User getByUsernameOrEmail(String userNameOrEmail){
        if (userNameOrEmail.contains("@")) return userDao.getByEmail(userNameOrEmail);
        else return userDao.getByUsername(userNameOrEmail);
    }
}
