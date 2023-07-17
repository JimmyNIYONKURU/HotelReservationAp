package ui;

import api.HotelResource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;

public class MainMenu {
    private static final Scanner scanner;
    private static final SimpleDateFormat dateFormat;

    public MainMenu() {
    }

    public static void displayMenu() {
        boolean exit = false;

        while(!exit) {
            System.out.println("Main Menu");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            int choice = getUserChoice(5);
            switch (choice) {
                case 1:
                    findAndReserveRoom();
                    break;
                case 2:
                    seeMyReservations();
                    break;
                case 3:
                    createAccount();
                    break;
                case 4:
                    AdminMenu.displayMenu();
                    break;
                case 5:
                    exit = true;
            }
        }

    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static void findAndReserveRoom() {
        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);
        System.out.println("Enter check-in date (MM/dd/yyyy):");
        Date checkInDate = getDateInput();
        System.out.println("Enter check-out date (MM/dd/yyyy):");
        Date checkOutDate = getDateInput();
        Collection<IRoom> availableRooms = HotelResource.getInstance().findARoom(checkInDate, checkOutDate);
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available for the selected dates.");
        } else {
            System.out.println("Available Rooms:");
            Iterator var5 = availableRooms.iterator();

            IRoom selectedRoom;
            while(var5.hasNext()) {
                selectedRoom = (IRoom)var5.next();
                System.out.println(selectedRoom);
            }

            System.out.println("Enter the room number to reserve:");
            String roomNumber = scanner.nextLine();
            selectedRoom = HotelResource.getInstance().getRoom(roomNumber);
            if (selectedRoom != null) {
                System.out.println("Enter your email address:");
                String email = scanner.nextLine();
                Customer customer = HotelResource.getInstance().getCustomer(email);
                if (customer == null) {
                    while(!pattern.matcher(email).matches()) {
                        System.out.println("Invalid email. Please enter a valid mail");
                    }

                    System.out.println("Creating a new account...");
                    System.out.println("Enter your first name:");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter your last name:");
                    String lastName = scanner.nextLine();
                    HotelResource.getInstance().createACustomer(email, firstName, lastName);
                    System.out.println("Account created successfully!");
                    customer = HotelResource.getInstance().getCustomer(email);
                }

                Reservation reservation = HotelResource.getInstance().bookARoom(email, selectedRoom, checkInDate, checkOutDate);
                if (reservation != null) {
                    System.out.println("Reservation successful!");
                    System.out.println("Reservation details:");
                    System.out.println(reservation);
                } else {
                    System.out.println("Failed to make a reservation.");
                }
            } else {
                System.out.println("Invalid room number.");
            }
        }

    }

    private static void seeMyReservations() {
        System.out.println("Enter your email address:");
        String email = scanner.nextLine();
        Customer customer = HotelResource.getInstance().getCustomer(email);
        if (customer != null) {
            Collection<Reservation> reservations = HotelResource.getInstance().getCustomersReservations(email);
            if (reservations.isEmpty()) {
                System.out.println("You have no reservations.");
            } else {
                System.out.println("Your Reservations:");
                Iterator var3 = reservations.iterator();

                while(var3.hasNext()) {
                    Reservation reservation = (Reservation)var3.next();
                    System.out.println(reservation);
                }
            }
        } else {
            System.out.println("No customer found with the provided email address.");
        }

    }

    private static void createAccount() {
        System.out.println("Enter your first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter your last name:");
        String lastName = scanner.nextLine();

        while(true) {
            while(true) {
                System.out.println("Enter your email address:");
                String email = scanner.nextLine();
                if (isValidEmail(email)) {
                    try {
                        CustomerService.getInstance().addCustomer(email, firstName, lastName);
                        System.out.println("Account created successfully!");
                        return;
                    } catch (IllegalArgumentException var4) {
                        System.out.println("Error creating account: " + var4.getMessage());
                    }
                } else {
                    System.out.println("Invalid email address. Please enter a valid email.");
                }
            }
        }
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
            } catch (NumberFormatException var3) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        return choice;
    }

    private static Date getDateInput() {
        Date date = null;
        boolean isValid = false;

        while(!isValid) {
            String input = scanner.nextLine();

            try {
                date = dateFormat.parse(input);
                isValid = true;
            } catch (ParseException var4) {
                System.out.println("Invalid date format. Please enter a date in the format MM/dd/yyyy:");
            }
        }

        return date;
    }

    static {
        scanner = new Scanner(System.in);
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    }
}