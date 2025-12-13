package com.xiaobai.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaobai.mybatisplus.entity.User;
import com.xiaobai.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomSqlTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testCustomSql(){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>()
                .like(User::getName,"l")
                        .lt(User::getAge,30);
        userMapper.updateUserAge(lambdaQueryWrapper, 2);
    }
}
