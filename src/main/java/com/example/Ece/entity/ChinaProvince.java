// com.example.Ece.entity.ChinaProvince.java
package com.example.Ece.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("china_provinces") // 对应之前的省级表
public class ChinaProvince {
    @TableId(type = IdType.AUTO)
    private Integer provinceId;
    private String provinceName;
}