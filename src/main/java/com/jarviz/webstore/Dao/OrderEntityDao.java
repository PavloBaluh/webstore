package com.jarviz.webstore.Dao;
import com.jarviz.webstore.Models.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderEntityDao extends JpaRepository<OrderEntity,Integer> {
    OrderEntity getOrdersById(Integer id);

    @Transactional
    @Modifying
    @Query("delete from OrderEntity o where  o.id = :orderEntityId")
    void deleteOrderEntity(@Param("orderEntityId") Integer orderEntityId);
}
