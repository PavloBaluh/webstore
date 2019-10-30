package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.OrderDao;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderDao orderDao;
    private UserService userService;


}
