package com.amr.project.service.abstracts;

import com.amr.project.model.chat.ChatSTOMPMessage;
import com.amr.project.model.dto.ChatDto;
import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.Message;
import com.amr.project.model.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


public interface ChatService {
    List<ChatDto> getChatByUser(Long user_id);
    ChatDto addUser2Chat(User ownerChat, User user );
    List<Message> getMessageList(Long chat_id);
    Message saveMessage(ChatSTOMPMessage message);
    Chat getChatById(Long id);
}
