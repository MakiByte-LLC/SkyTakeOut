package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("add new dish : {}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }
    @GetMapping("/page")
    public Result<PageResult>page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("page query : {}", dishPageQueryDTO);
        PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * batch delete
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        log.info("delete dish : {}", ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("get dish by id : {}", id);
        val dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("update dish : {}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }
    /**
     * get dish by category id
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }
}
