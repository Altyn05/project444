package com.amr.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MessageDTO {
    private Long id;
    private Long chatId;
    private Long to;
    private Long from;
    private String textMessage;
    private boolean viewed;
}
