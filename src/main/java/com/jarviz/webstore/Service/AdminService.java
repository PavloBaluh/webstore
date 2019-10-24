package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.UserDao;
import com.jarviz.webstore.Enums.Role;
import com.jarviz.webstore.Models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminService {
    private UserDao userDao;

    public List<User> getAllUsers(){
       return userDao.findAll().stream().filter(user -> user.getRoles() == Role.ROLE_USER).collect(Collectors.toList());
    }

    public User lockUnlock(Integer id) {
        User one = userDao.getOne(id);
        one.setAccountNonLocked(!one.isAccountNonLocked());
        userDao.save(one);
        return one;
    }
}
