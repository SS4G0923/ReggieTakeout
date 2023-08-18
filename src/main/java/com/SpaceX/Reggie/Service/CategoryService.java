package com.SpaceX.Reggie.Service;

import com.SpaceX.Reggie.Entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CategoryService extends IService<Category>{
    public void remove(Long id);
}
