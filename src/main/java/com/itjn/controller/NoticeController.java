package com.itjn.controller;


import com.itjn.service.NoticeService;
import com.github.pagehelper.PageInfo;
import com.itjn.common.Result;
import com.itjn.domain.entity.Notice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 公告信息表前端操作接口
 **/
@Api(tags = "公告信息相关接口")
@RestController
@RequestMapping("/notice")
//@CrossOrigin(origins = "*", maxAge = 3600)//允许跨域
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     * 新增
     */
    @ApiOperation(value = "新增公告")
    @PostMapping("/add")
    public Result add(@RequestBody Notice notice) {
        noticeService.add(notice);
        return Result.success();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "根据id删除公告")
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        noticeService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除公告")
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        noticeService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "根据id修改公告")
    @PutMapping("/update")
    public Result updateById(@RequestBody Notice notice) {
        noticeService.updateById(notice);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @ApiOperation(value = "根据id查询公告")
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Notice notice = noticeService.selectById(id);
        return Result.success(notice);
    }

    /**
     * 查询所有
     */
    @ApiOperation(value = "查询所有公告信息")
    @GetMapping("/selectAll")
    public Result selectAll(Notice notice) {
        List<Notice> list = noticeService.selectAll(notice);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询公告信息")
    @GetMapping("/selectPage")
    public Result selectPage(Notice notice,
                             @RequestParam Integer pageNum,
                             @RequestParam Integer pageSize) {
        PageInfo<Notice> page = noticeService.selectPage(notice, pageNum, pageSize);
        return Result.success(page);
    }

}
