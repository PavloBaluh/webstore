package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.OrderEntityDao;
import com.jarviz.webstore.Dao.OrderProductDao;
import com.jarviz.webstore.Enums.OrderStatus;
import com.jarviz.webstore.Exceptions.ExceptionWriter;
import com.jarviz.webstore.Models.OrderEntity;
import com.jarviz.webstore.Models.OrderProduct;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

@Service
public class OrderEntityService {
    @Autowired
    private OrderEntityDao orderEntityDao;
    @Autowired
    private ExceptionWriter exceptionWriter;
    @Autowired
    private OrderProductDao orderProductDao;

    public OrderEntity getOrderById(Integer id) {
        return orderEntityDao.getOrdersById(id);
    }

    public void changeOrderStatus(String info) {
        String[] split = info.split(",");
        String orderEntityId = split[1];
        String status = split[0].replaceAll(" ", "_");
        OrderEntity orderById = getOrderById(Integer.parseInt(orderEntityId));
        orderById.setOrderStatus(OrderStatus.valueOf(status));
        orderEntityDao.save(orderById);
    }

    public boolean deleteOrderEntity(Integer id) throws IOException {
        try {
            OrderEntity orderById = getOrderById(id);
            this.orderProductDao.deleteAllByOrderEntity(orderById);
            orderEntityDao.deleteOrderEntity(id);
            return true;
        } catch (Exception e) {
            exceptionWriter.write(e.getLocalizedMessage());
            return false;
        }
    }
}
