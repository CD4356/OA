package com.cd.oa.controller;

import com.cd.oa.entity.Department;
import com.cd.oa.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller("departmentController")
@RequestMapping("/department")
public class DepartmentController {

    @Qualifier("departmentService")
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("list",departmentService.getAll());
        return "department_list";
    }

    @RequestMapping("/toAdd")
    public String toAdd(Map<String,Object> map){
//        map.put("department",new Department());
        return "department_add";
    }

    @RequestMapping("/add")
    public String add(Department department){
        departmentService.add(department);
        return "redirect:/department/list";
    }

    @RequestMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable("id")String id, Map<String,Object> map){
        map.put("depart",departmentService.get(id));
        return "department_update";
    }

    @RequestMapping("/update")
    public String update(Department department, Map<String,Object> map){
        departmentService.edit(department);
        return "redirect:/department/list";
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id")String id){
        departmentService.remove(id);
        return "redirect:/department/list";
    }


}
