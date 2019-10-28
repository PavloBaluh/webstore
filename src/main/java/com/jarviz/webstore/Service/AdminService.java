package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.UserDao;
import com.jarviz.webstore.Enums.Role;
import com.jarviz.webstore.Exceptions.ExceptionWriter;
import com.jarviz.webstore.Models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminService {
    private UserDao userDao;
    private GroupService groupService;
    private PropertyValueService propertyValueService;
    private ProductService productService;
    private ExceptionWriter exceptionWriter;

    public List<User> getAllUsers() {
        return userDao.findAll().stream().filter(user -> user.getRoles() == Role.ROLE_USER).collect(Collectors.toList());
    }

    public User lockUnlock(Integer id) {
        User one = userDao.getOne(id);
        one.setAccountNonLocked(!one.isAccountNonLocked());
        userDao.save(one);
        return one;
    }

    public List<Orders> getAllOrders() {
        List<User> all = userDao.findAll();
        List<Orders> orders = new ArrayList<>();
        all.forEach(user -> {
            if (user.getOrder() != null && user.getOrder().getOrderEntities().size()>0)
            orders.add(user.getOrder());
        });
        return orders;
    }

    public boolean changeProductInfo(Product product, Integer groupId, MultipartFile productPicture, String properties,String oldPicture) throws IOException {
        System.out.println(productPicture);
        Group group = groupService.getOne(groupId);
        product.setGroup(group);
        List<PropertyValue> propertyValues = propertyValueService.getByNames(properties);
        product.setPropertyValues(propertyValues);
        if (savePicture(productPicture,product)){
            product.setPicture(productPicture.getOriginalFilename());
        }
        else {
            product.setPicture(oldPicture);
        }
        productService.save(product);
        return true;
    }
    private boolean savePicture(MultipartFile picture, Product product) throws IOException {
        String path = System.getProperty("user.home") + "\\Desktop\\Front\\src\\assets\\Goods\\";
        if (picture == null) {
            return false;
        }
        if (!(product.getPicture() == null) && !(product.getPicture().equals(""))) {
            File file = new File(path + product.getPicture());
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
