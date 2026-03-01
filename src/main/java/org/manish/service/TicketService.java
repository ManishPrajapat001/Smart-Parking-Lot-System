package org.manish.service;

import org.manish.entity.ParkingSlot;
import org.manish.entity.Ticket;
import org.manish.entity.Vehicle;
import org.manish.repositories.ParkingSlotRepository;
import org.manish.repositories.TicketRepository;

import java.time.Duration;



public class TicketService {
    private final ParkingSlotRepository parkingSlotRepository;
    private final TicketRepository ticketRepository;

    private static final double MOTORCYCLE_RATE = 10;
    private static final double CAR_RATE = 20;
    private static final double BUS_RATE = 40;

    public TicketService(ParkingSlotRepository parkingSlotRepository,
                         TicketRepository ticketRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.ticketRepository = ticketRepository;
    }

    // CHECK-IN
    public Ticket checkIn(Vehicle vehicle) {

        ParkingSlot slot = parkingSlotRepository.allocateSlot(vehicle.getVehicleType());

        if (slot == null) {
            throw new IllegalStateException(
                    "No available slot for vehicle type: " + vehicle.getVehicleType()
            );
        }


        return ticketRepository.createTicket(vehicle, slot);

    }

    // CHECK-OUT
    public double checkOut(String ticketId) {

        Ticket ticket = ticketRepository.getActiveTicket(ticketId);

        if (ticket == null) {
            throw new IllegalArgumentException("Invalid ticket ID");
        }

        ticket.markExit();   // Ticket controls lifecycle

        double amount = calculateFee(ticket);

        parkingSlotRepository.releaseSlot(ticket.getParkingSlot());

        ticketRepository.archiveTicket(ticket);

        return amount;
    }

    private double calculateFee(Ticket ticket) {

        long minutes = Duration.between(
                ticket.getEntryTime(),
                ticket.getExitTime()
        ).toMinutes();

        long hours = (long) Math.ceil(minutes / 60.0);

        return switch (ticket.getVehicle().getVehicleType()) {
            case BIKE -> hours * MOTORCYCLE_RATE;
            case CAR -> hours * CAR_RATE;
            case BUS -> hours * BUS_RATE;
            default -> throw new IllegalStateException("Unknown vehicle type");
        };
    }
}
