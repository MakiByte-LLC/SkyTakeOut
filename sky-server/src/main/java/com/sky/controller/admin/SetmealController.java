package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @PostMapping
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("add a new setmeal:{}", setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }
    /**
     * query by page
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
    /**
     * batch delete setmeal
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids){
        setmealService.deleteBatch(ids);
        return Result.success();
    }
    /**
     * search setmeal basing on id to display data in edit page
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }
    /**
     * edit the setmeal
     * @param setmealDTO
     * @return
     */
    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        setmealService.update(setmealDTO);
        return Result.success();
    }
    /**
     * start or stop to sell a mealset
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        setmealService.startOrStop(status, id);
        return Result.success();
    }
}
