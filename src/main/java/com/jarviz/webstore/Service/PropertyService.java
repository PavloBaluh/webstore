package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.PropertyDao;
import com.jarviz.webstore.Exceptions.ExceptionWriter;
import com.jarviz.webstore.Models.Category;
import com.jarviz.webstore.Models.Property;
import com.jarviz.webstore.Models.PropertyValue;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class PropertyService {
    private PropertyDao propertyDao;
    private PropertyValueService propertyValueService;
    private ExceptionWriter exceptionWriter;

    public List<Property> getAll(){
        return propertyDao.findAll();
    }

    public Property addNew(){
        Property property = new Property();
        property.setPropertyName("Default");
        return propertyDao.save(property);
    }

    public Boolean rename(String data) throws IOException {
        try {
            String[] split = data.split(",");
            String newName = split[0];
            Integer id = Integer.parseInt(split[1]);
            Property property = propertyDao.getOne(id);
            property.setPropertyName(newName);
            propertyDao.save(property);
            return true;
        } catch (Exception e) {
            exceptionWriter.write(e.getLocalizedMessage());
            return false;
        }
    }

    public void delete(Integer id) {
        Property prop = propertyDao.getOne(id);
        for (PropertyValue value : prop.getValues()) {
            propertyValueService.delete(value.getId());
        }
        propertyDao.delete(id);
    }
}
