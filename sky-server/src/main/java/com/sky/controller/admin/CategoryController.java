package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    @ApiOperation("add a new catogory")
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        log.info("new :{}",categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("query by page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("query by page：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);}

    @DeleteMapping
    @ApiOperation("Delete a category")
    public Result<String> deleteById(Long id){
        log.info("Delete a category：{}", id);
        categoryService.deleteById(id);
        return Result.success();}


    @PostMapping("/status/{status}")
    @ApiOperation("enable or disable a category")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id){
        log.info("enable or disable a category：{}", id);
        categoryService.startOrStop(status,id);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("search category base on type")
    public Result<List<Category>> list(Integer type){
        log.info("search category base on type");
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
