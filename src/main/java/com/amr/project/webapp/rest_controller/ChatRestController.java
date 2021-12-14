package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.MessageMapper;
import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.ChatDto;
import com.amr.project.model.dto.MessageDTO;
import com.amr.project.model.dto.UserChatDTO;
import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ChatService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chatapi")
public class ChatRestController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ChatService chatService;
    private final MessageMapper messageMapper;


    public ChatRestController(UserService userService, UserMapper userMapper, ChatService chatService, MessageMapper messageMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.chatService = chatService;

        this.messageMapper = messageMapper;
    }

    @PreAuthorize("hasAnyAuthority({'ROLE_ADMIN', 'ROLE_USER'})")
    @PostMapping("/users")
    public List<UserChatDTO> findUsers(@RequestParam(value ="eMail") String eMail){
        System.out.println(eMail);
       List<User>  list =  userService.findUsersByEMail(eMail);

       if ((list != null) && (!list.isEmpty())) {
           System.out.println(list.size());
           return userMapper.listUserToListUserChatDTO(list);
        }
       return new ArrayList<UserChatDTO>();
    }

    @PreAuthorize("hasAnyAuthority({'ROLE_ADMIN', 'ROLE_USER'})")
    @PostMapping("/addUser")
    public Chat addUser2Chat(@RequestParam(value ="id") Long id,  Principal principal){

        User user = userService.findUserByUsername(principal.getName());
        chatService.addUser2Chat(user, userService.findById(id));
        return new Chat();
    }

    @PreAuthorize("hasAnyAuthority({'ROLE_ADMIN', 'ROLE_USER'})")
    @GetMapping("/getChats")
    public List<ChatDto> getUserChats(Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        return chatService.getChatByUser(user.getId());
    }

    @PreAuthorize("hasAnyAuthority({'ROLE_ADMIN', 'ROLE_USER'})")
    @GetMapping("/getChatMessages/{id}")
    public List<MessageDTO> getMessages(@PathVariable(value ="id") Long chat_id, Principal principal){
        //нужна проверка на  user_id=chat.owner  <-- User user = userService.findUserByUsername(principal.getName());

        return   messageMapper.listMessageToListMessageDTO(chatService.getMessageList(chat_id));
    }

    @PreAuthorize("hasAnyAuthority({'ROLE_ADMIN', 'ROLE_USER'})")
    @GetMapping("/getId")
    public UserChatDTO getUserId(Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        return userMapper.userToChatDTO(user);
    }


}
