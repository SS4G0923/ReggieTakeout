package com.SpaceX.Reggie.DTO;


import com.SpaceX.Reggie.Entity.Dish;
import com.SpaceX.Reggie.Entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
