package ui;

import api.HotelResource;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Customer;
import model.FreeRoom;
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

    private static Collection<IRoom>findRecommendedRooms(Date checkInDate, Date checkOutDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkInDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);//Add 7 days to original check-in date
        Date recommendedCheckInDate = calendar.getTime();
        calendar.setTime(checkOutDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);//Add 7 days to original check-out date
        Date recommendedCheckOutDate = calendar.getTime();
        return HotelResource.getInstance().findARoom(recommendedCheckInDate,recommendedCheckOutDate);
    }

    private static boolean askToReserveRecommendedRoom(Collection<IRoom> recommendedRooms) {
        System.out.println("No rooms found at the selected date. See below our recommended rooms for one week later:");
        for (IRoom room : recommendedRooms) {
            System.out.println(room);
        }
        System.out.println("Do you want to reserve one of them? (yes/no)");
        String reply = scanner.nextLine().toLowerCase();
        return reply.equals("yes");
    }
    private static void findAndReserveRoom() {
        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);
        System.out.println("Enter check-in date (MM/dd/yyyy):");
        Date checkInDate = getDateInput();
        System.out.println("Enter check-out date (MM/dd/yyyy):");
        Date checkOutDate = getDateInput();
        while(checkInDate.after(checkOutDate) || checkInDate.before(new Date())) {

            System.out.println("Invalid reservation period. Check-in date must be before check-out date and in the future.");
            System.out.println("Enter check-in date (MM/dd/yyyy):");
            checkInDate = getDateInput();
            System.out.println("Enter check-out date (MM/dd/yyyy):");
            checkOutDate = getDateInput();

        }

        Collection<IRoom> availableRooms = HotelResource.getInstance().findARoom(checkInDate, checkOutDate);
        if (availableRooms.isEmpty()) {
            Collection<IRoom> recommendedRooms = findRecommendedRooms(checkInDate, checkOutDate);

            if(!recommendedRooms.isEmpty())
            {
                if(askToReserveRecommendedRoom(recommendedRooms))
                {
                    findAndReserveRoom();
                }
            }
            else {
                System.out.println("No recommended rooms for your dates!");
            }

        }

        else {
            System.out.println("Available Rooms:");
            String roomPrice;
            Iterator iterator1 = availableRooms.iterator();

            IRoom selectedRoom;
            while (iterator1.hasNext()) {
                selectedRoom = (IRoom) iterator1.next();
                if (selectedRoom.getRoomPrice()==0.0)
                {
                    selectedRoom = new FreeRoom(selectedRoom.getRoomNumber(),selectedRoom.getRoomType());
                    roomPrice = "Free";
                }
                else {
                    roomPrice = "$ " + selectedRoom.getRoomPrice() + " per night";
                }
                System.out.println(selectedRoom.getRoomNumber()+ " - "+ roomPrice + " - " + selectedRoom.getRoomType());
            }

            System.out.println("Do you want to reserve one of these rooms? (yes/no)");
            String answer = scanner.nextLine().toLowerCase();
            while(!(answer.equals("yes") || answer.equals("no")))
            {
                System.out.println("Invalid reply. Please enter yes or no");
                System.out.println("Do you want to reserve one of these rooms? (yes/no)");
                answer = scanner.nextLine().toLowerCase();
            }
            if (answer.equals("yes")) {
                System.out.println("Enter the room number to reserve:");
                double roomNumber = scanner.nextDouble();
                scanner.nextLine();//consume the new line character
                selectedRoom = HotelResource.getInstance().getRoom(String.valueOf(roomNumber));
                while (selectedRoom == null)
                {
                    System.out.println("Invalid room number, please enter a valid room number!");
                    roomNumber = scanner.nextDouble();
                    scanner.nextLine();
                    selectedRoom = HotelResource.getInstance().getRoom(String.valueOf(roomNumber));
                }
                if (selectedRoom != null) {
                    System.out.println("Enter your email address:");
                    String email = scanner.nextLine();
                    Customer customer = HotelResource.getInstance().getCustomer(email);
                    if (customer == null) {
                        while (!pattern.matcher(email).matches())
                        {
                            System.out.println("Invalid email. Please enter a valid mail");
                            email = scanner.nextLine();
                            customer = HotelResource.getInstance().getCustomer(email);

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
                    }
                    else
                    {
                        System.out.println("Failed to make a reservation.");
                    }
                }
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
                Iterator iterator = reservations.iterator();

                while(iterator.hasNext()) {
                    Reservation reservation = (Reservation)iterator.next();
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
                System.out.println("Enter your email address:");
                String email = scanner.nextLine();
                if (isValidEmail(email)) {
                    try {
                        CustomerService.getInstance().addCustomer(email, firstName, lastName);
                        System.out.println("Account created successfully!");
                        return;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error creating account: " + e.getMessage());
                    }
                } else {
                    System.out.println("Invalid email address. Please enter a valid email.");
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
            } catch (NumberFormatException e) {
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
            } catch (ParseException e) {
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