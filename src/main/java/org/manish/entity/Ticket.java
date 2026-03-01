package org.manish.entity;

import org.manish.exception.InvalidTime;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSlot parkingSlot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSlot parkingSlot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parkingSlot = parkingSlot;
        this.entryTime = LocalDateTime.now();
    }

    public void markExit() {
        if (this.exitTime != null) {
            throw new IllegalStateException("Vehicle already checked out");
        }
        this.exitTime = LocalDateTime.now();
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime(){
        if(exitTime == null){
            throw new InvalidTime("Vehicle not exited yet");
        }
        return exitTime;
    }

}
