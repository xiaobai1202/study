package com.xiaobai.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("address")
public class Address {
    @TableId
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField
    private String address;

}
