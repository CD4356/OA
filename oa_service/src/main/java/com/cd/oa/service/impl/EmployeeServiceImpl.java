package com.cd.oa.service.impl;

import com.cd.oa.dao.EmployeeDao;
import com.cd.oa.entity.Employee;
import com.cd.oa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Qualifier("employeeDao")
    @Autowired
    private EmployeeDao employeeDao;

    public void add(Employee employee) {
        //给添加的员工设置一个默认密码
        employee.setPassword("123456");
        employeeDao.insert(employee);
    }

    public void edit(Employee employee) {
        employeeDao.update(employee);
    }

    public void remove(String id) {
        employeeDao.delete(id);
    }

    public Employee get(String id) {
        return employeeDao.select(id);
    }

    public List<Employee> getAll() {
        return employeeDao.selectAll();
    }
}
