package com.example.springRestapi.RESTApi.controller;

import com.example.springRestapi.RESTApi.dto.ChatRequest;
import com.example.springRestapi.RESTApi.dto.ChatResponse;
import com.example.springRestapi.RESTApi.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }
    @PostMapping("/ai/chat")
    public ChatResponse chat(@RequestBody ChatRequest chatRequest){
        String reply = chatService.chat(chatRequest.msg());
        return new ChatResponse(reply,null);
    }

    @PostMapping("/ai/chat/memory")
    public ChatResponse memory(@RequestBody ChatRequest chatRequest){
        String reply = chatService.chatWithMemory(chatRequest.msg(),chatRequest.CId());
        return new ChatResponse(reply,chatRequest.CId());
    }

    @PostMapping(value = "/chat/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestBody ChatRequest chatRequest){
        return chatService.chatStream(chatRequest.msg());
    }
}
