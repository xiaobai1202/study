package com.xiaobai.mybatisplus.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.xiaobai.mybatisplus.entity.Address;
import com.xiaobai.mybatisplus.entity.User;
import com.xiaobai.mybatisplus.mapper.UserMapper;
import com.xiaobai.mybatisplus.pojo.UserDTO;
import com.xiaobai.mybatisplus.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * 通过实现自己的 {@link IUserService} 并继承mybatis plus 的 {@link ServiceImpl} 类，实现自动的基本增删改查
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Override
    public List<User> queryUserList() {
        return list();
    }

    @Override
    public List<UserDTO> staticDbTool() {
        /**
         * 当需要在一个业务类中查询另一个表的时候，并不能直接用当前继承的IService方法进行，需要借助静态Db工具进行查询
         * 这个例子是在查询用户的业务逻辑中既要返回用户信息也要返回用户地址
         */
        List<User> list = list();
        List<Address> addresses = Db.list(Address.class);
        Map<Long, String> addressMap = addresses.stream()
                .collect(Collectors.toMap(Address::getUserId, Address::getAddress));
        List<UserDTO> userDTOList = BeanUtil.copyToList(list, UserDTO.class);
        userDTOList.forEach(userDTO -> {
            userDTO.setAddress(addressMap.get(userDTO.getId()));
        });
        return userDTOList;
    }
}
