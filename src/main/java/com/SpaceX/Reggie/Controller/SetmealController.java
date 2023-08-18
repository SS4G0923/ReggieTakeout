package com.SpaceX.Reggie.Controller;

import com.SpaceX.Reggie.DTO.SetmealDto;
import com.SpaceX.Reggie.Entity.Setmeal;
import com.SpaceX.Reggie.Service.CategoryService;
import com.SpaceX.Reggie.Service.SetmealDishService;
import com.SpaceX.Reggie.Service.SetmealService;
import com.SpaceX.Reggie.common.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping//新增套餐
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info(setmealDto.toString());
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        Page<Setmeal> pageInfo = new Page(page, pageSize);
        Page<SetmealDto> pageDtoInfo = new Page<>();

        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Setmeal::getName, name);
        wrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, wrapper);
        BeanUtils.copyProperties(pageInfo, pageDtoInfo, "records");
        List<SetmealDto> setmealDtos = new ArrayList<>();

        pageInfo.getRecords().forEach((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            setmealDto.setCategoryName(categoryService.getById(item.getCategoryId()).getName());
            setmealDtos.add(setmealDto);
        });

        pageDtoInfo.setRecords(setmealDtos);

        return R.success(pageDtoInfo);
    }

    @DeleteMapping
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("删除套餐成功");
    }

    @GetMapping("/list")
    @Cacheable(value = "setmealCache", key = "#setmeal.categoryId + '_' + #setmeal.status")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        wrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        wrapper.orderByDesc(Setmeal::getUpdateTime);
        return R.success(setmealService.list(wrapper));
    }
}
