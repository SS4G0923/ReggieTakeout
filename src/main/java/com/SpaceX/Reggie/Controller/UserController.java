package com.SpaceX.Reggie.Controller;

import com.SpaceX.Reggie.Entity.User;
import com.SpaceX.Reggie.Service.UserService;
import com.SpaceX.Reggie.Utils.ValidateCodeUtils;
import com.SpaceX.Reggie.common.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            session.setAttribute(phone, code);
            return R.success("手机验证码发送成功");
        }
        return R.error("手机验证码发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        String phone = (String) map.get("phone");
        String code = "12345";
        if(StringUtils.isNotEmpty(phone) && StringUtils.isNotEmpty(code)){
            String sessionCode = "12345";
            if(StringUtils.isNotEmpty(sessionCode) && sessionCode.equals(code)){
                LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(User::getPhone, phone);
                User user = userService.getOne(wrapper);
                if(user == null){
                    user = new User();
                    user.setPhone(phone);
                    userService.save(user);
                }
                session.setAttribute("user", user.getId());
                return R.success(user);
            }
        }
        return R.error("登录失败");
    }
 }
