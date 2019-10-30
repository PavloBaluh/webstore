package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.CategoryDao;
import com.jarviz.webstore.Dao.SubCategoryDao;
import com.jarviz.webstore.Exceptions.ExceptionWriter;
import com.jarviz.webstore.Models.Category;
import com.jarviz.webstore.Models.Property;
import com.jarviz.webstore.Models.SubCategory;
import com.jarviz.webstore.Models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class SubCategoryService {
    private GroupService groupService;
    private SubCategoryDao subCategoryDao;
    private ExceptionWriter exceptionWriter;
    private CategoryDao categoryDao;

    public SubCategory getByName(String name) {
        return subCategoryDao.getByName(name);
    }

    public void deleteByCategory(Category category){
        List<SubCategory> byCategory = subCategoryDao.getByCategory(category);
        for (SubCategory subCategory : byCategory) {
            groupService.deleteBySubCategory(subCategory);
        }
        subCategoryDao.deleteAllByCategory(category);
    }



    public List<Property> getAllPropertiesBySubCategory(String subCategory) throws IOException {
        try {
            SubCategory sub = getByName(subCategory);
            return sub.getProperty();
        }
        catch (NullPointerException e){
            exceptionWriter.write(e.getLocalizedMessage());
            return null;
        }
    }

    public Boolean edit(String data, MultipartFile picture) throws IOException {
        try {
            String[] split = data.split(",");
            String newName = split[0];
            Integer id = Integer.parseInt(split[1]);
            SubCategory Subcategory = subCategoryDao.getOne(id);
            Subcategory.setName(newName);
            if(savePicture(picture,Subcategory)){
                Subcategory.setPicture(picture.getOriginalFilename());
            }
            subCategoryDao.save(Subcategory);
            return true;
        }
        catch (Exception e) {
            exceptionWriter.write(e.getLocalizedMessage());
            return false;
        }
    }

    public void delete(Integer id) {
        SubCategory sub = subCategoryDao.getOne(id);
        groupService.deleteBySubCategory(sub);
        subCategoryDao.delete(sub);
    }

    public SubCategory add(String name, Category category) {
        SubCategory subCategory = new SubCategory(name);
        Category one = categoryDao.getOne(category.getId());
        subCategory.setCategory(one);
       return subCategoryDao.save(subCategory);
    }


    private boolean savePicture(MultipartFile picture, SubCategory subCategory) throws IOException {
        String path = System.getProperty("user.home") + "\\Desktop\\Front\\src\\assets\\Groups\\";
        if (picture == null) {
            return false;
        }
        if (!(subCategory.getPicture() == null) && !(subCategory.getPicture().equals(""))) {
            File file = new File(path + subCategory.getPicture());
            if (file.exists()) {
                file.delete();
            }
        }
        File file = new File(path + picture.getOriginalFilename());
        if (!file.exists()) {
            file.createNewFile();
        }
        try {
            picture.transferTo(file);
        } catch (IOException e) {
            exceptionWriter.write(e.getLocalizedMessage());
            return false;
        }

        return true;
    }
}
