package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.PropertyValueDao;
import com.jarviz.webstore.Models.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyValueService {
    @Autowired
    private PropertyValueDao propertyValueDao;

    public PropertyValue getByName(String name){
        return propertyValueDao.getByValue(name);
    }

    public List<PropertyValue> getByNames(String values){
        List<PropertyValue> propertyValues = new ArrayList<>();
        String[] split = values.split(",");
        for (String s : split) {
           propertyValues.add(getByName(s));
        }
        return propertyValues;
    }
}
