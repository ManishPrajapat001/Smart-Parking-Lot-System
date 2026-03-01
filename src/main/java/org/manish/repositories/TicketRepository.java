package org.manish.repositories;

import org.manish.entity.ParkingSlot;
import org.manish.entity.Ticket;
import org.manish.entity.Vehicle;

import java.util.*;

public class TicketRepository {
    private final Map<String, Ticket> activeTickets;
    private final List<Ticket> archivedTickets;

    public TicketRepository() {
        this.activeTickets = new HashMap<>();
        this.archivedTickets = new ArrayList<>();
    }

    public synchronized Ticket createTicket(Vehicle vehicle, ParkingSlot parkingSlot){
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }

        if (parkingSlot == null) {
            throw new IllegalArgumentException("Parking slot cannot be null");
        }

        String ticketId = UUID.randomUUID().toString();

        Ticket ticket = new Ticket(ticketId, vehicle, parkingSlot);

        activeTickets.put(ticketId, ticket);

        return ticket;
    }

    // Fetch active ticket
    public synchronized Ticket getActiveTicket(String ticketId) {
        return activeTickets.get(ticketId);
    }

    // Move ticket from active → archived
    public synchronized void archiveTicket(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket cannot be null");
        }

        if (!activeTickets.containsKey(ticket.getTicketId())) {
            throw new IllegalStateException("Ticket not found in active tickets");
        }

        activeTickets.remove(ticket.getTicketId());
        archivedTickets.add(ticket);
    }

    public synchronized int getActiveTicketCount() {
        return activeTickets.size();
    }

    public synchronized int getArchivedTicketCount() {
        return archivedTickets.size();
    }
}
