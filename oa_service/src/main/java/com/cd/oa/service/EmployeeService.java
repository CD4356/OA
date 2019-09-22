package com.cd.oa.service;

import com.cd.oa.entity.Employee;

import java.util.List;

public interface EmployeeService {

    void add(Employee employee);

    void edit(Employee employee);

    void remove(String id);

    Employee get(String id);

    List<Employee> getAll();

}
