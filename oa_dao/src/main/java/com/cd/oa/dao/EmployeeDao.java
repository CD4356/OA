package com.cd.oa.dao;

import com.cd.oa.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("employeeDao")
public interface EmployeeDao {

    void insert(Employee employee);

    void update(Employee employee);

    void delete(String id);

    Employee select(String id);

    List<Employee> selectAll();

    /**
     * 多参数时,注意一定要在参数前加上@Param来匹配相同参数名的参数,
     * 即@Param注解内的名称必须与xml映射文件中的属性名一致
     * 则会报: Parameter 'XXX' not found. Available parameters are [1, 0, param1, param2] 参数不匹配错误
     */
    List<Employee> selectByDepartmentAndPost(@Param("did") String did,@Param("post") String post);

}
