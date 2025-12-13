package com.xiaobai.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaobai.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {

    User selectByName(String name);

    /*
        使用queryWrapper构建复杂的where查询条件，然后自己自定义剩下的sql语句部分
        通过手动编写mapper 中的sql  通过ew 对象注入条件，注意条件参数一定是ew
     */

    void updateUserAge(@Param("ew") LambdaQueryWrapper<User> queryWrapper, @Param("age") int age);
}
