package model;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Reservation(Customer customer, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public String toString()
    {
        return "Reservation{customer=" + String.valueOf(this.customer) + ", room=" + String.valueOf(this.room) + ", checkInDate=" + String.valueOf(this.checkInDate) + ", checkOutDate=" + String.valueOf(this.checkOutDate) + "}";
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public IRoom getRoom() {
        return this.room;
    }

    public ChronoLocalDate getCheckInDate() {
        return this.checkInDate;
    }

    public ChronoLocalDate getCheckOutDate() {
        return this.checkOutDate;
    }
}
