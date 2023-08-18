package com.SpaceX.Reggie.Service;

import com.SpaceX.Reggie.DTO.SetmealDto;
import com.SpaceX.Reggie.Entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);
}
