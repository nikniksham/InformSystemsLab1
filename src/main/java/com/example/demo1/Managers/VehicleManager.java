package com.example.demo1.Managers;

import com.example.demo1.DBObjects.Vehicle;
import com.example.demo1.ENUMs.FuelType;
import com.example.demo1.ENUMs.VehicleType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

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

    public Vehicle getVehicleById(long id) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM Vehicle u WHERE u.id = :id").setParameter("id", id);
        try {
            Vehicle vehicle = (Vehicle) query.getSingleResult();
            em.close();
            return vehicle;
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }

    public boolean editVehicleById(long vehicle_id, String name, VehicleType vehicleType, float enginePower, int numberOfWheels, double capacity, long distanceTravelled, long fuelConsumption, FuelType fuelType) {
        Vehicle vehicle = getVehicleById(vehicle_id);
        if (vehicle != null) {
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                vehicle.setName(name);
                vehicle.setVehicleType_id(vehicleType.getId());
                vehicle.setEnginePower(enginePower);
                vehicle.setNumberOfWheels(numberOfWheels);
                vehicle.setCapacity(capacity);
                vehicle.setDistanceTravelled(distanceTravelled);
                vehicle.setFuelConsumption(fuelConsumption);
                vehicle.setFuelType_id(fuelType.getId());
                em.merge(vehicle);
                em.getTransaction().commit();
                em.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean deleteVehicleById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Vehicle WHERE id = :id").setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }
}
