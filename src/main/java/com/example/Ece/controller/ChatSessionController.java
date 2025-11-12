package com.example.Ece.controller;

import com.example.Ece.common.Result;
import com.example.Ece.entity.ChatSession;
import com.example.Ece.service.ChatSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Date;

@RestController
@RequestMapping("/conversations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8888")
public class ChatSessionController {
    private final ChatSessionService chatSessionService;

    @GetMapping
    public ResponseEntity<Result<List<ChatSession>>> getConversations(@RequestParam String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            Result<List<ChatSession>> result = new Result<>();
            result.setCode("400");
            result.setMsg("用户ID不能为空");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        List<ChatSession> data = chatSessionService.getConversationsByUserId(userId);
        return ResponseEntity.ok(Result.success(data));
    }

    @PostMapping
    public ResponseEntity<Result<ChatSession>> createConversation(@RequestBody ChatSession chatSession) {
        if (chatSession.getUserId() == null || chatSession.getUserId().trim().isEmpty()) {
            Result<ChatSession> result = new Result<>();
            result.setCode("400");
            result.setMsg("用户ID不能为空");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        if (chatSession.getTitle() == null || chatSession.getTitle().trim().isEmpty()) {
            chatSession.setTitle("未命名会话");
        }
        if (chatSession.getCreateTime() == null) {
            chatSession.setCreateTime(new Date());
        }

        boolean saved = chatSessionService.save(chatSession);
        if (saved) {
            return ResponseEntity.ok(Result.success(chatSession));
        } else {
            Result<ChatSession> result = new Result<>();
            result.setCode("500");
            result.setMsg("会话创建失败");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Result<Void>> deleteConversation(@PathVariable String uid) {
        if (uid == null || uid.trim().isEmpty()) {
            Result<Void> result = new Result<>();
            result.setCode("400");
            result.setMsg("会话ID不能为空");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        boolean deleted = chatSessionService.removeById(uid);
        if (deleted) {
            return ResponseEntity.ok(Result.success());
        } else {
            Result<Void> result = new Result<>();
            result.setCode("404");
            result.setMsg("会话不存在");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{uid}/title")
    public ResponseEntity<Result<Boolean>> updateTitle(
            @PathVariable String uid,
            @RequestBody Map<String, String> titleMap) {
        if (uid == null || uid.trim().isEmpty() || titleMap == null || titleMap.get("title") == null || titleMap.get("title").trim().isEmpty()) {
            Result<Boolean> result = new Result<>();
            result.setCode("400");
            result.setMsg("会话ID和标题不能为空");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        boolean success = chatSessionService.updateConversationTitle(uid, titleMap.get("title").trim());
        if (success) {
            return ResponseEntity.ok(Result.success(true));
        } else {
            Result<Boolean> result = new Result<>();
            result.setCode("404");
            result.setMsg("会话不存在");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Result<List<ChatSession>>> searchConversations(
            @RequestParam String userId,
            @RequestParam String keyword) {
        if (userId == null || userId.trim().isEmpty() || keyword == null || keyword.trim().isEmpty()) {
            Result<List<ChatSession>> result = new Result<>();
            result.setCode("400");
            result.setMsg("用户ID和搜索关键词不能为空");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        List<ChatSession> data = chatSessionService.searchConversations(userId, keyword);
        return ResponseEntity.ok(Result.success(data));
    }
}