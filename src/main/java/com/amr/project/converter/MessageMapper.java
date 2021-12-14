package com.amr.project.converter;

import com.amr.project.model.dto.MessageDTO;
import com.amr.project.model.entity.Message;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MessageMapper {



    public  MessageDTO messageToMessageDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setTextMessage(message.getTextMessage());
        messageDTO.setFrom(message.getFrom().getId());
        messageDTO.setTo(message.getTo().getId());
        messageDTO.setChatId(message.getChat().getId());
        messageDTO.setViewed(message.isViewed());
        return messageDTO;
    }

    public abstract List<MessageDTO> listMessageToListMessageDTO(List<Message> listMessage);

}
