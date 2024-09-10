package com.itjn.service;



import com.github.pagehelper.PageInfo;
import com.itjn.domain.entity.Notice;

import java.util.List;

public interface NoticeService {

    /**
     * 新增
     */
    public void add(Notice notice);

    /**
     * 删除
     */
    void deleteById(Integer id);

    /**
     * 批量删除
     */
    void deleteBatch(List<Integer> ids);

    /**
     * 修改
     */
    void updateById(Notice notice);

    /**
     * 根据ID查询
     */
    Notice selectById(Integer id);

    /**
     * 查询所有
     */
    List<Notice> selectAll(Notice notice);

    /**
     * 分页查询
     */
    PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize);


}
