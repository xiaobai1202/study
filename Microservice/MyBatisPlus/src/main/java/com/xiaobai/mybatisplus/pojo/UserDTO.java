package com.xiaobai.mybatisplus.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UserDTO {

    private Long id;

    private String name;

    private Integer age;

    private String email;

    private String address;
}
