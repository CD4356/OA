package com.cd.oa.service.impl;

import com.cd.oa.dao.DepartmentDao;
import com.cd.oa.entity.Department;
import com.cd.oa.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {


    @Qualifier("departmentDao")
    @Autowired
    private DepartmentDao departmentDao;

    public void add(Department department) {
        departmentDao.insert(department);
    }

    public void edit(Department department) {
        departmentDao.update(department);
    }

    public void remove(String id) {
        departmentDao.delete(id);
    }

    public Department get(String id) {
        return departmentDao.select(id);
    }

    public List<Department> getAll() {
        return departmentDao.selectAll();
    }
}