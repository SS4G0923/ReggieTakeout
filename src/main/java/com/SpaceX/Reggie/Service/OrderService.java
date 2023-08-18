package com.SpaceX.Reggie.Service;

import com.SpaceX.Reggie.Entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderService extends IService<Orders> {

    public void submit(Orders orders);
}
