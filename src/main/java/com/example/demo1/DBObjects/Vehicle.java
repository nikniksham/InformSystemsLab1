package com.example.demo1.DBObjects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Entity
@Table
@Getter
@Setter
public class Vehicle implements java.io.Serializable {
    @Id
    @SequenceGenerator(name="MY_VEHICLE_SEQ", sequenceName="vehicle_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="MY_VEHICLE_SEQ")
    private long id;
    @NonNull
    private String name;
    @NonNull
    private long coordinates_id; // REFERENCES TO Coordinates(id)
    private Timestamp creationDate; // autoCreate in DB
    private int vehicleType_id; // REFERENCES to java ENUM VehicleType
    @NonNull
    private float enginePower;
    private int numberOfWheels;
    @NonNull
    private double capacity;
    @NonNull
    private long distanceTravelled;
    private long fuelConsumption;
    @NonNull
    private int fuelType_id; // REFERENCES to java ENUM FuelType
}
