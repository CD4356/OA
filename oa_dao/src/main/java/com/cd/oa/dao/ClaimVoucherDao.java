package com.cd.oa.dao;

import com.cd.oa.entity.ClaimVoucher;
import com.cd.oa.entity.DealRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimVoucherDao")
public interface ClaimVoucherDao {

    void insert(ClaimVoucher claimVoucher);

    void update(ClaimVoucher claimVoucher);

    void delete(int id);

    ClaimVoucher select(int id);

    //根据创建人id查看个人报销单
    List<ClaimVoucher> selectByCreateId(String cid);

    //根据待处理人id查看待处理报销单
    List<ClaimVoucher> selectByNextDealId(String ndid);
}
