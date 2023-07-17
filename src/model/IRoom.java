package model;

public interface IRoom {
    String getRoomNumber();
    double getRoomPrice();
    RoomType getRoomType();
    boolean isFree();
}
