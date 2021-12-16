package com.amr.project.converter;

import com.amr.project.model.dto.ChatDto;
import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ChatMapper {
        @Autowired
        UserMapper userMapper;



        public ChatDto chatToChatDTO(Chat chat){
                ChatDto chatDto = new ChatDto();
                chatDto.setId(chat.getId() );
                chatDto.setChatName(chat.getChatName());
                chatDto.setOwner(userMapper.userToChatDTO(chat.getOwner()));
                chatDto.setHash(chat.getHash());
                chatDto.setMembers(userMapper.listUserToListUserChatDTO(chat.getMembers()));
                return chatDto;
        }

        public abstract List<ChatDto> listChatToListChatDTO(List<Chat> chatList) ;
}
