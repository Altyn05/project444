package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.Message;
import com.amr.project.model.entity.User;

import java.util.List;


public interface ChatDao extends ReadWriteDao<Chat, Long> {


    List<Chat> getListChatsByUser(User user);
    Chat getChatById(Long chatId);

    Message saveMessage(Message message);


}
