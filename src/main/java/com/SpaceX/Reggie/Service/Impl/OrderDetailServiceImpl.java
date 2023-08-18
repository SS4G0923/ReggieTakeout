package com.SpaceX.Reggie.Service.Impl;

import com.SpaceX.Reggie.Entity.OrderDetail;
import com.SpaceX.Reggie.Mapper.OrderDetailMapper;
import com.SpaceX.Reggie.Service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService{
}
