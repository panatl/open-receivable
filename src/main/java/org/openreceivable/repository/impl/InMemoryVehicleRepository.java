package org.openreceivable.repository.impl;

import org.openreceivable.enums.VehicleStatus;
import org.openreceivable.model.Vehicle;
import org.openreceivable.repository.VehicleRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of VehicleRepository
 */
@Repository
public class InMemoryVehicleRepository implements VehicleRepository {
    
    private final Map<String, Vehicle> vehicles = new ConcurrentHashMap<>();
    
    @Override
    public Vehicle save(Vehicle vehicle) {
        if (vehicle.getVehicleId() == null || vehicle.getVehicleId().isEmpty()) {
            vehicle.setVehicleId(UUID.randomUUID().toString());
        }
        vehicles.put(vehicle.getVehicleId(), vehicle);
        return vehicle;
    }
    
    @Override
    public Optional<Vehicle> findById(String vehicleId) {
        return Optional.ofNullable(vehicles.get(vehicleId));
    }
    
    @Override
    public Optional<Vehicle> findByVin(String vin) {
        return vehicles.values().stream()
                .filter(v -> vin.equals(v.getVin()))
                .findFirst();
    }
    
    @Override
    public List<Vehicle> findByStatus(VehicleStatus status) {
        return vehicles.values().stream()
                .filter(v -> status.equals(v.getStatus()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Vehicle> findAll() {
        return new ArrayList<>(vehicles.values());
    }
    
    @Override
    public void delete(String vehicleId) {
        vehicles.remove(vehicleId);
    }
    
    @Override
    public boolean exists(String vehicleId) {
        return vehicles.containsKey(vehicleId);
    }
}
