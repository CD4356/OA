package com.cd.oa.service.impl;

import com.cd.oa.dao.EmployeeDao;
import com.cd.oa.entity.Employee;
import com.cd.oa.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("globalService")
public class GlobalServiceImpl implements GlobalService {

    @Qualifier("employeeDao")
    @Autowired
    private EmployeeDao employeeDao;

    public Employee login(String id, String password) {
        Employee employee = employeeDao.select(id);
        if(employee!=null && employee.getPassword().equals(password)){
            return employee;
        }
        return null;
    }

    public void changePassword(Employee employee) {
        employeeDao.update(employee);
    }
}
