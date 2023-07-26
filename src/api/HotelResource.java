package api;

import java.time.LocalDate;
import java.util.Collection;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

public class HotelResource {
    private static HotelResource instance;

    private HotelResource() {
    }

    public static HotelResource getInstance() {
        if (instance == null) {
            instance = new HotelResource();
        }

        return instance;
    }

    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        CustomerService.getInstance().addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return ReservationService.getInstance().getRoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        Customer customer = CustomerService.getInstance().getCustomer(customerEmail);
        return customer != null ? ReservationService.getInstance().reserveARoom(customer, room, checkInDate, checkOutDate) : null;
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = CustomerService.getInstance().getCustomer(customerEmail);
        return customer != null ? ReservationService.getInstance().getCustomersReservation(customer) : null;
    }

    public Collection<IRoom> findARoom(LocalDate checkinDate, LocalDate checkOutDate) {
        return ReservationService.getInstance().findRooms(checkinDate, checkOutDate);
    }

    /**
     * give access to the rooms that will be released in less than one week
     *
     * @param checkInDate       original date to enter
     * @param checkOutDate      original date to release the room
     * @return                  recommended rooms
     */
    public Collection<IRoom>findRecommendedRooms(LocalDate checkInDate, LocalDate checkOutDate)
    {

        Collection<IRoom>recommendedRooms =ReservationService.getInstance().findRecommendedRooms(checkInDate,checkOutDate);
        return recommendedRooms;

    }
    public boolean isRoomReservedAtDate(IRoom room, LocalDate date)
    {
        return ReservationService.getInstance().isRoomReservedAtDate(room, date);
    }

}

