package model;

public interface IRoom {
    /**
     *
     * @return  room number
     */
    String getRoomNumber();

    /**
     *
     * @return  our room price
     */
    double getRoomPrice();

    /**
     *
     * @return  SINGLE or DOUBLE as type
     */
    RoomType getRoomType();

    /**
     *
     * @return  true if the room has no cost, false else
     */
    boolean isFree();
}
