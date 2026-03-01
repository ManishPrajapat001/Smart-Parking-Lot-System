package org.manish.repositories;

import org.manish.entity.ParkingSlot;
import org.manish.entity.VehicleType;

import java.util.*;

public class ParkingSlotRepository {

    private final Map<VehicleType, Queue<ParkingSlot>> availableSlots;
    private final Set<ParkingSlot> occupiedSlots;

    public ParkingSlotRepository() {
        availableSlots = new HashMap<>();
        occupiedSlots = new HashSet<>();

        for (VehicleType type : VehicleType.values()) {
            availableSlots.put(type, new LinkedList<>());
        }
    }

    // Create and add slot (ID generated internally)
    public synchronized ParkingSlot createParkingSlot(int floorNumber, int slotNumber, VehicleType vehicleType) {
        String slotId = UUID.randomUUID().toString();

        ParkingSlot slot = new ParkingSlot(
                slotId,
                floorNumber,
                slotNumber,
                vehicleType
        );

        availableSlots.get(vehicleType).offer(slot);

        return slot;
    }

    // Allocate slot for vehicle type
    public synchronized ParkingSlot allocateSlot(VehicleType vehicleType) {
        Queue<ParkingSlot> slots = availableSlots.get(vehicleType);

        if (slots == null || slots.isEmpty()) {
            return null; // No slot available
        }

        ParkingSlot slot = slots.poll();
        occupiedSlots.add(slot);

        return slot;
    }

    // Release slot back to available
    public synchronized void releaseSlot(ParkingSlot slot) {
        if (slot == null) {
            throw new IllegalArgumentException("Slot cannot be null");
        }

        if (!occupiedSlots.contains(slot)) {
            throw new IllegalStateException("Slot is not currently occupied");
        }

        occupiedSlots.remove(slot);
        availableSlots.get(slot.getSupportedVehicleType()).offer(slot);
    }

    public synchronized int getAvailableCount(VehicleType type) {
        return availableSlots.get(type).size();
    }

    public synchronized int getOccupiedCount() {
        return occupiedSlots.size();
    }
}
