package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.CategoryDao;
import com.jarviz.webstore.Exceptions.ExceptionWriter;
import com.jarviz.webstore.Models.Category;
import com.jarviz.webstore.Models.SubCategory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryDao categoryDao;
    private SubCategoryService subCategoryService;
    private ExceptionWriter exceptionWriter;

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    public Boolean rename(String data) throws IOException {
        try {
            String[] split = data.split(",");
            String newName = split[0];
            Integer id = Integer.parseInt(split[1]);
            Category category = categoryDao.getOne(id);
            category.setName(newName);
            categoryDao.save(category);
            return true;
        } catch (Exception e) {
            exceptionWriter.write(e.getLocalizedMessage());
            return false;
        }
    }

    public void delete(Integer id) {
        Category category = categoryDao.getOne(id);
        subCategoryService.deleteByCategory(category);
        categoryDao.delete(category);
    }

    public Category add(String name) {
        return categoryDao.save(new Category(name));
    }
}
