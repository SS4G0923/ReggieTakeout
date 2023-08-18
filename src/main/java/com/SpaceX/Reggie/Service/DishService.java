package com.SpaceX.Reggie.Service;

import com.SpaceX.Reggie.DTO.DishDto;
import com.SpaceX.Reggie.Entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDto dishDto);

    public void updateWithFlavor(DishDto dishDto);
}
