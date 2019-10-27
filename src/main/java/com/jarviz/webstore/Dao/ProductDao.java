package com.jarviz.webstore.Dao;

import com.jarviz.webstore.Models.Category;
import com.jarviz.webstore.Models.Group;
import com.jarviz.webstore.Models.Product;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.group = :groupEl  order by p.title asc ")
    List<Product> getProductsByGroup(@Param("groupEl") Group group, Pageable pageable);

    @Query("select p from  Product p where p.price between :pF and :pT and p.group = :gr")
    List<Product> getSortedProducts(@Param("pF") float priceFrom, @Param("pT") float priceTo, @Param("gr") Group group, Pageable pageable);

    List<Product> getByGroup(Group group);

    @Query("select p from Product p where p.title like concat('%',:p,'%') ")
    List<Product> getByCharsSequence(@Param("p") String ch, Pageable pageable);

    @Query("select p from Product p where p.group = :groupEl and p.price between :pF and :pT ")
    List<Product> getProductCount(@Param("groupEl") Group group, @Param("pF") float priceFrom, @Param("pT") float priceTo);

    @Query("select p from Product p where p.group = :groupEl")
    List<Product> getProductCountWithGroup(@Param("groupEl") Group group);

    @Query("select p from Product p order by p.rate desc ")
    List<Product> getMostPopularProducts(Pageable pageable);

    @Query("select p from Product p order by p.date desc ")
    List<Product> getLatestProducts(Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Product p where  p.group = :group")
    void deleteByGroup(@Param("group") Group group);
}
