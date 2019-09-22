package com.cd.oa.service.impl;

import com.cd.oa.dao.LogDao;
import com.cd.oa.entity.Log;
import com.cd.oa.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("logService")
public class LogServiceImpl implements LogService {

    @Qualifier("logDao")
    @Autowired
    private LogDao logDao;

    public void add(Log log) {
        logDao.insert(log);
    }

    public void delete(int id) {
        logDao.delete(id);
    }

    public List<Log> getAll(String id) {
        return logDao.selectAll(id);
    }
}
