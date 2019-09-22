package com.cd.oa.service;

import com.cd.oa.entity.Employee;

public interface GlobalService {

    //登陆
    Employee login(String id,String password);

    //修改密码
    void changePassword(Employee employee);

}
