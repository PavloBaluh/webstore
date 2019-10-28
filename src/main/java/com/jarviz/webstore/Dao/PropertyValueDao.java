package com.jarviz.webstore.Dao;

import com.jarviz.webstore.Models.PropertyValue;
import javafx.beans.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyValueDao extends JpaRepository<PropertyValue,Integer> {

    PropertyValue getByValue(String value);
}
