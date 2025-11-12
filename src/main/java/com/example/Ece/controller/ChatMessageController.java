package com.example.Ece.controller;

import com.example.Ece.common.Result;
import com.example.Ece.entity.ChatMessage;
import com.example.Ece.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Date;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8888")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    @GetMapping
    public ResponseEntity<Result<List<ChatMessage>>> getMessages(@RequestParam String uid) {
        if (uid == null || uid.trim().isEmpty()) {
            Result<List<ChatMessage>> result = new Result<>();
            result.setCode("400");
            result.setMsg("会话ID不能为空");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        List<ChatMessage> data = chatMessageService.getMessagesByUid(uid);
        return ResponseEntity.ok(Result.success(data));
    }

    @PostMapping
    public ResponseEntity<Result<ChatMessage>> saveMessage(@RequestBody ChatMessage chatMessage) {
        if (chatMessage.getUid() == null || chatMessage.getUid().trim().isEmpty() ||
                chatMessage.getUserId() == null || chatMessage.getUserId().trim().isEmpty() ||
                chatMessage.getContent() == null || chatMessage.getContent().trim().isEmpty()) {
            Result<ChatMessage> result = new Result<>();
            result.setCode("400");
            result.setMsg("会话ID、用户ID和消息内容不能为空");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        if (chatMessage.getCreateTime() == null) {
            chatMessage.setCreateTime(new Date());
        }

        boolean saved = chatMessageService.save(chatMessage);
        if (saved) {
            return ResponseEntity.ok(Result.success(chatMessage));
        } else {
            Result<ChatMessage> result = new Result<>();
            result.setCode("500");
            result.setMsg("消息保存失败");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}