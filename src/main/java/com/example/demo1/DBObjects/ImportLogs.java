package com.example.demo1.DBObjects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table
@Getter
@Setter
public class ImportLogs implements java.io.Serializable {
    @Id
    @SequenceGenerator(name="MY_IMPORTLOGS_SEQ", sequenceName="importLogs_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="MY_IMPORTLOGS_SEQ")
    private long id;
    private long user_id;
    private String filename;
    private boolean result;
    private Timestamp creationDate;
    private int count;
}
