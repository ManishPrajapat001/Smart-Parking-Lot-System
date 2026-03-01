package org.manish.service;

import org.manish.entity.ParkingSlot;
import org.manish.entity.VehicleType;
import org.manish.repositories.ParkingSlotRepository;

public class ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;

    public ParkingSlotService(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    // Create slot (repo generates ID internally)
    public ParkingSlot addParkingSlot(int floorNumber,
                                      int slotNumber,
                                      VehicleType vehicleType) {

        return parkingSlotRepository.createParkingSlot(
                floorNumber,
                slotNumber,
                vehicleType
        );
    }

    public int getAvailableSlots(VehicleType vehicleType) {
        return parkingSlotRepository.getAvailableCount(vehicleType);
    }

    public int getTotalOccupiedSlots() {
        return parkingSlotRepository.getOccupiedCount();
    }
}