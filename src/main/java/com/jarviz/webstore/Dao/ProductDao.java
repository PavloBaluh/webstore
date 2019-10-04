package com.jarviz.webstore.Dao;

import com.jarviz.webstore.Models.Category;
import com.jarviz.webstore.Models.Group;
import com.jarviz.webstore.Models.Product;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.group = :groupEl  order by p.title asc ")
    List<Product> getProductsByGroup(@Param("groupEl") Group group, Pageable pageable);

//    @Query("select p from  Product p join p.propertyValues v where p.price between :pF and :pT and v.id in (:pr) and p.group = :gr")
//    List<Product> getSortedProductsWithProperties(@Param("pF") float priceFrom, @Param("pT") float priceTo, @Param("gr") Group group, @Param("pr") ArrayList<Integer> properties, Pageable pageable);

    @Query("select p from  Product p where p.price between :pF and :pT and p.group = :gr")
    List<Product> getSortedProducts(@Param("pF") float priceFrom, @Param("pT") float priceTo, @Param("gr") Group group, Pageable pageable);

    List<Product> getByGroup(Group group);
}
