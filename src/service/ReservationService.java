package service;

import java.util.*;

import model.Customer;
import model.IRoom;
import model.Reservation;

public class ReservationService {
    private static ReservationService instance;
    private Set<IRoom> rooms = new HashSet<>();
    private Set<Reservation> reservations = new HashSet<>();

    private ReservationService() {
    }

    public IRoom addRoom(IRoom room) {
        rooms.add(room);
        return null;
    }

    public IRoom getRoom(String roomId) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : rooms) {
            if (isRoomAvailable(room, checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> customerReservation = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservation.add(reservation);
            }
        }
        return customerReservation;
    }

    public void printAllReservation() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    private boolean isRoomAvailable(IRoom room, Date checkInDate, Date checkOutDate) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room)) {
                if (!(checkOutDate.before(reservation.getCheckInDate()) || checkInDate.after(reservation.getCheckOutDate()))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public Collection<Reservation> getAllReservations() {
        return reservations;
    }

    public Collection<IRoom> getAllRooms() {
        return rooms;
    }
}
