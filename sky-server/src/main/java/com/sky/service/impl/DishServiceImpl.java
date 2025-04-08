package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setMealDishMapper;
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //add one dish into the table
        dishMapper.insert(dish);

        Long dishId=dish.getId();
        List<DishFlavor> flavors=dishDTO.getFlavors();

        if (flavors!=null&&flavors.size()>0){
            flavors.forEach(dishFlavor ->{
                dishFlavor.setDishId(dishId);
            });
            //add flavor data into the flavor table
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page=dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        //check if there is any dishes that are currently avaiable for sale
        for (Long id:ids){
            Dish dish=dishMapper.getById(id);
            if (dish.getStatus()== StatusConstant.ENABLE){
                //can not be deleted
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //check if in any mealset
        List<Long> setmealIds=setMealDishMapper.getSetmealIdsByDishIds(ids);
        if(setmealIds!=null&&setmealIds.size()>0){
            //in meal set, cant be deleted
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //del dish
        for (Long id : ids) {
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteByDishId(id);
        }
        //del flavor

    }
    @Override
    public DishVO getByIdWithFlavor(Long id) {
        //search dish with id
        Dish dish=dishMapper.getById(id);
        //search flavor with id
        List<DishFlavor> dishFlavors= dishFlavorMapper.getByDishId(id);
        //add into vo
        DishVO dishVO=new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }
    @Override
    public void updateWithFlavor(DishDTO dishDTO){
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //edit the basic value of the dish
        dishMapper.update(dish);
        //delete existing flavor
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
        //add new flavor
        List<DishFlavor> flavors=dishDTO.getFlavors();
        if (flavors!=null&&flavors.size()>0){
            flavors.forEach(dishFlavor ->{
                dishFlavor.setDishId(dishDTO.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    };
    /**
     * get dishes by category id
     * @param categoryId
     * @return
     */
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }
}
