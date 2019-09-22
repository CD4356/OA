package com.cd.oa.dao;

import com.cd.oa.entity.ClaimVoucherItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimVoucherItemDao")
public interface ClaimVoucherItemDao {

    void insert(ClaimVoucherItem claimVoucherItem);

    void update(ClaimVoucherItem claimVoucherItem);

    void delete(int id);

    //获取某报销单所属的所有报销单条目
    List<ClaimVoucherItem> selectByClaimVoucher(int claimVoucherId);

}
