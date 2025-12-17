// com.example.Ece.entity.ChinaCity.java
package com.example.Ece.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("china_cities") // 对应之前的城市表
public class ChinaCity {
    @TableId(type = IdType.AUTO)
    private Integer cityId;
    private Integer provinceId;
    private String cityName;
}