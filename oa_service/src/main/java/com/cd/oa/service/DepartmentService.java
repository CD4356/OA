package com.cd.oa.service;

import com.cd.oa.entity.Department;

import java.util.List;

public interface DepartmentService {

    void add(Department department);

    void edit(Department department);

    void remove(String id);

    Department get(String id);

    List<Department> getAll();
}
