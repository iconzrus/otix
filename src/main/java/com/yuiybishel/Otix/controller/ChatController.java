package com.yuiybishel.Otix.controller;

import com.yuiybishel.Otix.model.Message;
import com.yuiybishel.Otix.model.User;
import com.yuiybishel.Otix.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {

    private final MessageRepository messageRepository;

    @Autowired
    public ChatController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/chat")
    public String getChatPage(Model model) {
        List<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        model.addAttribute("messageForm", new Message());
        return "chat";
    }

    @PostMapping("/chat")
    public String postMessage(@ModelAttribute("messageForm") Message message, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        message.setUser(user);
        messageRepository.save(message);
        return "redirect:/chat";
    }

}