package com.SpaceX.Reggie.Service.Impl;

import com.SpaceX.Reggie.Entity.Employee;
import com.SpaceX.Reggie.Mapper.EmployeeMapper;
import com.SpaceX.Reggie.Service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
