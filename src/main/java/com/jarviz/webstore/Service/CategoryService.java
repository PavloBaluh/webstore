package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.CategoryDao;
import com.jarviz.webstore.Models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    public List<Category> getAllCategories(){
      return  categoryDao.findAll();
    }
}
