package com.SpaceX.Reggie.Controller;

import com.SpaceX.Reggie.Entity.ShoppingCart;
import com.SpaceX.Reggie.Service.ShoppingCartService;
import com.SpaceX.Reggie.common.BaseContext;
import com.SpaceX.Reggie.common.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info(shoppingCart.toString());
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        Long id = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);

        if (id != null) {
            wrapper.eq(ShoppingCart::getDishId, id);
        }else{
            wrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart shoppingCartServiceOne = shoppingCartService.getOne(wrapper);

        if(shoppingCartServiceOne != null){
            shoppingCartServiceOne.setNumber(shoppingCartServiceOne.getNumber() + 1);
            shoppingCartServiceOne.setAmount(shoppingCartServiceOne.getAmount().add(shoppingCart.getAmount()));
            shoppingCartService.updateById(shoppingCartServiceOne);
        }else {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            shoppingCartServiceOne = shoppingCart;
        }

        return R.success(shoppingCartServiceOne);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        wrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(wrapper);

        return R.success(list);
    }

    @DeleteMapping("/clean")
    public R<String> deleteAll(){
        Long userID = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userID);
        shoppingCartService.remove(wrapper);

        return R.success("清空购物车成功");
    }
}
