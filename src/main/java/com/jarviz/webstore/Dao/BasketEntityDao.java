package com.jarviz.webstore.Dao;

import com.jarviz.webstore.Models.Basket;
import com.jarviz.webstore.Models.BasketEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BasketEntityDao extends JpaRepository<BasketEntity, Integer> {

    @Transactional
    @Modifying
    @Query("delete from BasketEntity b where b.basket =:basket and b.id = :basketEntityId")
    void deleteBasketEntity(@Param("basket") Basket basket, @Param("basketEntityId") Integer basketEntityId);
}
