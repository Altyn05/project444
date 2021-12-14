package com.amr.project.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @OneToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User to;

    @OneToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User from;

    private String textMessage;
    private boolean viewed;
}
