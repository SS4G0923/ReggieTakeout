package com.SpaceX.Reggie.Service.Impl;

import com.SpaceX.Reggie.DTO.SetmealDto;
import com.SpaceX.Reggie.Entity.SetmealDish;
import com.SpaceX.Reggie.Mapper.SetmealDishMapper;
import com.SpaceX.Reggie.Service.SetmealDishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {

}
