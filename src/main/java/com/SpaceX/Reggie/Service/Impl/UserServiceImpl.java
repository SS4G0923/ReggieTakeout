package com.SpaceX.Reggie.Service.Impl;

import com.SpaceX.Reggie.Entity.User;
import com.SpaceX.Reggie.Mapper.UserMapper;
import com.SpaceX.Reggie.Service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
}
