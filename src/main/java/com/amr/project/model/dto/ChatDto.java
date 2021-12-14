package com.amr.project.model.dto;

import com.amr.project.model.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private Long id;
    private List<UserChatDTO> members;
    private Long hash;
    private String chatName;
    private UserChatDTO owner;
}
