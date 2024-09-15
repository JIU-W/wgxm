package com.itjn.service.impl;

import com.itjn.domain.entity.SysOperLog;
import com.itjn.mapper.SysOperLogMapper;
import com.itjn.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysOperLogServiceImpl implements SysOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    //插入操作日志
    public void insert(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }

}
