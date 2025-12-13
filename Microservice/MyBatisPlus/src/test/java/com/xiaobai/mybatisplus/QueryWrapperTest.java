package com.xiaobai.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.xiaobai.mybatisplus.entity.User;
import com.xiaobai.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class QueryWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testQueryWrapper() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>()
                .select("id", "name", "age", "email")
                .gt("age", 21)
                .like("name", "o");
        List<User> users = userMapper.selectList(userQueryWrapper);
        Assert.notNull(users, "");
        users.forEach(System.out::println);
    }

    @Test
    public void testUpdateWrapper() {
        User user = new User();
        user.setAge(99);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>()
                .eq("name", "Tom");
        userMapper.update(user, userQueryWrapper);
    }

    @Test
    public void testQueryWrapper2() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>()
                .setSql("age = age + 1");
        userMapper.update(updateWrapper);
    }
}
