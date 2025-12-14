package com.xiaobai.mybatisplus.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobai.mybatisplus.entity.User;
import com.xiaobai.mybatisplus.pojo.UserDTO;

import java.util.List;

/**
 * 需要继承 {@link IService} 来继承基本增删改查方法
 */

public interface IUserService extends IService<User> {

    List<User> queryUserList();

    List<UserDTO> staticDbTool();
}
