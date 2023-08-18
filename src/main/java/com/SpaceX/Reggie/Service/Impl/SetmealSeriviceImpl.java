package com.SpaceX.Reggie.Service.Impl;

import com.SpaceX.Reggie.DTO.SetmealDto;
import com.SpaceX.Reggie.Entity.Setmeal;
import com.SpaceX.Reggie.Entity.SetmealDish;
import com.SpaceX.Reggie.Mapper.SetmealMapper;
import com.SpaceX.Reggie.Service.SetmealDishService;
import com.SpaceX.Reggie.Service.SetmealService;
import com.SpaceX.Reggie.common.CustomException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SetmealSeriviceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService{

    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Setmeal::getId, ids);
        wrapper.eq(Setmeal::getStatus, 1);
        int count = this.count(wrapper);
        if(count > 0){
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(wrapper1);
    }
}
