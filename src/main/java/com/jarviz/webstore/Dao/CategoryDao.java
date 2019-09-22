package com.jarviz.webstore.Dao;

import com.jarviz.webstore.Models.Category;
import com.jarviz.webstore.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends JpaRepository<Category,Integer> {
}
