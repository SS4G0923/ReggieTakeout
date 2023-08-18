package com.SpaceX.Reggie.Controller;

import com.SpaceX.Reggie.DTO.DishDto;
import com.SpaceX.Reggie.Entity.Category;
import com.SpaceX.Reggie.Entity.Dish;
import com.SpaceX.Reggie.Entity.DishFlavor;
import com.SpaceX.Reggie.Service.CategoryService;
import com.SpaceX.Reggie.Service.DishFlavorService;
import com.SpaceX.Reggie.Service.DishService;
import com.SpaceX.Reggie.common.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishFlavorService dishFlavorService;

    @Autowired
    DishService dishService;

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> pageInfoDto = new Page<>();

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Dish::getName, name);
        wrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo, wrapper);
        BeanUtils.copyProperties(pageInfo, pageInfoDto, "records");
        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long id = item.getCategoryId();
            Category category = categoryService.getById(id);

            if(category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());

        pageInfoDto.setRecords(list);
        return R.success(pageInfoDto);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        Dish dish = dishService.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        Category category = categoryService.getById(dish.getCategoryId());
        dishDto.setCategoryName(category.getName());
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, id);
        dishDto.setFlavors(dishFlavorService.list(wrapper));
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("添加成功");
    }

//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//
//        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(dish.getCategoryId()!=null , Dish::getCategoryId , dish.getCategoryId());
//        wrapper.eq(Dish::getStatus, 1);
//        wrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//        List<Dish> list = dishService.list(wrapper);
//
//        return R.success(list);
//    }

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dish.getCategoryId()!=null , Dish::getCategoryId , dish.getCategoryId());
        wrapper.eq(Dish::getStatus, 1);
        wrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(wrapper);

        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long id = item.getCategoryId();
            Category category = categoryService.getById(id);
            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> flavorWrapper = new LambdaQueryWrapper<>();
            flavorWrapper.eq(DishFlavor::getDishId, dishId);
            dishDto.setFlavors(dishFlavorService.list(flavorWrapper));

            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }
}
