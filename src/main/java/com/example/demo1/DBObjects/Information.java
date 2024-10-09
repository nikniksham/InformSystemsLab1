package com.example.demo1.DBObjects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table
@Getter
@Setter
public class Information implements java.io.Serializable {
    @Id
    @SequenceGenerator(name="MY_INFORMATION_SEQ", sequenceName="information_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="MY_INFORMATION_SEQ")
    private long id;
    private long user_id;
    private long vehicle_id;
    private Timestamp modifDate;
    private int typeOfOperation; // REFERENCES to java ENUM TypeOfOperation
}
