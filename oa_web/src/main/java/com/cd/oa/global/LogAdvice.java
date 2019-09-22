package com.cd.oa.global;

import com.cd.oa.entity.Employee;
import com.cd.oa.entity.Log;
import com.cd.oa.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Component
@Aspect
public class LogAdvice {

    @Autowired
    private LogService logService;

    @AfterReturning(value = "login()||changePassword()")
    public void operationLog(JoinPoint joinPoint){
        HttpSession session = (HttpSession) joinPoint.getArgs()[0];
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
//        HttpServletRequest request = servletRequestAttributes.getRequest();
//        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        Log log = new Log();
        log.setEmployeeId(employee.getId());
        log.setOperationTime(new Date());
        log.setOperation(joinPoint.getSignature().getName());
        logService.add(log);
    }

    @Pointcut(value = "execution(* com.cd.oa.controller.GlobalController.login(..))")
    private void login(){}

    @Pointcut(value = "execution(* com.cd.oa.controller.GlobalController.changePassword(..))")
    private void changePassword(){}



}
