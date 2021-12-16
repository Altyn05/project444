package com.amr.project.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<User> members;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    private Long hash;

    private String chatName;

    @OneToOne
    private User owner;


}
