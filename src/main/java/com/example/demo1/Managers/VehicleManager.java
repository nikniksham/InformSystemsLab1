package com.example.demo1.Managers;

import com.example.demo1.DBObjects.Users;
import com.example.demo1.DBObjects.Vehicle;
import com.example.demo1.ENUMs.FuelType;
import com.example.demo1.ENUMs.VehicleType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class VehicleManager {
    @Inject
    InformationManager informationManager;

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

    public List<Vehicle> getAllVehicle() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM Vehicle u");
        try {
            List<Vehicle> resultList = (List<Vehicle>) query.getResultList();
            em.close();
            return resultList;
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }

    public List<Vehicle> getPaginVehicle(Long last_id, Long oper, Long page_size) {
        EntityManager em = emf.createEntityManager();
        if (page_size == null) {page_size = 10l;}
        if (last_id == null) {last_id = 0l;}
        if (oper == null) {oper = 0l;}
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root veh = cq.from(Vehicle.class);
            if (oper == 0l) {
                cq.where(cb.greaterThan(veh.get("id"), last_id));
                cq.orderBy(cb.asc(veh.get("id")));
            } else {
                cq.where(cb.lessThan(veh.get("id"), last_id));
                cq.orderBy(cb.desc(veh.get("id")));
            }
            List<Vehicle> resultList = (List<Vehicle>) em.createQuery(cq).setMaxResults(Math.toIntExact(page_size)).getResultList();
            em.close();
            if (oper == 1l) {
                Collections.reverse(resultList);
            }
            return resultList;
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }

    public boolean haveElemGreaterOrLower(long id, boolean is_greater) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root veh = cq.from(Vehicle.class);
            if (is_greater) {
                cq.where(cb.greaterThan(veh.get("id"), id));
            } else {
                cq.where(cb.lessThan(veh.get("id"), id));
            }
            em.createQuery(cq).setMaxResults(1).getSingleResult();
            em.close();
            return true;
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }

    public String addWheels(long vehicle_id, int count_wheels) {
        if (count_wheels < 0) {return "Кол-во добавляемых колёс должно быть > 0";}
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root veh = cq.from(Vehicle.class);
            cq.where(cb.equal(veh.get("id"), vehicle_id));
            Vehicle vehicle = (Vehicle) em.createQuery(cq).setMaxResults(1).getSingleResult();
            if ((long)vehicle.getNumberOfWheels() + (long)count_wheels > 2147483647) {
                em.close();
                return "Слишком много подкидываешь колёс, не поверят (максимум можно добавить " + (2147483647 - vehicle.getNumberOfWheels()) + ")";
            }
            em.getTransaction().begin();
            vehicle.setNumberOfWheels(vehicle.getNumberOfWheels() + count_wheels);
            em.merge(vehicle);
            em.getTransaction().commit();
            em.close();
            return null;
        } catch (Exception ex) {
            em.close();
            return "ТС не найдено";
        }
    }

    public Double calcAverageFuelConsumption() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM Vehicle u");
        try {
            List<Vehicle> resultList = (List<Vehicle>) query.getResultList();
            em.close();
            double average = 0d;
            for (Vehicle veh: resultList) {
                average += veh.getFuelConsumption();
            }
            return average/resultList.size();
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }

    public HashMap<Long, Boolean> getUserRights(List<Vehicle> vehicleList, Users user) {
        if (vehicleList == null || user == null) {
            return null;
        }
        HashMap<Long, Boolean> resultList = new HashMap<>();
        for (Vehicle vehicle : vehicleList) {
            if (user.getStatus() == 2 || informationManager.checkUserIsAuthor(user.getId(), vehicle.getId())) {
                resultList.put(vehicle.getId(), true);
            } else {
                resultList.put(vehicle.getId(), false);
            }
        }
        return resultList;
    }

    public List<Vehicle> searchVehicle(String sample, boolean is_start, Double power_min, Double power_max) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root veh = cq.from(Vehicle.class);
            List<Predicate> predicates = new ArrayList<>();

            if (power_min != null) {
                predicates.add(cb.greaterThanOrEqualTo(veh.get("enginePower"), power_min));
            }
            if (power_max != null) {
                predicates.add(cb.lessThanOrEqualTo(veh.get("enginePower"), power_max));
            }
            if (!sample.equals("")) {
                predicates.add(cb.like(veh.get("name"), (is_start ? "" : "%") + sample + "%"));
            }

            cq.where(cb.and(predicates.toArray(new Predicate[0])));

            List<Vehicle> resultList = (List<Vehicle>) em.createQuery(cq).getResultList();

            em.close();
            return resultList;
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }
}
