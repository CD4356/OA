package com.cd.oa.controller;

import com.cd.oa.entity.Employee;
import com.cd.oa.service.GlobalService;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class GlobalController {

    @Autowired
    private GlobalService globalService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(HttpSession session, @RequestParam String id, @RequestParam String password){
        Employee employee = globalService.login(id,password);
        if (employee == null) {
            return "redirect:to_login";
        }
        session.setAttribute("employee",employee);
        return "redirect:self";
    }

    @RequestMapping("/self")
    public String self(){
        return  "self";
    }

    @RequestMapping("/quit")
    public String quit(HttpSession session){
        session.setAttribute("employee",null);
        return "redirect:to_login";
    }

    @RequestMapping("/to_change_password")
    public String toChangePassword(){
        return "change_password";
    }

    @RequestMapping("/change_password")
    public String changePassword(HttpSession session, @RequestParam String old, @RequestParam String new1 ,@RequestParam String new2){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee.getPassword().equals(old) && new1.equals(new2)){
            employee.setPassword(new1);
            globalService.changePassword(employee);
            return "redirect:self";
        }
        return "redirect:/to_change_password";
    }


}
