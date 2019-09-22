package com.cd.oa.controller;

import com.cd.oa.entity.Employee;
import com.cd.oa.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("log")
public class LogController {

    @Autowired
    private LogService logService;

    @RequestMapping("/list")
    public String list(HttpSession session,Map<String, Object> map){
        Employee employee = (Employee) session.getAttribute("employee");
        String id = employee.getId();
        map.put("list",logService.getAll(id));
        return "log_list";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        logService.delete(id);
        return "redirect:/log/list";
    }



}
