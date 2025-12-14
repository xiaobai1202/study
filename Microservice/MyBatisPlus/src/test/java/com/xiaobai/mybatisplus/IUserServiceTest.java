package com.xiaobai.mybatisplus;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaobai.mybatisplus.entity.User;
import com.xiaobai.mybatisplus.pojo.UserDTO;
import com.xiaobai.mybatisplus.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class IUserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testBasicSelect(){
        List<User> allUsers = userService.queryUserList();
        Assert.notNull(allUsers,"");
        allUsers.forEach(System.out::println);
    }

    @Test
    public void testUserWithAddress() {
        List<UserDTO> userDTOList = userService.staticDbTool();
        Assert.notNull(userDTOList,"");
        userDTOList.forEach(System.out::println);
    }
}
