package ui;

import api.AdminResource;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;
import model.RoomType;
import service.ReservationService;

public class AdminMenu {
    private static final Scanner scanner;

    public AdminMenu() {
    }

    public static void displayMenu() {
        boolean backToMainMenu = false;

        while(!backToMainMenu) {
            System.out.println("Admin Menu");
            System.out.println("1. See all customers");
            System.out.println("2. See all rooms");
            System.out.println("3. See all reservations");
            System.out.println("4. Add a room");
            System.out.println("5. Back to Main Menu");
            int choice = getUserChoice(5);
            switch (choice) {
                case 1:
                    seeAllCustomers();
                    break;
                case 2:
                    seeAllRooms();
                    break;
                case 3:
                    seeAllReservations();
                    break;
                case 4:
                    addRoom();
                    break;
                case 5:
                    backToMainMenu = true;
            }
        }

    }

    private static void seeAllCustomers() {
        Collection<Customer> customers = AdminResource.getInstance().getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("All Customers:");
            Iterator<Customer> iterator = customers.iterator();
            while(iterator.hasNext()) {
                Customer customer = iterator.next();
                System.out.println(customer);
            }
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> rooms = AdminResource.getInstance().getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            System.out.println("All Rooms:");
            Iterator<IRoom> iterator = rooms.iterator();
            while(iterator.hasNext()) {
                IRoom room = iterator.next();
                System.out.println(room);
            }
        }
    }

    private static void seeAllReservations() {
        Collection<Reservation> reservations = ReservationService.getInstance().getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("All Reservations:");
            Iterator<Reservation> iterator = reservations.iterator();
            while(iterator.hasNext()) {
                Reservation reservation = iterator.next();
                System.out.println(reservation);
            }
        }
    }

    private static void addRoom() {
        boolean validRoomNumber = false;
        double roomNumber = 0.0;
        while (!validRoomNumber) {
            System.out.println("Enter the room number to reserve:");
            try {
                roomNumber = Double.parseDouble(scanner.nextLine());
                validRoomNumber = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid room number. Please enter a valid room number.");
            }
        }
        boolean validPrice = false;
        double price = 0.0;
        while(!validPrice)
        {
            System.out.println("Enter the price per night:");
            try{
                price = Double.parseDouble(scanner.nextLine());
                validPrice = true;
            }
            catch(NumberFormatException e)
            {
                System.out.println("Invalid price! Try again>...");
            }
        }

        RoomType roomType = null;
        while (roomType == null) {
            System.out.println("Enter the room type (SINGLE/DOUBLE):");
            String roomTypeString = scanner.nextLine().toUpperCase();
            try {
                roomType = RoomType.valueOf(roomTypeString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid room type. Please enter SINGLE or DOUBLE.");
            }
        }
        IRoom room = new Room(String.valueOf(roomNumber), price, roomType);
        AdminResource.getInstance().addRoom(room);

    }

    private static int getUserChoice(int maxChoice) {
        int choice = -1;
        while(choice < 1 || choice > maxChoice) {
            System.out.print("Enter your choice (1-" + maxChoice + "): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > maxChoice) {
                    System.out.println("Invalid input. Please enter a number between 1 and " + maxChoice + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return choice;
    }

    private static double getDoubleInput() {
        double value = 0.0;
        boolean isValid = false;
        while(!isValid) {
            String input = scanner.nextLine();
            try {
                value = Double.parseDouble(input);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    static {
        scanner = new Scanner(System.in);
    }
}


