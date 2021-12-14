package com.amr.project.webapp.controller;


import com.amr.project.dao.abstracts.ChatDao;
import com.amr.project.model.chat.ChatNotification;
import com.amr.project.model.chat.ChatSTOMPMessage;
import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.Message;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ChatService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatMessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;


    @MessageMapping("/ws")
    public void processMessage(@Payload ChatSTOMPMessage stompMessage, Principal principal  ) throws Exception {
       // System.out.println(stompMessage.getMessage());

        Chat chat = chatService.getChatById(stompMessage.getChatId() );




        Message saved = chatService.saveMessage(stompMessage);

        User owner = chat.getOwner();


        for (User user : chat.getMembers()) {
            messagingTemplate.convertAndSendToUser(
                    user.getUsername(),"/queue/messages",
                    new ChatNotification(
                            saved.getChat().getId().toString(),
                            saved.getFrom().getId().toString(),
                            saved.getFrom().getFirstName()));

        }

        messagingTemplate.convertAndSendToUser(
                //saved.getChat().getId().toString(),
                owner.getUsername(),
                "/queue/messages",
                new ChatNotification(
                        saved.getChat().getId().toString(),
                        saved.getFrom().getId().toString(),
                        saved.getFrom().getFirstName()));

    }


}
