package com.xiaobai.mybatisplus;

import com.xiaobai.mybatisplus.entity.User;
import com.xiaobai.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class HelloWordTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect(){
        List<User> users = userMapper.selectList(null);
        Assert.isTrue(5 == users.size(),"");
        users.forEach(System.out::println);
    }

    @Test
    public void testCustomSelect() {
        User jack = userMapper.selectByName("Jack");
        Assert.notNull(jack,"");
        System.out.println(jack);
    }

}
