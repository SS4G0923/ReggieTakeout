package com.SpaceX.Reggie.DTO;


import com.SpaceX.Reggie.Entity.Setmeal;
import com.SpaceX.Reggie.Entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
