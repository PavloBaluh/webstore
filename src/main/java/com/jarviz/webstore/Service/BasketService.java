package com.jarviz.webstore.Service;
import com.jarviz.webstore.Dao.BasketEntityDao;
import com.jarviz.webstore.Models.BasketEntity;
import com.jarviz.webstore.Models.User;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BasketService {
    @Autowired
    private BasketEntityDao basketEntityDao;
    @Autowired
    private UserService userService;

    public boolean deleteFromBasket(Integer id) throws IOException {
        User user = userService.getAuthentication();
        if (user == null) return false;
        basketEntityDao.deleteBasketEntity(user.getBasket(),id);
        return true;
    }
}
