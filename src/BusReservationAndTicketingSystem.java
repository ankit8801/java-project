import java.io.*;

public class BusReservationAndTicketingSystem {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String[] DESTINATIONS = {"Karjat", "Panvel", "Khopoli", "Murbad", "Badlapur"};
    private static final int[] DISTANCES = {0, 300, 400, 600, 700}; // Example distances in kilometers
    private static final double FARE_PER_KM = 0.05; // Fixed fare rate per kilometer

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        if (authenticateUser(in)) {
            runMainMenu(in);
        } else {
            System.out.println("Invalid user or password!");
        }
    }

    private static boolean authenticateUser(BufferedReader in) throws IOException {
        for (int attempts = 0; attempts < 3; attempts++) {
            System.out.print("Enter Username: ");
            String user = in.readLine();
            System.out.print("Enter Password: ");
            String password = in.readLine();

            if (user.equals(USERNAME) && password.equals(PASSWORD)) {
                return true;
            } else {
                System.out.println("Invalid user or password! Attempts left: " + (2 - attempts));
            }
        }
        return false;
    }

    private static void runMainMenu(BufferedReader in) throws IOException {
        int[] availableSeats = {10, 10, 10, 10, 10}; // Initializing available seats for each destination
        String[][] ticketDetails = new String[100][2]; // Store passenger details (name, destination)
        double[] ticketFare = new double[100]; // Store fare details (1D array)
        int transactionCount = 0; // Track number of transactions
        boolean exit = false;

        while (!exit) {
            displayMainMenu();
            String choice = in.readLine();

            switch (choice) {
                case "1":
                    displayDestinations(availableSeats);
                    break;
                case "2":
                    transactionCount = buyTicket(in, availableSeats, ticketDetails, ticketFare, transactionCount);
                    break;
                case "3":
                    processTransaction(in, ticketDetails, ticketFare, transactionCount);
                    break;
                case "4":
                    viewPassengerDetails(in, ticketDetails, ticketFare, transactionCount);
                    break;
                case "5":
                    exit = true;
                    System.out.println("Thank You!");
                    break;
                default:
                    System.out.println("Invalid Input!");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("**********");
        System.out.println("* BUS TICKETING SYSTEM   *");
        System.out.println("**********");
        System.out.println("* [1] Destination        *");
        System.out.println("* [2] Buy Ticket         *");
        System.out.println("* [3] Transaction        *");
        System.out.println("* [4] View               *");
        System.out.println("* [5] Exit               *");
        System.out.println("**********\n");
    }

    private static void displayDestinations(int[] availableSeats) {
        System.out.println("*************");
        System.out.println("*   DESTINATION   |  SEAT  *");
        System.out.println("*************");
        for (int i = 0; i < DESTINATIONS.length; i++) {
            System.out.println("* " + (i + 1) + ".) " + DESTINATIONS[i] + " | " + availableSeats[i] + " seats available *");
        }
        System.out.println("*************\n");
        System.out.println("PWD, STUDENT, & SENIOR CITIZEN with 20% DISCOUNT!!!\n");
    }

    private static int buyTicket(BufferedReader in, int[] availableSeats, String[][] ticketDetails, double[] ticketFare, int transactionCount) throws IOException {
        String passengerName;
        while (true) {
            System.out.print("\nENTER PASSENGER'S NAME: ");
            passengerName = in.readLine();
            if (!isNameUsed(ticketDetails, passengerName, transactionCount)) {
                break;
            }
        }

        int destinationIndex = getDestinationIndex(in);
        if (destinationIndex == -1) {
            System.out.println("Invalid destination selection.");
            return transactionCount;
        }

        int numberOfPassengers = getNumberOfPassengers(in);
        if (numberOfPassengers > availableSeats[destinationIndex]) {
            System.out.println("Sorry, we don't have enough seats available for " + numberOfPassengers + " persons.");
            return transactionCount;
        }

        // Calculate the fare based on distance
        double fare = calculateFare(destinationIndex) * numberOfPassengers;

        // Ask user if they want to proceed with the purchase
        System.out.println("\nThe total fare for " + numberOfPassengers + " passengers to " + DESTINATIONS[destinationIndex] + " is Rs" + fare);
        System.out.print("Do you want to proceed with the purchase? (yes/no): ");
        String purchaseDecision = in.readLine().trim().toLowerCase();

        if (!purchaseDecision.equals("yes")) {
            System.out.println("Purchase cancelled.");
            return transactionCount; // Exit without completing the transaction
        }

        // Proceed with the ticket purchase
        availableSeats[destinationIndex] -= numberOfPassengers; // Reduce available seats
        ticketDetails[transactionCount][0] = passengerName;
        ticketDetails[transactionCount][1] = DESTINATIONS[destinationIndex];
        ticketFare[transactionCount] = fare;

        System.out.println("\n***************");
        System.out.println("* PASSENGER'S DETAILS *");
        System.out.println("*************");
        System.out.println("PASSENGER'S NAME: " + passengerName);
        System.out.println("PASSENGER'S DESTINATION: " + DESTINATIONS[destinationIndex]);
        System.out.println("TOTAL FARE: Rs" + fare);
        System.out.println("*************\n");

        return transactionCount + 1;
    }

    private static boolean isNameUsed(String[][] ticketDetails, String name, int transactionCount) {
        for (int i = 0; i < transactionCount; i++) {
            if (ticketDetails[i][0].equalsIgnoreCase(name)) {
                System.out.println("Sorry, passenger's name has already been used!");
                return true;
            }
        }
        return false;
    }

    private static int getDestinationIndex(BufferedReader in) throws IOException {
        int destinationIndex = -1;
        while (true) {
            System.out.print("ENTER DESTINATION [number]: ");
            try {
                destinationIndex = Integer.parseInt(in.readLine()) - 1; // Adjust for 0-based index
                if (destinationIndex >= 0 && destinationIndex < DESTINATIONS.length) {
                    return destinationIndex;
                } else {
                    System.out.println("Invalid input! Please select a valid destination.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private static int getNumberOfPassengers(BufferedReader in) throws IOException {
        int numberOfPassengers = 0;
        while (true) {
            System.out.print("HOW MANY PASSENGERS ARE YOU?: ");
            try {
                numberOfPassengers = Integer.parseInt(in.readLine());
                if (numberOfPassengers > 0) {
                    return numberOfPassengers;
                } else {
                    System.out.println("Invalid input! Number of passengers must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    private static double calculateFare(int destinationIndex) {
        // Assuming the distance is from Tokyo (index 0) to the destination
        int distance = DISTANCES[destinationIndex];
        return distance * FARE_PER_KM;
    }

    private static void processTransaction(BufferedReader in, String[][] ticketDetails, double[] ticketFare, int transactionCount) throws IOException {
        System.out.print("ENTER PASSENGER'S NAME: ");
        String searchName = in.readLine();

        boolean found = false;
        for (int i = 0; i < transactionCount; i++) {
            if (ticketDetails[i][0].equalsIgnoreCase(searchName)) {
                found = true;
                System.out.println("*************");
                System.out.println("* PASSENGER'S DETAILS *");
                System.out.println("*************");
                System.out.println("PASSENGER'S NAME: " + ticketDetails[i][0]);
                System.out.println("PASSENGER'S DESTINATION: " + ticketDetails[i][1]);
                System.out.println("TOTAL FARE: Rs" + ticketFare[i]);
                System.out.println("*************");
                break;
            }
        }

        if (!found) {
            System.out.println("PASSENGER'S NAME NOT FOUND!");
        }
    }

    private static void viewPassengerDetails(BufferedReader in, String[][] ticketDetails, double[] ticketFare, int transactionCount) throws IOException {
        System.out.print("SEARCH PASSENGER'S NAME: ");
        String searchName = in.readLine();

        boolean found = false;
        for (int i = 0; i < transactionCount; i++) {
            if (ticketDetails[i][0].equalsIgnoreCase(searchName)) {
                found = true;
                System.out.println("*************");
                System.out.println("* PASSENGER'S DETAILS *");
                System.out.println("*************");
                System.out.println("PASSENGER'S NAME: " + ticketDetails[i][0]);
                System.out.println("PASSENGER'S DESTINATION: " + ticketDetails[i][1]);
                System.out.println("TOTAL FARE: Rs" + ticketFare[i]);
                System.out.println("*************");
                break;
            }
        }

        if (!found) {
            System.out.println("PASSENGER'S NAME NOT FOUND!");
        }
    }
}
