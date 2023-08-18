package com.SpaceX.Reggie.Service.Impl;

import com.SpaceX.Reggie.Entity.ShoppingCart;
import com.SpaceX.Reggie.Mapper.ShoppingCartMapper;
import com.SpaceX.Reggie.Service.ShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
