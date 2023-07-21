package service;

import java.util.*;

import model.Customer;
import model.IRoom;
import model.Reservation;

public class ReservationService {
    private static ReservationService instance;
    // To ensure uniqueness
    private Set<IRoom> rooms = new HashSet<>();
    private Set<Reservation> reservations = new HashSet<>();

    private ReservationService() {
    }

    /**
     * Allow to add a room to rooms available
     *
     * @param room      room to add
     * @return          null
     */
    public IRoom addRoom(IRoom room) {
        rooms.add(room);
        return null;
    }

    /**
     * give access to room access
     *
     * @param roomId        room number that give access to the room details
     * @return              room corresponding to the given Id or null if no corresponding room
     */
    public IRoom getRoom(String roomId) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    /**
     * reserve a room
     *
     * @param customer      booking customer details
     * @param room          room the customer is booking
     * @param checkInDate   The date from which he has access to the room
     * @param checkOutDate  The date he'll be leaving
     * @return              a reservation
     */
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    /**
     * Allow to find avalaible rooms on given date
     *
     * @param checkInDate       date from which the room is needed
     * @param checkOutDate      date at which the room will be released
     * @return                  all rooms available on a given time interval
     */
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : rooms) {
            if (isRoomAvailable(room, checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    /**
     * Allow access to a reservation of a given customer
     *
     * @param customer      The customer whose reservation is
     * @return              The reservation
     */
    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> customerReservation = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservation.add(reservation);
            }
        }
        return customerReservation;
    }

    /**
     * display all reservations
     */
    public void printAllReservation() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    /**
     * Check if a room is available
     *
     * @param room              room to check availibility
     * @param checkInDate       date from which room is needed
     * @param checkOutDate      deadline to release the room
     * @return                  true if the room is available, false else
     */
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

    /**
     * Ensure we access the same class instance
     *
     * @return      class instance
     */
    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    /**
     * All access to all reservations
     *
     * @return      reservations
     */
    public Collection<Reservation> getAllReservations() {
        return reservations;
    }

    /**
     * Give access to all rooms
     *
     * @return      all rooms
     */
    public Collection<IRoom> getAllRooms() {
        return rooms;
    }
}
