package com.SpaceX.Reggie.DTO;


import com.SpaceX.Reggie.Entity.OrderDetail;
import com.SpaceX.Reggie.Entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
