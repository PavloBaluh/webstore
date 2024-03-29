package com.jarviz.webstore.Dao;

import com.jarviz.webstore.Models.PropertyValue;
import javafx.beans.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PropertyValueDao extends JpaRepository<PropertyValue,Integer> {

    PropertyValue getByValue(String value);

    @Transactional
    @Modifying
    @Query("delete from PropertyValue  p where  p.id = :id")
    void delete(@Param("id") Integer id);
}
