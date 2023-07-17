package api;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class AdminResource {
    private static AdminResource instance;
    private Set<IRoom> rooms = new HashSet<>();

    private AdminResource() {
    }

    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource();
        }
        return instance;
    }

    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }

    public void addRoom(IRoom room) {
        ReservationService.getInstance().addRoom(room);
    }

    public Collection<IRoom> getAllRooms() {
        return ReservationService.getInstance().getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return CustomerService.getInstance().getAllCustomers();
    }

    public void displayAllReservations() {
        ReservationService.getInstance().printAllReservation();
    }

    public IRoom getRoom(String roomNumber) {
        Iterator<IRoom> iterator = ReservationService.getInstance().getAllRooms().iterator();
        while (iterator.hasNext()) {
            IRoom room = iterator.next();
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }
}


