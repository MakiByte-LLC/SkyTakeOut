package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * insert a new category
     * @param category
     */
    @Insert("insert into category(name, type ,sort,status, create_time, update_time, create_user, update_user) "+
            "values(#{name},#{type},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    /**
     * search category
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * delete category
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    /**
     * update category data based on id
     * @param category
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);
    /**
     * given the type,get all categories belong to that type
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}
