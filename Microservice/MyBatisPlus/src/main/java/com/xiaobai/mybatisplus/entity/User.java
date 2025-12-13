package com.xiaobai.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")  // 指定实体类所属表名
public class User {

    @TableId("id") //指定id字段的值
    private Long id;

    @TableField("name") // 指定字段对应数据库中的列
    private String name;

    private Integer age;

    private String email;

    @TableField(exist = false) // 当某个字段不是数据库中的列的时候可以使用这个方式排除
    private String idNumber;

}
