package com.jarviz.webstore.Dao;

import com.jarviz.webstore.Models.Category;
import com.jarviz.webstore.Models.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryDao extends JpaRepository<SubCategory,Integer> {
    SubCategory getByName(String name);

    void deleteAllByCategory(Category category);

    List<SubCategory> getByCategory(Category category);
}
