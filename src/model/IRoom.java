package model;

import java.util.Date;

public interface IRoom {
    /**
     * allow access to room number
     *
     * @return  room number
     */
    String getRoomNumber();

    /**
     * allow access to room price
     *
     * @return  our room price
     */
    double getRoomPrice();

    /**
     * Allow access to room type
     *
     * @return  type
     */
    RoomType getRoomType();




}
