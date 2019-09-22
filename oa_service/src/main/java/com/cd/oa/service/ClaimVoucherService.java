package com.cd.oa.service;

import com.cd.oa.entity.ClaimVoucher;
import com.cd.oa.entity.ClaimVoucherItem;
import com.cd.oa.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherService {

    //保存报销单
    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    //获取报销单
    ClaimVoucher get(int id);

    //获取报销单条目
    List<ClaimVoucherItem> getItems(int claimVoucherId);

    //获取处理记录
    List<DealRecord> getRecords(int claimVoucherId);

    //获取个人报销单
    List<ClaimVoucher> getForSelf(String id);

    //获取待处理报销单
    List<ClaimVoucher> getForDeal(String id);

    //修改报销单
    void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    //提交表单
    void submit(int id);

    //报销单审核与打款
    void deal(DealRecord dealRecord);
}
