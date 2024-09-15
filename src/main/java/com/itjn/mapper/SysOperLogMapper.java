package com.itjn.mapper;

import com.itjn.domain.entity.SysOperLog;
import org.apache.ibatis.annotations.Insert;

public interface SysOperLogMapper {

    void insert(SysOperLog sysOperLog);

}
