package model;

public class FreeRoom extends Room {
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }

    @Override
    public String toString() {
        return "The room Number: " + getRoomNumber() + "\n" +
                "Room Type: " + getRoomType() + "\n" +
                "Room price: " + getRoomPrice();
    }
}
