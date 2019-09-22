package com.cd.oa.dao;

import com.cd.oa.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("departmentDao")
public interface DepartmentDao {

    void insert(Department department);

    void update(Department department);

    void delete(String id);

    Department select(String id);

    List<Department> selectAll();
}
