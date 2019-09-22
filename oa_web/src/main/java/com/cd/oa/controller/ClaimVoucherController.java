package com.cd.oa.controller;

import com.cd.oa.dto.ClaimVoucherInfo;
import com.cd.oa.entity.DealRecord;
import com.cd.oa.entity.Employee;
import com.cd.oa.global.Contant;
import com.cd.oa.service.ClaimVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {

    @Autowired
    private ClaimVoucherService claimVoucherService;

    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("items", Contant.getItems());
        map.put("info",new ClaimVoucherInfo());
        return "claim_voucher_add";
    }

    @RequestMapping("/add")
    public String add(HttpSession session,ClaimVoucherInfo info){
        Employee employee = (Employee) session.getAttribute("employee");
        info.getClaimVoucher().setCreateId(employee.getId());
        claimVoucherService.save(info.getClaimVoucher(),info.getItems());
        return "redirect:/claim_voucher/deal";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable int id, Map<String,Object> map){
        map.put("claimVoucher",claimVoucherService.get(id));
        map.put("items",claimVoucherService.getItems(id));
        map.put("records",claimVoucherService.getRecords(id));
        return "claim_voucher_detail";
    }

    @RequestMapping("/self")
    public String self(HttpSession session, Map<String,Object> map){
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list",claimVoucherService.getForSelf(employee.getId()));
        return "claim_voucher_self";
    }

    @RequestMapping("/deal")
    public String deal(HttpSession session, Map<String,Object> map){
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list",claimVoucherService.getForDeal(employee.getId()));
        return "claim_voucher_deal";
    }

    @RequestMapping("/to_update/{id}")
    public String toUpdate(@PathVariable int id,Map<String,Object> map){
        map.put("items", Contant.getItems());
        ClaimVoucherInfo info = new ClaimVoucherInfo();
        info.setClaimVoucher(claimVoucherService.get(id));
        info.setItems(claimVoucherService.getItems(id));
        map.put("info",info);
        return "claim_voucher_update";
    }

    @RequestMapping("/update")
    public String update(HttpSession session,ClaimVoucherInfo info){
        Employee employee = (Employee) session.getAttribute("employee");
        //设置创建人
        info.getClaimVoucher().setCreateId(employee.getId());
        claimVoucherService.update(info.getClaimVoucher(),info.getItems());
        return "redirect:/claim_voucher/deal";
    }

    @RequestMapping("/submit/{id}")
    public String submit(@PathVariable("id") int id){
        claimVoucherService.submit(id);
        return "redirect:/claim_voucher/deal";
    }

    @RequestMapping("/to_check/{id}")
    public String toCheck(@PathVariable("id") int id,Map<String,Object> map){
        map.put("claimVoucher",claimVoucherService.get(id));
        map.put("items",claimVoucherService.getItems(id));
        map.put("records",claimVoucherService.getRecords(id));
        DealRecord dealRecord =new DealRecord();
        dealRecord.setClaimVoucherId(id);
        map.put("record",dealRecord);
        return "claim_voucher_check";
    }

    @RequestMapping("/check")
    public String check(HttpSession session, DealRecord dealRecord){
        Employee employee = (Employee)session.getAttribute("employee");
        dealRecord.setDealId(employee.getId());
        claimVoucherService.deal(dealRecord);
        return "redirect:/claim_voucher/deal";
    }

}
