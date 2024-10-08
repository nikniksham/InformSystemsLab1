package com.example.demo1.Managers;

import com.example.demo1.DBObjects.Vehicle;
import com.example.demo1.ENUMs.FuelType;
import com.example.demo1.ENUMs.VehicleType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.sql.Timestamp;

@ApplicationScoped
public class VehicleManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");

    public Long createNewVehicle(String name, long coordinates_id, VehicleType vehicleType, float enginePower, int numberOfWheels, double capacity, long distanceTravelled, long fuelConsumption, FuelType fuelType) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Vehicle new_vehicle = new Vehicle();

            new_vehicle.setName(name);
            new_vehicle.setCoordinates_id(coordinates_id);
            new_vehicle.setCreationDate(new Timestamp(new java.util.Date().getTime()));
            new_vehicle.setVehicleType_id(vehicleType.getId());
            new_vehicle.setEnginePower(enginePower);
            new_vehicle.setNumberOfWheels(numberOfWheels);
            new_vehicle.setCapacity(capacity);
            new_vehicle.setDistanceTravelled(distanceTravelled);
            new_vehicle.setFuelConsumption(fuelConsumption);
            new_vehicle.setFuelType_id(fuelType.getId());

            em.persist(new_vehicle);
            em.getTransaction().commit();
            em.close();

            return new_vehicle.getId();
        } catch (Exception e) {
            em.close();
            return null;
        }
    }
}
