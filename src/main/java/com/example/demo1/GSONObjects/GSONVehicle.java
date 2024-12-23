package com.example.demo1.GSONObjects;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class GSONVehicle {
    @NonNull
    private String name;
    private double x;
    private int y;
    private int vehicleType_id;
    private float enginePower;
    private int numberOfWheels;
    private double capacity;
    private long distanceTravelled;
    private long fuelConsumption;
    private int fuelType_id;
    private boolean commonAccess;
}

