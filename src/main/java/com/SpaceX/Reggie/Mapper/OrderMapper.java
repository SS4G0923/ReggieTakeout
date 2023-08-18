package com.SpaceX.Reggie.Mapper;

import com.SpaceX.Reggie.Entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
