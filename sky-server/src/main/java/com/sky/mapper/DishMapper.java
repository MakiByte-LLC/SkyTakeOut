package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    /**
     * search dish by dishname
     * @param name
     * @return dish
     */
    Dish getByDishname(String name);

    @Select("select  * from dish where id=#{id}")
    Dish getById(Long id);
    /**
     * search how many dishes are in the category
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id=#{categoryId}")
    Integer countByCategoryId(Long categoryId);

    void insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);
    @Delete("delete from dish where id=#{id}")
    void deleteById(Long id);
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);
    /**
     * dynamic search dishes
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);
    /**
     * get dishes by set meal id
     * @param setmealId
     * @return
     */
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);
}
