package com.amr.project;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.amr.project.dao.abstracts.ChatDao;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ChatService;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
public class ChatTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;



    @Autowired
    private ChatDao chatDao;

    @Autowired
    private UserDao userDao;

    private static User ownerUser;
    private static User memberUSer;
    private static Chat testChat;

    public void prepareClasses(){
        //String email, String username, String password, String firstName, String lastName
        Optional<User> oUser = userDao.findUserByEmail("123Owner@usa.net");
        if (!oUser.isPresent()) {

            ownerUser = new User("123Owner@usa.net", "OwnerUserName", "OwnerPass", "ownerF", "ownerL");
            memberUSer = new User("123Member@usa.net", "MemberUserName", "MemberPass", "memberF", "memberL");
            userDao.persist(ownerUser);
            userDao.persist(memberUSer);

        } else {
            ownerUser = oUser.get();
            oUser = userDao.findUserByEmail("123Member@usa.net");
            memberUSer = oUser.get();
        }



        List<Chat> list = chatDao.getListChatsByUser(ownerUser);
        System.out.println(list.size());
        for( Chat chat : list) {
            chatDao.delete(chat);
        }


        testChat = new Chat();
        testChat.setChatName("testChat");
        testChat.setOwner(ownerUser);
        LinkedList<User> members = new LinkedList<>();
        members.add(memberUSer);
        testChat.setMembers(members);
        chatDao.persist(testChat);

    }

    public void removeChat(){
        chatDao.delete(testChat);
     //   userDao.delete(ownerUser);
     //   userDao.delete(memberUSer);
    }



    @Test
    public void  TestFindChatByUserId(){

        prepareClasses();

        List<Chat> listByOwner = chatDao.getListChatsByUser(ownerUser);
        List<Chat> listByMember = chatDao.getListChatsByUser(memberUSer);
        //проверяем на !Null
        Assert.assertNotNull(listByMember);

        Assert.assertNotNull(listByOwner);

        // проверяем что создался один чат, и ищется как по owner так и по member один и тот же чат
        Assert.assertEquals(1, listByMember.size());

        Assert.assertEquals(1, listByOwner.size());

        // проверяем владельца
        Assert.assertEquals(listByOwner.get(0).getOwner().getId(), ownerUser.getId());

        Assert.assertEquals("testChat", listByOwner.get(0).getChatName() );

        removeChat();

    }


    @Test
    @WithMockUser(username="user", password="user", roles = {"USER"})
    public void getIndexChatPage() throws Exception {


        this.mockMvc.perform(get("/chat")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<form id=\"formChatAdd\">")));



    }



}
