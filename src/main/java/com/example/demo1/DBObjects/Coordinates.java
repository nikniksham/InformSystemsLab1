package com.example.demo1.DBObjects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Coordinates implements java.io.Serializable {
    @Id
    @SequenceGenerator(name="MY_COORDINATES_SEQ", sequenceName="coordinates_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="MY_COORDINATES_SEQ")
    private long id;
    @NonNull
    private double x;
    @NonNull
    private int y;
}
