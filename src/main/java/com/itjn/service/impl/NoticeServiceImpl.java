package com.itjn.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itjn.common.context.BaseContext;
import com.itjn.domain.entity.User;
import com.itjn.mapper.NoticeMapper;
import com.itjn.service.NoticeService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itjn.domain.entity.Notice;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告信息表业务处理
 **/
@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Autowired
    private UserInfoServiceImpl userInfoService;
    /**
     * 新增
     */
    public void add(Notice notice) {
        //设置创建时间
        notice.setTime(DateUtil.today());
        //设置创建人
        Integer currentUserId = BaseContext.getCurrentId();
        User currentUser = userInfoService.selectById(currentUserId);
        notice.setUser(currentUser.getUserName());
        noticeMapper.insert(notice);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        noticeMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            noticeMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Notice notice) {
        noticeMapper.updateById(notice);
    }

    /**
     * 根据ID查询
     */
    public Notice selectById(Integer id) {
        return noticeMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Notice> selectAll(Notice notice) {
        return noticeMapper.selectAll(notice);
    }

    /**
     * 分页查询
     */
    public PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = noticeMapper.selectAll(notice);
        return PageInfo.of(list);
    }

}
