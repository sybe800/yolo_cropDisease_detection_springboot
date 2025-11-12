package com.example.Ece.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Ece.entity.ChatSession;
import com.example.Ece.entity.ChatMessage;
import com.example.Ece.mapper.ChatSessionMapper;
import com.example.Ece.mapper.ChatMessageMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service
public class ChatSessionService extends ServiceImpl<ChatSessionMapper, ChatSession> {

    @Resource
    private ChatMessageMapper chatMessageMapper;

    // 重写removeById方法，实现删除会话时同时删除关联消息
    @Override
    public boolean removeById(Serializable uid) {
        if (uid instanceof String) {
            // 1. 删除该会话下的所有消息
            LambdaQueryWrapper<ChatMessage> messageQueryWrapper = new LambdaQueryWrapper<>();
            messageQueryWrapper.eq(ChatMessage::getUid, (String) uid);
            chatMessageMapper.delete(messageQueryWrapper);
        }
        // 2. 删除会话本身
        return super.removeById(uid);
    }

    // 1. 根据用户ID查询会话（按创建时间倒序）
    public List<ChatSession> getConversationsByUserId(String userId) {
        LambdaQueryWrapper<ChatSession> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatSession::getUserId, userId)
                .orderByDesc(ChatSession::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }

    // 2. 搜索会话（标题或消息内容包含关键词）
    public List<ChatSession> searchConversations(String userId, String keyword) {
        String likeValue = "%" + keyword + "%";
        LambdaQueryWrapper<ChatSession> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatSession::getUserId, userId)
                .and(wrapper -> wrapper
                        .like(ChatSession::getTitle, likeValue)
                        .or().exists(String.format(
                                "SELECT 1 FROM chat_message m WHERE m.uid = chat_session.uid AND m.content LIKE '%s'",
                                likeValue
                        ))
                )
                .orderByDesc(ChatSession::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }

    // 3. 更新会话标题
    public boolean updateConversationTitle(String uid, String title) {
        ChatSession session = getById(uid);
        if (session == null) return false;
        session.setTitle(title);
        return updateById(session);
    }
}