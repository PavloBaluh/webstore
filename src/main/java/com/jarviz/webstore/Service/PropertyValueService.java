package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.PropertyDao;
import com.jarviz.webstore.Dao.PropertyValueDao;
import com.jarviz.webstore.Exceptions.ExceptionWriter;
import com.jarviz.webstore.Models.Property;
import com.jarviz.webstore.Models.PropertyValue;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PropertyValueService {
    private PropertyValueDao propertyValueDao;
    private PropertyDao propertyDao;
    private ExceptionWriter exceptionWriter;

    public PropertyValue getByName(String name) {
        return propertyValueDao.getByValue(name);
    }

    public List<PropertyValue> getByNames(String values) {
        List<PropertyValue> propertyValues = new ArrayList<>();
        String[] split = values.split(",");
        for (String s : split) {
            propertyValues.add(getByName(s));
        }
        return propertyValues;
    }

    public void delete(Integer id) {
        this.propertyValueDao.delete(id);
    }

    public Boolean rename(String data) throws IOException {
        System.out.println(data);
        try {
            String[] split = data.split(",");
            String newName = split[0];
            Integer id = Integer.parseInt(split[1]);
            PropertyValue propertyValue = propertyValueDao.getOne(id);
            propertyValue.setValue(newName);
            propertyValueDao.save(propertyValue);
            return true;
        } catch (Exception e) {
            exceptionWriter.write(e.getLocalizedMessage());
            return false;
        }
    }

    public PropertyValue addNew(Integer id) {
        Property one = propertyDao.getOne(id);
        PropertyValue propertyValue = new PropertyValue();
        propertyValue.setValue("Default");
        List<PropertyValue> values = one.getValues();
        values.add(propertyValue);
        one.setValues(values);
        propertyValue.setProperty(one);
        PropertyValue saved = propertyValueDao.save(propertyValue);
        propertyDao.save(one);
        return saved;
    }
}
