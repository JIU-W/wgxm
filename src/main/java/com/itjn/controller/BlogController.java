package com.itjn.controller;

import com.itjn.common.Result;
import com.itjn.common.annotation.Log;
import com.itjn.common.enums.BusinessType;
import com.itjn.domain.entity.Notice;
import com.itjn.mapper.BlogMapper;
import com.itjn.service.BlogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    /**
     * 更新浏览量时，更新redis中的数据
     * @param id
     * @return
     */
    @Log(title = "修改浏览量", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "修改浏览量")
    @PutMapping("/updateReadCount/{id}")
    public Result updateReadCountById(@PathVariable("id") Integer id) {
        blogService.updateReadCountById(id);
        return Result.success();
    }

    @Log(title = "查询浏览量", businessType = BusinessType.QUERY)
    @ApiOperation(value = "查询浏览量")
    @GetMapping("/selectReadCount/{id}")
    public Result selectReadCountById(@PathVariable("id") Integer id) {
        Integer readCount = blogService.selectReadCountById(id);
        return Result.success(readCount);
    }

}
