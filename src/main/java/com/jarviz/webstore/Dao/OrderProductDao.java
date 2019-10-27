package com.jarviz.webstore.Dao;

import com.jarviz.webstore.Models.OrderEntity;
import com.jarviz.webstore.Models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderProductDao extends JpaRepository<OrderProduct,Integer> {

    @Transactional
    @Modifying
    @Query("delete from OrderProduct o where  o.orderEntity = :orderEntity")
    void deleteAllByOrderEntity(@Param("orderEntity") OrderEntity orderEntity);
}
