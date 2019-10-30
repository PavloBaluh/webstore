package com.jarviz.webstore.Dao;
import com.jarviz.webstore.Models.Group;
import com.jarviz.webstore.Models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface PropertyDao extends JpaRepository<Property,Integer> {



    @Transactional
    @Modifying
    @Query("delete from Property  p where  p.id = :id")
    void delete(@Param("id") Integer id);
}
