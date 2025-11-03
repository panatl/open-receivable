package org.openreceivable.repository;

import org.openreceivable.enums.VehicleStatus;
import org.openreceivable.model.Vehicle;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Vehicle entity
 */
public interface VehicleRepository {
    
    /**
     * Save a vehicle
     */
    Vehicle save(Vehicle vehicle);
    
    /**
     * Find vehicle by ID
     */
    Optional<Vehicle> findById(String vehicleId);
    
    /**
     * Find vehicle by VIN
     */
    Optional<Vehicle> findByVin(String vin);
    
    /**
     * Find all vehicles by status
     */
    List<Vehicle> findByStatus(VehicleStatus status);
    
    /**
     * Find all vehicles
     */
    List<Vehicle> findAll();
    
    /**
     * Delete a vehicle
     */
    void delete(String vehicleId);
    
    /**
     * Check if vehicle exists
     */
    boolean exists(String vehicleId);
}
