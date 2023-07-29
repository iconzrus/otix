package com.yuiybishel.Otix.controller;

import com.yuiybishel.Otix.model.Message;
import com.yuiybishel.Otix.model.User;
import com.yuiybishel.Otix.repository.MessageRepository;
import com.yuiybishel.Otix.repository.UserRepository;
import com.yuiybishel.Otix.service.ChatGPTService;
import com.yuiybishel.Otix.service.ChatService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatGPTService chatGPTService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/message")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        // Отправляем сообщение в ChatGPT и получаем ответ
        String response = chatGPTService.getChatGPTResponse(message.getText());

        // Разбираем ответ от ChatGPT
        JSONObject jsonResponse = new JSONObject(response);
        String chatGPTResponse = jsonResponse.getJSONObject("choices").getJSONArray("text").getString(0);

        // Создаем новое сообщение с ответом от ChatGPT
        Message responseMessage = new Message();
        responseMessage.setText(chatGPTResponse);
        responseMessage.setUser("ChatGPT");

        // Сохраняем сообщение в базе данных
        chatService.saveMessage(responseMessage);

        // Возвращаем ответ в виде сообщения
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(Principal principal) {
        // Загружаем пользователя из базы данных
        User user = userRepository.findByUsername(principal.getName());

        // Получаем все сообщения этого пользователя
        List<Message> messages = messageRepository.findByUser(user);

        // Возвращаем сообщения
        return ResponseEntity.ok(messages);
    }
}