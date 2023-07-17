package model;

import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public String toString() {
        String var10000 = String.valueOf(this.customer);
        return "Reservation{customer=" + var10000 + ", room=" + String.valueOf(this.room) + ", checkInDate=" + String.valueOf(this.checkInDate) + ", checkOutDate=" + String.valueOf(this.checkOutDate) + "}";
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public IRoom getRoom() {
        return this.room;
    }

    public Date getCheckInDate() {
        return this.checkInDate;
    }

    public Date getCheckOutDate() {
        return this.checkOutDate;
    }
}
