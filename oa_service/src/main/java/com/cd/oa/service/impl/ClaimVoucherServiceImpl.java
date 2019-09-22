package com.cd.oa.service.impl;

import com.cd.oa.dao.ClaimVoucherDao;
import com.cd.oa.dao.ClaimVoucherItemDao;
import com.cd.oa.dao.DealRecordDao;
import com.cd.oa.dao.EmployeeDao;
import com.cd.oa.entity.ClaimVoucher;
import com.cd.oa.entity.ClaimVoucherItem;
import com.cd.oa.entity.DealRecord;
import com.cd.oa.entity.Employee;
import com.cd.oa.global.Contant;
import com.cd.oa.service.ClaimVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service("claimVoucherService")
public class ClaimVoucherServiceImpl implements ClaimVoucherService {

    @Qualifier("claimVoucherDao")
    @Autowired
    private ClaimVoucherDao claimVoucherDao;
    @Qualifier("claimVoucherItemDao")
    @Autowired
    private ClaimVoucherItemDao claimVoucherItemDao;
    @Qualifier("dealRecordDao")
    @Autowired
    private DealRecordDao dealRecordDao;
    @Qualifier("employeeDao")
    @Autowired
    private EmployeeDao employeeDao;

    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        //设置报销单业务
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setNextDealId(claimVoucher.getCreateId());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.insert(claimVoucher);

        //为保存的报销单条目都添加报销单id
        for (ClaimVoucherItem item:items){
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(item);
        }

        //保存报销单创建记录
        DealRecord dealRecord = new DealRecord();
        dealRecord.setClaimVoucherId(claimVoucher.getId());
        dealRecord.setDealId(claimVoucher.getCreateId());
        dealRecord.setDealTime(new Date());
        dealRecord.setDealType(Contant.DEAL_CREATE);
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_CREATED);
        dealRecord.setComment("无");
        dealRecordDao.insert(dealRecord);
    }

    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }

    public List<ClaimVoucherItem> getItems(int claimVoucherId) {
        return claimVoucherItemDao.selectByClaimVoucher(claimVoucherId);
    }

    public List<DealRecord> getRecords(int claimVoucherId) {
        return dealRecordDao.selectByClaimVoucher(claimVoucherId);
    }

    public List<ClaimVoucher> getForSelf(String id) {
        return claimVoucherDao.selectByCreateId(id);
    }

    public List<ClaimVoucher> getForDeal(String id) {
        return claimVoucherDao.selectByNextDealId(id);
    }

    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setNextDealId(claimVoucher.getCreateId());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.update(claimVoucher);

        /** 下面是第一种修改方式：
         *  规则：
         *      1、删除数据库中报销单编号对应的所有条目
         *      2、插入页面修改后提交的新条目集合
         */
        List<ClaimVoucherItem> olds = claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        //删除数据库中原有条目
        for(ClaimVoucherItem old : olds){
            claimVoucherItemDao.delete(old.getId());
        }
        //向数据库中插入新增条目
        for (ClaimVoucherItem item:items){
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(item);
        }

        /** 下面是第二种修改方式：
         *  规则：
         *      1、数据库中没有的条目，而页面提交的数据中有的，则插入
         *      2、数据库中有的条目，而页面提交的数据中没有的，则删除
         *      3、数据库中有的，而页面提交的数据中也有的，则修改
         */
//        for (ClaimVoucherItem old:olds){
//            boolean isHave = false;
//            for (ClaimVoucherItem item:items){
//                //判断数据库中是否存在与新提交条目中id一样的条目
//                if(item.getId()==old.getId()){
//                    isHave = true;
//                    break;
//
//                }
//            }
//            //删除数据库id与新提交条目id不一样的条目
//            if(!isHave){
//                claimVoucherItemDao.delete(old.getId());
//            }
//        }
//        for(ClaimVoucherItem item:items){
//            item.setClaimVoucherId(claimVoucher.getId());
//            if(item.getId()>0){
//                claimVoucherItemDao.update(item);
//            }else {
//                claimVoucherItemDao.insert(item);
//            }
//        }
    }

    //这里传入的id是订单编号
    public void submit(int id) {
        //获取报销单创建人
        ClaimVoucher claimVoucher = claimVoucherDao.select(id);
        Employee employee = employeeDao.select(claimVoucher.getCreateId());

        //设置报销单
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
        claimVoucher.setNextDealId(employeeDao.selectByDepartmentAndPost(employee.getDepartmentId(),Contant.POST_FM).get(0).getId());
        claimVoucherDao.update(claimVoucher);

        //设置处理记录
        DealRecord dealRecord = new DealRecord();
        dealRecord.setClaimVoucherId(id);
        dealRecord.setDealId(employee.getId());
        dealRecord.setDealTime(new Date());
        dealRecord.setDealType(Contant.DEAL_SUBMIT);
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);
        dealRecord.setComment("无");
        dealRecordDao.insert(dealRecord);
    }

    //报销单审核打款
    public void deal(DealRecord dealRecord) {
        //获取报销单
        ClaimVoucher claimVoucher = claimVoucherDao.select(dealRecord.getClaimVoucherId());
        //获取当前处理人
        Employee employee = employeeDao.select(dealRecord.getDealId());
        //设置处理时间
        dealRecord.setDealTime(new Date());

        if (dealRecord.getDealType().equals(Contant.DEAL_PASS)){
            //如果报销金额小于5000或者审核人
            if(claimVoucher.getTotalAmount()<=Contant.LIMIT_CHECK || employee.getPost().equals(Contant.POST_GM)){
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
                claimVoucher.setNextDealId(employeeDao.selectByDepartmentAndPost(null,Contant.POST_CASHIER).get(0).getId());

                dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
            }else{
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
                claimVoucher.setNextDealId(employeeDao.selectByDepartmentAndPost(null,Contant.POST_GM).get(0).getId());

                dealRecord.setDealResult(Contant.CLAIMVOUCHER_RECHECK);
            }
        }else if(dealRecord.getDealType().equals(Contant.DEAL_BACK)){
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);
            claimVoucher.setNextDealId(claimVoucher.getCreateId());

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);
        }else if(dealRecord.getDealType().equals(Contant.DEAL_REJECT)){
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);
            claimVoucher.setNextDealId(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);
        }else if(dealRecord.getDealType().equals(Contant.DEAL_REJECT)){
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);
            claimVoucher.setNextDealId(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);
        }else if(dealRecord.getDealType().equals(Contant.DEAL_PAID)){
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);
            claimVoucher.setNextDealId(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
        }

        claimVoucherDao.update(claimVoucher);
        dealRecordDao.insert(dealRecord);
    }

}
