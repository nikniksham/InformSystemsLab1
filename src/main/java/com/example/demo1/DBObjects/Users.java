package com.example.demo1.DBObjects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Users implements java.io.Serializable {
    @Id
    @SequenceGenerator(name="MY_USERS_SEQ", sequenceName="users_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="MY_USERS_SEQ")
    private long id;
    private String login;
    private int status;
    private byte[] password;
}
