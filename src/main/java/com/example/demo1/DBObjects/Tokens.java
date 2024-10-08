package com.example.demo1.DBObjects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class Tokens implements java.io.Serializable {
    @Id
    @SequenceGenerator(name="MY_TOKEN_SEQ", sequenceName="token_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="MY_TOKEN_SEQ")
    private long id;
    @NonNull
    private long user_id;
    @Column(name = "creationDate")
    private Timestamp creationDate;
    @NonNull
    private String code;
}
