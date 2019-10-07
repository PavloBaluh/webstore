package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupDao groupDao;

    public List<String> getHierarchyByGroupName(String name) {
        String hierarchyByGroupName = groupDao.getHierarchyByGroupName(name);
        return Arrays.asList(hierarchyByGroupName.split(","));
    }

}
