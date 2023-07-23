package model;

import java.util.Date;

public class Room implements IRoom {
    private String roomNumber;
    private double roomPrice;
    private RoomType roomType;


    public Room(String roomNumber, double roomPrice, RoomType roomType) {
        super();
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;

    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public double getRoomPrice() {
        return roomPrice;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }


    @Override
    public String toString() {
        return "The room Number: " + roomNumber + "\n" +
                "Room Type: " + roomType + "\n" +
                "Room price: " + roomPrice + "\n"
                ;
    }
}

