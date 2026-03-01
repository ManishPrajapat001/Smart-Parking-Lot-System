package org.manish.entity;

import java.util.Objects;

public class ParkingSlot {
    private final String parkingSlotId;
    private final int floorNumber;
    private final int slotNumber;
    private VehicleType supportedVehicleType;

    public ParkingSlot(String parkingSlotId,
                       int floorNumber,
                       int slotNumber,
                       VehicleType supportedVehicleType) {

        if (parkingSlotId == null || parkingSlotId.isBlank()) {
            throw new IllegalArgumentException("Parking slot ID cannot be null or empty");
        }
        if (floorNumber < 0) {
            throw new IllegalArgumentException("Floor number cannot be negative");
        }
        if (slotNumber < 0) {
            throw new IllegalArgumentException("Slot number cannot be negative");
        }
        if (supportedVehicleType == null) {
            throw new IllegalArgumentException("Supported vehicle type cannot be null");
        }

        this.parkingSlotId = parkingSlotId;
        this.floorNumber = floorNumber;
        this.slotNumber = slotNumber;
        this.supportedVehicleType = supportedVehicleType;
    }

    public void setSupportedVehicleType(VehicleType vehicleType){
        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null");
        }
        this.supportedVehicleType = vehicleType;
    }
    public String getParkingSlotId() {
        return parkingSlotId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public VehicleType getSupportedVehicleType() {
        return supportedVehicleType;
    }

    public boolean supportsVehicle(VehicleType vehicleType) {
        return this.supportedVehicleType == vehicleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingSlot)) return false;
        ParkingSlot that = (ParkingSlot) o;
        return parkingSlotId.equals(that.parkingSlotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkingSlotId);
    }

    @Override
    public String toString() {
        return "ParkingSlot{" +
                "id='" + parkingSlotId + '\'' +
                ", floor=" + floorNumber +
                ", slot=" + slotNumber +
                ", type=" + supportedVehicleType +
                '}';
    }
}
