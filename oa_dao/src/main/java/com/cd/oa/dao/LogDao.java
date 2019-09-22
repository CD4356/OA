package com.cd.oa.dao;

import com.cd.oa.entity.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("logDao")
public interface LogDao {

    void insert(Log log);

    void delete(int id);

    List<Log> selectAll(String id);

}
