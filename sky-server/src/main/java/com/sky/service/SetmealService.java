package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;
public interface SetmealService {
    void saveWithDish(SetmealDTO setmealDTO);
    /**
     * query by page
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
    /**
     * batch delete setmeal
     * @param ids
     */
    void deleteBatch(List<Long> ids);
    /**
     * basing on id to search for the relations between dishes and setmeals
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * edit the setmeal
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);
    /**
     * start or stop to sell a meal set
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}
