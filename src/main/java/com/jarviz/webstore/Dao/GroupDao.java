package com.jarviz.webstore.Dao;

import com.jarviz.webstore.Models.Group;
import com.jarviz.webstore.Models.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupDao extends JpaRepository<Group, Integer> {

    Group getByName(String name);

    @Query("select g.name,s.name,c.name from  Group  g join g.subCategory s join s.category c where g.name = :name")
    String getHierarchyByGroupName(@Param("name") String name);

    @Transactional
    void deleteAllBySubCategory(SubCategory subCategory);

    @Transactional
    @Modifying
    @Query("delete from Group  g where  g =:gr")
    void deleteByGroup(@Param("gr") Group gr);

    List<Group> getBySubCategory(SubCategory subCategory);
}
