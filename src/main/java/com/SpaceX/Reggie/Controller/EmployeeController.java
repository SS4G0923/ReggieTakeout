package com.SpaceX.Reggie.Controller;

import com.SpaceX.Reggie.Entity.Employee;
import com.SpaceX.Reggie.Service.EmployeeService;
import com.SpaceX.Reggie.common.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if(emp == null){
            return R.error("用户名不存在");
        }
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }
        if(emp.getStatus() == 0){
            return R.error("账号已被禁用");
        }
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        //log.info("新增员工，员工信息{}", employee.toString());

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser((Long)request.getSession().getAttribute("employee"));
//        employee.setUpdateUser((Long)request.getSession().getAttribute("employee"));
        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name){

        log.info("page:{} pageSize:{} name:{}",page, pageSize, name);
        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo, lambdaQueryWrapper);

        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        //根据ID来修改员工信息
        log.info(employee.toString());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return R.success("修改员工信息成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据ID查询员工信息");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询到员工信息");
    }
}
