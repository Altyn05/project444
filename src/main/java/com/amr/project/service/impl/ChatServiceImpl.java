package com.amr.project.service.impl;

import com.amr.project.converter.ChatMapper;
import com.amr.project.converter.UserMapper;
import com.amr.project.dao.abstracts.ChatDao;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.chat.ChatSTOMPMessage;
import com.amr.project.model.dto.ChatDto;
import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.Message;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ChatService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    private ChatDao chatDao;
    private UserDao userDao;
    private UserMapper userMapper;
    private ChatMapper chatMapper;

    public ChatServiceImpl(ChatDao chatDao, UserDao userDao, UserMapper userMapper, ChatMapper chatMapper) {
        this.chatDao = chatDao;
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.chatMapper = chatMapper;
    }

    public Message saveMessage(ChatSTOMPMessage stompMessage){
        // здесь нужна проверка на принадлежность чату (Chat.owner == stompMessage.getFromUserId()) || stompMessage.getFromUserId() in Chat.members!

        long chatId = stompMessage.getChatId();
        long user_id = stompMessage.getFromUserId();
        Message message = new Message();
        Chat chat  =  chatDao.getChatById(chatId);
        User user  = userDao.findById(user_id);
        message.setChat(chat);
        message.setFrom(user);
        message.setTo(user);
        message.setViewed(false);
        message.setTextMessage(stompMessage.getMessage());
        chatDao.saveMessage(message);
        return message;
    }

    public List<ChatDto> getChatByUser(Long user_id){

        List<Chat> listChat = chatDao.getListChatsByUser(userDao.findById(user_id));
        for (Chat chat:
             listChat) {
            System.out.println(chat.getChatName()+ " " + chatMapper.chatToChatDTO(chat).getChatName());

        }
        List<ChatDto> chatDtos = chatMapper.listChatToListChatDTO(listChat);
        for (ChatDto chat:
                chatDtos) {
            System.out.println(chat.getChatName());
        }

        return  chatDtos;
    }

    public ChatDto addUser2Chat(User ownerChat, User user ){
        //нужна проверка на существующий
        System.out.println(ownerChat.getUsername()+ "<-" +user.getUsername());
        Chat chat = new Chat();
        if (ownerChat.getId() != user.getId()) {
            chat.setChatName(user.getFirstName() + " " + user.getLastName());
            chat.setOwner(ownerChat);
            List<User> members = new ArrayList<User>();
            members.add(user);
            chat.setMembers(members);
            chatDao.persist(chat);
        }
        return chatMapper.chatToChatDTO(chat);
    }
    public List<Message> getMessageList(Long chat_id){
        return chatDao.getChatById(chat_id).getMessages();
    }


    public Chat getChatById(Long id){
        return chatDao.getChatById(id);
    }


}
