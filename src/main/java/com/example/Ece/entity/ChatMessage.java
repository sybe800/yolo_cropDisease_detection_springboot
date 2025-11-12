package com.example.Ece.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.util.Date;

@Data
// 去掉 @TableName 注解，依赖默认规则：ChatMessage → chat_message
public class ChatMessage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String uid;
    private String userId;
    private String role;
    private String content;
    private Date createTime;
}