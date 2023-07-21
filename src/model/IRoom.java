package model;

public interface IRoom {
    /**
     *
     * @return  room number
     */
    String getRoomNumber();

    /**
     *
     * @return
     */
    double getRoomPrice();

    /**
     *
     * @return
     */
    RoomType getRoomType();

    /**
     *
     * @return
     */
    boolean isFree();
}
