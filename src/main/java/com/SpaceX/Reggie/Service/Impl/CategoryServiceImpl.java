package com.SpaceX.Reggie.Service.Impl;

import com.SpaceX.Reggie.Entity.Category;
import com.SpaceX.Reggie.Entity.Dish;
import com.SpaceX.Reggie.Entity.Setmeal;
import com.SpaceX.Reggie.Mapper.CategoryMapper;
import com.SpaceX.Reggie.Service.CategoryService;
import com.SpaceX.Reggie.Service.DishService;
import com.SpaceX.Reggie.Service.SetmealService;
import com.SpaceX.Reggie.common.CustomException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
        dishWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishWrapper);
        if(count1 > 0) {
            throw new CustomException("该分类下有菜品，不能删除");
        }
        LambdaQueryWrapper<Setmeal> setMealWrapper = new LambdaQueryWrapper<>();
        setMealWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setMealWrapper);
        if(count2 > 0){
            throw new CustomException("该分类下有套餐，不能删除");
        }
        super.removeById(id);
    }
}
