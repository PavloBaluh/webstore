package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.SubCategoryDao;
import com.jarviz.webstore.Models.Property;
import com.jarviz.webstore.Models.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService {
    @Autowired
    private SubCategoryDao subCategoryDao;

    public SubCategory getByName(String name) {
        return subCategoryDao.getByName(name);
    }
    public List<Property> getAllPropertiesBySubCategory(String subCategory){
        SubCategory sub = getByName(subCategory);
        return sub.getProperty();
    }
}
