package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ChatDao;
import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.Message;
import com.amr.project.model.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ChatDaoImpl extends ReadWriteDaoImpl<Chat, Long> implements ChatDao {
    @Override
    public List<Chat> getListChatsByUser(User user) {


        List<Chat> list = em.createQuery("select c from Chat c where  c.owner = :user", Chat.class).setParameter("user",user).getResultList();
        List<Chat> listInMembers = em.createQuery("select c from Chat c where :user MEMBER OF c.members ", Chat.class).setParameter("user",user).getResultList();
        list.addAll(listInMembers);

        return list;
    }

    public  Chat getChatById(Long chatId){
        return em.createQuery("select c from Chat c where  c.id = :chatId", Chat.class).setParameter("chatId",chatId).getSingleResult();
    }



    @Override
    public void persist(Chat entity) {
        em.persist(entity);
    }

    @Override
    public void update(Chat entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Chat entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }


    public com.amr.project.model.entity.Message saveMessage(Message message){
        em.persist(message);
        return message;
    }


}
