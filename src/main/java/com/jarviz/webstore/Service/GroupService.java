package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.GroupDao;
import com.jarviz.webstore.Dao.ProductDao;
import com.jarviz.webstore.Dao.SubCategoryDao;
import com.jarviz.webstore.Exceptions.ExceptionWriter;
import com.jarviz.webstore.Models.Category;
import com.jarviz.webstore.Models.Group;
import com.jarviz.webstore.Models.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class GroupService {
    private GroupDao groupDao;
    private ProductDao productDao;
    private ExceptionWriter exceptionWriter;
    private SubCategoryDao subCategoryDao;


    public List<String> getHierarchyByGroupName(String name) {
        String hierarchyByGroupName = groupDao.getHierarchyByGroupName(name);
        return Arrays.asList(hierarchyByGroupName.split(","));
    }

    public Group getByName(String s) {
        return this.groupDao.getByName(s);
    }

    public Boolean rename(String data) throws IOException {
        try {
            String[] split = data.split(",");
            String newName = split[0];
            Integer id = Integer.parseInt(split[1]);
            Group group = groupDao.getOne(id);
            group.setName(newName);
            groupDao.save(group);
            return true;
        }
        catch (Exception e) {
            exceptionWriter.write(e.getLocalizedMessage());
            return false;
        }
    }

    public void  deleteBySubCategory(SubCategory subCategory){
        List<Group> bySubCategory = groupDao.getBySubCategory(subCategory);
        for (Group group : bySubCategory) {
            productDao.deleteByGroup(group);
        }
        groupDao.deleteAllBySubCategory(subCategory);
    }

    public void delete(Integer id) {
        Group group = groupDao.getOne(id);
        productDao.deleteByGroup(group);
        groupDao.deleteByGroup(group);
    }

    public Group add(String name, SubCategory subCategory) {
        Group group = new Group(name);
        SubCategory one = subCategoryDao.getOne(subCategory.getId());
        group.setSubCategory(one);
        return groupDao.save(group);
    }
}
