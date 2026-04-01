package com.example.springRestapi.RESTApi.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
public class ChatService {
    private final ChatClient chatClient;
    public ChatService(ChatClient.Builder builder) {
        ChatMemory chatMemory = MessageWindowChatMemory.builder().build();

        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public String chat(String msg){
        return chatClient.prompt(msg).call().content();
    }

    public String chatWithMemory(String msg,String CId){
        return chatClient.prompt(msg)
                .advisors(a -> a.params(Map.of("chat_cid",CId)))
                .call().content();
    }

    
    // Stage3 : Streaming Concept :

    public Flux<String> chatStream(String msg){
        return chatClient.prompt(msg).stream().content();
    }

}
