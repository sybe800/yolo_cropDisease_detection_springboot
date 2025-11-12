// ChatMessageService.java（修改后）
package com.example.Ece.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Ece.entity.ChatMessage;
import com.example.Ece.mapper.ChatMessageMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatMessageService extends ServiceImpl<ChatMessageMapper, ChatMessage> {

    // 根据会话ID查询消息（按时间正序）
    public List<ChatMessage> getMessagesByUid(String uid) {
        LambdaQueryWrapper<ChatMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatMessage::getUid, uid)
                .orderByAsc(ChatMessage::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }
}