package com.example.Ece.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.util.Date;

@Data
// 去掉 @TableName 注解，依赖默认规则：ChatSession → chat_session
public class ChatSession {
    @TableId(type = IdType.ASSIGN_UUID)
    private String uid;
    private String userId;
    private String title;
    private Date createTime;
}