package com.xiaobai.elasticsearch.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemDoc {

    private String id;

    private String name;

    private Integer price;

    private String image;

    private String category;

    private String brand;

    private Integer sold;

    private Integer commentCount;

    private Boolean isAD;

    private LocalDateTime updateTime;

}
