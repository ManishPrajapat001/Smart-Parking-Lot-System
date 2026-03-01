package org.manish;
import org.manish.entity.Vehicle;
import org.manish.entity.VehicleType;
import org.manish.repositories.ParkingSlotRepository;
import org.manish.repositories.TicketRepository;
import org.manish.service.ParkingSlotService;
import org.manish.service.TicketService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

public static void main(String[] args) throws InterruptedException {

    // ---- Initialize Repositories ----
    ParkingSlotRepository parkingSlotRepository = new ParkingSlotRepository();
    TicketRepository ticketRepository = new TicketRepository();

    // ---- Initialize Services ----
    ParkingSlotService parkingSlotService =
            new ParkingSlotService(parkingSlotRepository);

    TicketService ticketService =
            new TicketService(parkingSlotRepository, ticketRepository);

    // ---- Create Parking Slots ----
    parkingSlotService.addParkingSlot(1, 1, VehicleType.CAR);
    parkingSlotService.addParkingSlot(1, 2, VehicleType.CAR);
    parkingSlotService.addParkingSlot(1, 3, VehicleType.BIKE);

    System.out.println("Initial CAR slots available: " +
            parkingSlotService.getAvailableSlots(VehicleType.CAR));

    // ---- Vehicle Entry ----
    Vehicle vehicle = new Vehicle("RJ14AB1234", VehicleType.CAR);

    System.out.println("\nVehicle entering...");

    var ticket = ticketService.checkIn(vehicle);

    System.out.println("Ticket Issued: " + ticket.getTicketId());

    System.out.println("CAR slots available after entry: " +
            parkingSlotService.getAvailableSlots(VehicleType.CAR));

    // ---- Simulate Parking Duration ----
    Thread.sleep(3000); // 3 seconds demo

    // ---- Vehicle Exit ----
    System.out.println("\nVehicle exiting...");

    double fee = ticketService.checkOut(ticket.getTicketId());

    System.out.println("Parking Fee: " + fee);

    System.out.println("CAR slots available after exit: " +
            parkingSlotService.getAvailableSlots(VehicleType.CAR));
}
}

