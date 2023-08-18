package com.SpaceX.Reggie.Service.Impl;

import com.SpaceX.Reggie.Entity.DishFlavor;
import com.SpaceX.Reggie.Mapper.DishFlavorMapper;
import com.SpaceX.Reggie.Service.DishFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
