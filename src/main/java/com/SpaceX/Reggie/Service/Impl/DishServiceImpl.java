package com.SpaceX.Reggie.Service.Impl;

import com.SpaceX.Reggie.DTO.DishDto;
import com.SpaceX.Reggie.Entity.Dish;
import com.SpaceX.Reggie.Entity.DishFlavor;
import com.SpaceX.Reggie.Mapper.DishMapper;
import com.SpaceX.Reggie.Service.DishFlavorService;
import com.SpaceX.Reggie.Service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService{

    @Autowired
    DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long dishID = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishID);
        }
        dishFlavorService.saveBatch(flavors);
    }

    @Transactional
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(wrapper);
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach((item) -> {
            item.setDishId(dishDto.getId());
        });
        dishFlavorService.saveBatch(flavors);
    }


}
