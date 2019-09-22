package com.cd.oa.service;

import com.cd.oa.entity.Log;

import java.util.List;

public interface LogService {

    void add(Log log);

    void delete(int id);

    List<Log> getAll(String id);
}
