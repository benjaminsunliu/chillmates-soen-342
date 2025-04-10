package app;
import app.auction.Auction;
import app.auction.AuctionHouse;
import app.auction.ObjectOfInterest;
import app.auction.Viewing;
import app.mappers.*;
import app.services.Availability;
import app.services.ServiceRequest;
import app.services.TimeSlot;
import app.users.Administrator;
import app.users.Client;
import app.users.Expert;
import app.users.User;

import javax.sound.midi.SysexMessage;
import java.util.Scanner;
import java.util.List;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Console {
    private User currentUser;
    private Scanner scanner;
    private UserMapper userMapper;
    private AvailabilityMapper availabilitymapper;
    private TimeSlotMapper timeSlotMapper;
    private ServiceRequestMapper serviceRequestMapper;
    private ObjectMapper objectMapper;
    private AuctionHouseMapper auctionHouseMapper;
    private AuctionMapper auctionMapper;

    public Console(){
        this.scanner = new Scanner(System.in);
        this.userMapper = new UserMapper();
        this.availabilitymapper = new AvailabilityMapper();
        this.timeSlotMapper = new TimeSlotMapper();
        this.serviceRequestMapper = new ServiceRequestMapper();
        this.objectMapper = new ObjectMapper();
        this.auctionHouseMapper = new AuctionHouseMapper();
        this.auctionMapper = new AuctionMapper();
    }

    public void run(){
        if(this.currentUser == null){
            mainMenu();
        } else if (this.currentUser instanceof Administrator) {
            adminMenu();
        } else if (this.currentUser instanceof Expert){
            expertMenu();
        } else if (this.currentUser instanceof Client){
            if (((Client) this.currentUser).getStatus()) {
                //clientMenu();
            } else {
                System.out.println("Your account is not approved yet. Please wait for an administrator to approve your account.");
                mainMenu();
            }
        }
        else {
            System.out.println("Invalid user type.");
            mainMenu();
        }

    }

    public void mainMenu(){
        System.out.println("-------------------------");
        System.out.println("Main Menu:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("-------------------------");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();

        switch(choice){
            case "1":
                promptLogin();
                break;
            case "2":
                promptRegister();
                break;
            case "3":
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                mainMenu();
        }
    }
    public void promptLogin(){
        System.out.println("Enter your email:");
        String email = scanner.nextLine().trim();
        System.out.println("Enter your password:");
        String password = scanner.nextLine().trim();
        User user = login(email, password);
    }
    public User login(String email, String password){
        User user = userMapper.findByCredentials(email, password);
        if (user != null) {
            System.out.println("Welcome " + user.getEmail());
            this.currentUser = user;
            run();
        } else {
            System.out.println("Invalid email or password. Please try again.");
            promptLogin();
        }
        return user;
    }
    public void promptRegister(){
        System.out.println("Select a role:");
        System.out.println("1. Client");
        System.out.println("2. Go back");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();
        switch(choice){
            case "1":
                registerClient();
                break;
            case "2":
                mainMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                promptRegister();
        }
    }
    public void registerExpert(){
        System.out.println("Enter expert email:");
        String email = scanner.nextLine().trim();

        if (userMapper.findByEmail(email) != null) {
            System.out.println("Email already in use. Please try logging in or using a different email.");
            mainMenu();
            return;
        }

        System.out.println("Enter expert password:");
        String password = scanner.nextLine().trim();
        System.out.println("Enter expert area of expertise:");
        String expertise = scanner.nextLine().trim();
        System.out.println("Enter expert license number:");
        String license = scanner.nextLine().trim();
        User user = new Expert(email, password, expertise, license);
        userMapper.create(user);
        System.out.println("Expert account created successfully.");
        mainMenu();
    }
    public void registerClient(){
        System.out.println("Enter your email:");
        String email = scanner.nextLine().trim();

        if (userMapper.findByEmail(email) != null) {
            System.out.println("Email already in use. Please try logging in or using a different email.");
            mainMenu();
            return;
        }

        System.out.println("Enter your password:");
        String password = scanner.nextLine().trim();
        System.out.println("Enter your affiliation:");
        String affiliation = scanner.nextLine().trim();
        System.out.println("Enter your intent:");
        String intent = scanner.nextLine().trim();
        User user = new Client(email, password, affiliation, intent, false);
        userMapper.create(user);
        System.out.println("Client account created successfully.");
        mainMenu();
    }

    public void adminMenu(){
        System.out.println("-------------------------");
        System.out.println("Admin Menu:");
        System.out.println("1. User Management");
        System.out.println("2. Object of Interest Management");
        System.out.println("3. Auction Houses Management");
        System.out.println("4. Auction Management");
        System.out.println("5. Viewing Management");
        System.out.println("6. Service Request Management");
        System.out.println("7. Logout");

        System.out.println("-------------------------");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();
        switch(choice){
            case "1":
                userManagement();
                break;
            case "2":
                objectOfInterestManagement();
                break;
            case "3":
                auctionHouseManagement();
                break;
            case "4":
                auctionManagement();
                break;
            case "5":
                viewingManagement();
                break;
            case "6":
                serviceRequestManagement();
                break;
            case "7":
                this.currentUser = null;
                mainMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                adminMenu();
        }
    }

    private void serviceRequestManagement() {
        System.out.println("-------------------------");
        System.out.println("Service Request Management:");
        System.out.println("1. Add Service Request");
        System.out.println("2. Edit Service Request");
        System.out.println("3. Delete Service Request");
        System.out.println("4. View all Service Requests");
        System.out.println("5. Go back");
        System.out.println("-------------------------");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();
        switch(choice){
            case "1":
                addServiceRequest();
                break;
            case "2":
                editServiceRequest();
                break;
            case "3":
                deleteServiceRequest();
                break;
            case "4":
                viewAllServiceRequests();
                break;
            case "5":
                adminMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                serviceRequestManagement();
        }
    }

    private void deleteServiceRequest() {
        System.out.println("-------------------------");
        List<ServiceRequest> serviceRequests = serviceRequestMapper.findAll();
        if (serviceRequests.isEmpty()) {
            System.out.println("No service requests found.");
            serviceRequestManagement();
            return;
        }
        System.out.println("Select a service request to delete:");
        for (int i = 0; i < serviceRequests.size(); i++) {
            ServiceRequest request = serviceRequests.get(i);
            System.out.printf("%d. %s%n", i + 1, request.toString());
        }
        System.out.println("Enter the number of the service request to delete, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            serviceRequestManagement();
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            serviceRequestManagement();
            return;
        }
        if (index < 0 || index >= serviceRequests.size()) {
            System.out.println("Selection out of range. Try again.");
            serviceRequestManagement();
            return;
        }
        ServiceRequest selectedServiceRequest = serviceRequests.get(index);
        serviceRequestMapper.delete(selectedServiceRequest);
        System.out.println("Service request deleted successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        serviceRequestManagement();
    }

    private void editServiceRequest() {
        System.out.println("-------------------------");
        List<ServiceRequest> serviceRequests = serviceRequestMapper.findAll();
        if (serviceRequests.isEmpty()) {
            System.out.println("No service requests found.");
            serviceRequestManagement();
            return;
        }
        System.out.println("Select a service request to edit:");
        for (int i = 0; i < serviceRequests.size(); i++) {
            ServiceRequest request = serviceRequests.get(i);
            System.out.printf("%d. %s%n", i + 1, request.toString());
        }
        System.out.println("Enter the number of the service request to edit, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            serviceRequestManagement();
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            serviceRequestManagement();
            return;
        }
        if (index < 0 || index >= serviceRequests.size()) {
            System.out.println("Selection out of range. Try again.");
            serviceRequestManagement();
            return;
        }
        ServiceRequest selectedServiceRequest = serviceRequests.get(index);
        System.out.printf("Editing service request: %s%n", selectedServiceRequest.toString());
        System.out.println("Enter new request type (or press Enter to keep current):");
        String newRequestType = scanner.nextLine().trim();
        if (!newRequestType.isEmpty()) {
            selectedServiceRequest.setRequestType(newRequestType);
        }
        List<Expert> experts = userMapper.findExperts();
        if (experts.isEmpty()) {
            System.out.println("No experts found.");
            serviceRequestManagement();
            return;
        }
        System.out.println("Select a new expert (or press Enter to keep current):");
        for (int i = 0; i < experts.size(); i++) {
            Expert expert = experts.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, expert.getEmail(), expert.getAreaOfExpertise());
        }
        System.out.println("Enter the number of the expert, or 'q' to go back:");
        input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            serviceRequestManagement();
            return;
        }
        if (!input.isEmpty()) {
            try {
                index = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'q' to go back.");
                serviceRequestManagement();
                return;
            }
            if (index < 0 || index >= experts.size()) {
                System.out.println("Selection out of range. Try again.");
                serviceRequestManagement();
                return;
            }
            Expert selectedExpert = experts.get(index);
            selectedServiceRequest.setAssignedExpert(selectedExpert);
        }
        List<Client> clients = userMapper.findClients();
        if (clients.isEmpty()) {
            System.out.println("No clients found.");
            serviceRequestManagement();
            return;
        }
        System.out.println("Select a new client (or press Enter to keep current):");
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, client.getEmail(), client.getAffiliation());
        }
        System.out.println("Enter the number of the client, or 'q' to go back:");
        input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            serviceRequestManagement();
            return;
        }
        if (!input.isEmpty()) {
            try {
                index = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'q' to go back.");
                serviceRequestManagement();
                return;
            }
            if (index < 0 || index >= clients.size()) {
                System.out.println("Selection out of range. Try again.");
                serviceRequestManagement();
                return;
            }
            Client selectedClient = clients.get(index);
            selectedServiceRequest.setRequestingClient(selectedClient);
        }
        List<TimeSlot> timeSlots = selectedServiceRequest.getAssignedExpert().getAvailability().getTimeSlots();
        if (timeSlots.isEmpty()) {
            System.out.println("No time slots available for this expert.");
            serviceRequestManagement();
            return;
        }
        System.out.println("Select a new time slot (or press Enter to keep current):");
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot timeSlot = timeSlots.get(i);
            System.out.printf("%d. %s - %s%n", i + 1, timeSlot.getStartTime(), timeSlot.getEndTime());
        }
        System.out.println("Enter the number of the time slot, or 'q' to go back:");
        input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            serviceRequestManagement();
            return;
        }
        if (!input.isEmpty()) {
            try {
                index = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'q' to go back.");
                serviceRequestManagement();
                return;
            }
            if (index < 0 || index >= timeSlots.size()) {
                System.out.println("Selection out of range. Try again.");
                serviceRequestManagement();
                return;
            }
            // Check if the time slot is already booked
            if (timeSlots.get(index).getServiceRequest() != null) {
                System.out.println("This time slot is already booked. Please select another one.");
                serviceRequestManagement();
                return;
            }

        }
        TimeSlot selectedTimeSlot = timeSlots.get(index);
        selectedServiceRequest.setTimeSlot(selectedTimeSlot);
        serviceRequestMapper.update(selectedServiceRequest);
        System.out.println("Service request updated successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        serviceRequestManagement();
    }

    private void addServiceRequest() {
        System.out.println("-------------------------");
        System.out.println("Enter the request type:");
        String requestType = scanner.nextLine().trim();
        System.out.println("Select an expert:");
        List<Expert> experts = userMapper.findExperts();
        if (experts.isEmpty()) {
            System.out.println("No experts found.");
            serviceRequestManagement();
            return;
        }
        for (int i = 0; i < experts.size(); i++) {
            Expert expert = experts.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, expert.getEmail(), expert.getAreaOfExpertise());
        }
        System.out.println("Enter the number of the expert, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            serviceRequestManagement();
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            serviceRequestManagement();
            return;
        }
        if (index < 0 || index >= experts.size()) {
            System.out.println("Selection out of range. Try again.");
            serviceRequestManagement();
            return;
        }
        Expert selectedExpert = experts.get(index);

        System.out.println("Select a time slot:");
        List<TimeSlot> timeSlots = selectedExpert.getAvailability().getTimeSlots();
        if (timeSlots.isEmpty()) {
            System.out.println("No time slots available for this expert.");
            serviceRequestManagement();
            return;
        }
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot timeSlot = timeSlots.get(i);
            System.out.printf("%d. %s - %s%n", i + 1, timeSlot.getStartTime(), timeSlot.getEndTime());
        }
        System.out.println("Enter the number of the time slot, or 'q' to go back:");
        input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            serviceRequestManagement();
            return;
        }
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            serviceRequestManagement();
            return;
        }
        if (index < 0 || index >= timeSlots.size()) {
            System.out.println("Selection out of range. Try again.");
            serviceRequestManagement();
            return;
        }
        TimeSlot timeSlot = timeSlots.get(index);
        // Check if the time slot is already booked
        if (timeSlot.getServiceRequest() != null) {
            System.out.println("This time slot is already booked. Please select another one.");
            serviceRequestManagement();
            return;
        }

        List<Client> clients = userMapper.findClients();
        Client selectedClient = null;
        System.out.println("Select a client:");
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, client.getEmail(), client.getAffiliation());
        }
        System.out.println("Enter the number of the client, or 'q' to go back:");
        input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            serviceRequestManagement();
            return;
        }
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            serviceRequestManagement();
            return;
        }
        if (index < 0 || index >= clients.size()) {
            System.out.println("Selection out of range. Try again.");
            serviceRequestManagement();
            return;
        }
        selectedClient = clients.get(index);

        ServiceRequest serviceRequest = new ServiceRequest(requestType, selectedExpert, selectedClient, timeSlot);
        serviceRequestMapper.create(serviceRequest);

        System.out.println("Service request created successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        serviceRequestManagement();
    }

    private void viewAllServiceRequests() {
        List<ServiceRequest> serviceRequests = serviceRequestMapper.findAll();
        if (serviceRequests.isEmpty()) {
            System.out.println("No service requests found.");
            serviceRequestManagement();
            return;
        }
        System.out.println("\nService Requests:");
        for (int i = 0; i < serviceRequests.size(); i++) {
            ServiceRequest request = serviceRequests.get(i);
            System.out.printf(request.toString());
        }
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        serviceRequestManagement();
    }

    private void viewingManagement() {
        System.out.println("-------------------------");
        System.out.println("Viewing Management:");
        System.out.println("1. Add Viewing");
        System.out.println("2. Edit Viewing");
        System.out.println("3. Delete Viewing");
        System.out.println("4. View all viewings");
        System.out.println("5. Go back");
        System.out.println("-------------------------");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();
        switch(choice){
            case "1":
                addViewing();
                break;
            case "2":
                editViewing();
                break;
            case "3":
                deleteViewing();
                break;
            case "4":
                viewAllViewings();
                break;
            case "5":
                adminMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                viewingManagement();
        }
    }

    private void deleteViewing() {
        System.out.println("-------------------------");
        List<Viewing> viewings = auctionMapper.findAllViewings();
        if (viewings.isEmpty()) {
            System.out.println("No viewings found.");
            viewingManagement();
            return;
        }
        System.out.println("Select a viewing to delete:");
        for (int i = 0; i < viewings.size(); i++) {
            Viewing viewing = viewings.get(i);
            System.out.printf("%d. %s Viewing (%s-%s) at %s %n", i + 1, viewing.getAuction().getAuctionType(), viewing.getStartTime(), viewing.getEndTime(), viewing.getAuction().getAuctionHouse().getName());
        }
        System.out.println("Enter the number of the viewing to delete, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            viewingManagement();
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            viewingManagement();
            return;
        }
        if (index < 0 || index >= viewings.size()) {
            System.out.println("Selection out of range. Try again.");
            viewingManagement();
            return;
        }

        Viewing selectedViewing = viewings.get(index);
        Auction auction = selectedViewing.getAuction();
        if (auction != null && auction.getViewing() == selectedViewing) {
            auction.setViewing(null); // Detach the viewing
            auctionMapper.update(auction); // Persist the change
        }

        auctionMapper.deleteViewing(selectedViewing); // Now safe to delete
        System.out.println("Viewing deleted successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        viewingManagement();
    }

    private void editViewing() {
        System.out.println("-------------------------");
        List<Viewing> viewings = auctionMapper.findAllViewings();
        if (viewings.isEmpty()) {
            System.out.println("No viewings found.");
            viewingManagement();
            return;
        }
        System.out.println("Select a viewing to edit:");
        for (int i = 0; i < viewings.size(); i++) {
            Viewing viewing = viewings.get(i);
            System.out.printf("%d. %s Viewing (%s-%s) at %s %n", i + 1, viewing.getAuction().getAuctionType(), viewing.getStartTime(), viewing.getEndTime(),viewing.getAuction().getAuctionHouse().getName());
        }
        System.out.println("Enter the number of the viewing to edit, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            viewingManagement();
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            viewingManagement();
            return;
        }
        if (index < 0 || index >= viewings.size()) {
            System.out.println("Selection out of range. Try again.");
            viewingManagement();
            return;
        }
        Viewing selectedViewing = viewings.get(index);
        System.out.printf("Editing viewing: %s%n", selectedViewing.getAuction().getAuctionType());
        System.out.println("Enter new start time (yyyy-MM-dd HH:mm) or press Enter to keep current:");
        String newStartTimeInput = scanner.nextLine().trim();
        if (!newStartTimeInput.isEmpty()) {
            LocalDateTime newStartTime;
            try {
                newStartTime = LocalDateTime.parse(newStartTimeInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm.");
                viewingManagement();
                return;
            }
            selectedViewing.setStartTime(newStartTime);
        }
        System.out.println("Enter new end time (yyyy-MM-dd HH:mm) or press Enter to keep current:");
        String newEndTimeInput = scanner.nextLine().trim();
        if (!newEndTimeInput.isEmpty()) {
            LocalDateTime newEndTime;
            try {
                newEndTime = LocalDateTime.parse(newEndTimeInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm.");
                viewingManagement();
                return;
            }
            selectedViewing.setEndTime(newEndTime);
        }
        auctionMapper.updateViewing(selectedViewing);
        System.out.println("Viewing updated successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        viewingManagement();
    }

    private void addViewing() {
        System.out.println("-------------------------");
        List<Auction> auctions = auctionMapper.findAll();
        if (auctions.isEmpty()) {
            System.out.println("No auctions found. Please create an auction first.");
            auctionManagement();
            return;
        }
        System.out.println("Select an auction:");
        for (int i = 0; i < auctions.size(); i++) {
            Auction auction = auctions.get(i);
            System.out.printf("%d. %s (%s) at %s %n", i + 1, auction.getAuctionType(), auction.getIsOnline() ? "Online" : "In-person", auction.getAuctionHouse().getName());
        }
        System.out.println("Enter the number of the auction, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            viewingManagement();
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            viewingManagement();
            return;
        }
        if (index < 0 || index >= auctions.size()) {
            System.out.println("Selection out of range. Try again.");
            viewingManagement();
            return;
        }
        Auction selectedAuction = auctions.get(index);
        if (selectedAuction.getViewing() != null) {
            System.out.println("This auction already has a viewing scheduled.");
            viewingManagement();
            return;
        }
        System.out.println("Enter start time (yyyy-MM-dd HH:mm):");
        String startTimeInput = scanner.nextLine().trim();
        LocalDateTime startTime;
        try {
            startTime = LocalDateTime.parse(startTimeInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm.");
            viewingManagement();
            return;
        }

        System.out.println("Enter end time (yyyy-MM-dd HH:mm):");
        String endTimeInput = scanner.nextLine().trim();
        LocalDateTime endTime;
        try {
            endTime = LocalDateTime.parse(endTimeInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm.");
            viewingManagement();
            return;
        }

        Viewing viewing = new Viewing(startTime, endTime, selectedAuction);
        selectedAuction.setViewing(viewing);
        auctionMapper.createViewing(viewing);
        auctionMapper.update(selectedAuction);
        System.out.println("Viewing created successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        viewingManagement();
    }

    private void viewAllViewings() {
        List<Viewing> viewings = auctionMapper.findAllViewings();
        if (viewings.isEmpty()) {
            System.out.println("No viewings found.");
            viewingManagement();
            return;
        }
        System.out.println("\nViewings:");
        for (int i = 0; i < viewings.size(); i++) {
            Viewing viewing = viewings.get(i);
            System.out.printf("%d. %s Viewing (%s-%s) at %s %n", i + 1, viewing.getAuction().getAuctionType(), viewing.getStartTime(), viewing.getEndTime(),viewing.getAuction().getAuctionHouse().getName());
        }
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        viewingManagement();
    }

    private void auctionManagement() {
        System.out.println("-------------------------");
        System.out.println("Auction Management:");
        System.out.println("1. Add Auction");
        System.out.println("2. Edit Auction");
        System.out.println("3. Delete Auction");
        System.out.println("4. View all auctions");
        System.out.println("5. Go back");
        System.out.println("-------------------------");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();
        switch(choice){
            case "1":
                addAuction();
                break;
            case "2":
                editAuction();
                break;
            case "3":
                deleteAuction();
                break;
            case "4":
                viewAllAuctions();
                break;
            case "5":
                adminMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                auctionManagement();
        }
    }

    private void deleteAuction() {
        System.out.println("-------------------------");
        List<Auction> auctions = auctionMapper.findAll();
        if (auctions.isEmpty()) {
            System.out.println("No auctions found.");
            auctionManagement();
            return;
        }
        System.out.println("Select an auction to delete:");
        for (int i = 0; i < auctions.size(); i++) {
            Auction auction = auctions.get(i);
            System.out.printf("%d. %s (%s) at %s %n", i + 1, auction.getAuctionType(), auction.getIsOnline() ? "Online" : "In-person", auction.getAuctionHouse().getName());
        }
        System.out.println("Enter the number of the auction to delete, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            auctionManagement();
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            auctionManagement();
            return;
        }
        if (index < 0 || index >= auctions.size()) {
            System.out.println("Selection out of range. Try again.");
            auctionManagement();
            return;
        }
        Auction auctionToDelete = auctions.get(index);
        auctionToDelete.getAuctionHouse().removeAuction(auctionToDelete);
        auctionMapper.delete(auctionToDelete);
        System.out.println("Auction deleted successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        auctionManagement();
    }

    private void editAuction() {
        System.out.println("-------------------------");
        List<Auction> auctions = auctionMapper.findAll();
        if (auctions.isEmpty()) {
            System.out.println("No auctions found.");
            auctionManagement();
            return;
        }
        System.out.println("Select an auction to edit:");
        for(int i = 0; i < auctions.size(); i++) {
            Auction auction = auctions.get(i);
            System.out.printf("%d. %s (%s) at %s %n", i + 1, auction.getAuctionType(), auction.getIsOnline() ? "Online" : "In-person", auction.getAuctionHouse().getName());
        }
        System.out.println("Enter the number of the auction to edit, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            auctionManagement();
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            auctionManagement();
            return;
        }
        if (index < 0 || index >= auctions.size()) {
            System.out.println("Selection out of range. Try again.");
            auctionManagement();
            return;
        }
        Auction auctionToEdit = auctions.get(index);
        System.out.printf("Editing auction: %s%n", auctionToEdit.getAuctionType());
        System.out.println("Enter new auction type (or press Enter to keep current):");
        String newAuctionType = scanner.nextLine().trim();
        if (!newAuctionType.isEmpty()) {
            auctionToEdit.setAuctionType(newAuctionType);
        }
        System.out.println("Is the auction online? (true/false):");
        boolean isOnline;
        while (true) {
            String onlineInput = scanner.nextLine().trim();
            if (onlineInput.equalsIgnoreCase("true")) {
                isOnline = true;
                break;
            } else if (onlineInput.equalsIgnoreCase("false")) {
                isOnline = false;
                break;
            } else {
                System.out.println("Invalid input. Please enter 'true' or 'false':");
            }
        }
        auctionToEdit.setIsOnline(isOnline);
        auctionMapper.update(auctionToEdit);
        System.out.println("Auction updated successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        auctionManagement();
    }

    private void addAuction() {
        System.out.println("-------------------------");
        List<AuctionHouse> auctionHouses = auctionHouseMapper.findAll();
        if (auctionHouses.isEmpty()) {
            System.out.println("No auction houses found. Please create an auction house first.");
            auctionHouseManagement();
            return;
        }
        System.out.println("Select an auction house:");
        for (int i = 0; i < auctionHouses.size(); i++) {
            AuctionHouse auctionHouse = auctionHouses.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, auctionHouse.getName(), auctionHouse.getLocation());
        }
        System.out.println("Enter the number of the auction house, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            auctionManagement();
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            auctionManagement();
            return;
        }
        if (index < 0 || index >= auctionHouses.size()) {
            System.out.println("Selection out of range. Try again.");
            auctionManagement();
            return;
        }
        AuctionHouse selectedAuctionHouse = auctionHouses.get(index);
        System.out.println("Enter auction type (e.g., Art, Antiques):");
        String auctionType = scanner.nextLine().trim();
        System.out.println("Is the auction online? (true/false):");
        boolean isOnline;
        while (true) {
            String onlineInput = scanner.nextLine().trim();
            if (onlineInput.equalsIgnoreCase("true")) {
                isOnline = true;
                break;
            } else if (onlineInput.equalsIgnoreCase("false")) {
                isOnline = false;
                break;
            } else {
                System.out.println("Invalid input. Please enter 'true' or 'false':");
            }
        }
        Auction auction = new Auction(auctionType, selectedAuctionHouse, isOnline);
        auctionMapper.create(auction);
        selectedAuctionHouse.addAuction(auction);
        auctionHouseMapper.update(selectedAuctionHouse);
        System.out.println("Auction created successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        auctionManagement();
    }

    private void viewAllAuctions() {
        List<Auction> auctions = auctionMapper.findAll();
        if (auctions.isEmpty()) {
            System.out.println("No auctions found.");
            auctionManagement();
            return;
        }
        System.out.println("\nAuctions:");
        for (int i = 0; i < auctions.size(); i++) {
            Auction auction = auctions.get(i);
            System.out.printf("%d. %s (%s) at %s %n", i + 1, auction.getAuctionType(), auction.getIsOnline() ? "Online" : "In-person", auction.getAuctionHouse().getName());
        }
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        auctionManagement();
    }

    private void auctionHouseManagement() {
        System.out.println("-------------------------");
        System.out.println("Auction House Management:");
        System.out.println("1. Add Auction House");
        System.out.println("2. Edit Auction House");
        System.out.println("3. Delete Auction House");
        System.out.println("4. View all auction houses");
        System.out.println("5. Go back");
        System.out.println("-------------------------");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();
        switch(choice){
            case "1":
                addAuctionHouse();
                break;
            case "2":
                editAuctionHouse();
                break;
            case "3":
                deleteAuctionHouse();
                break;
            case "4":
                viewAllAuctionHouses();
                break;
            case "5":
                adminMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                auctionHouseManagement();
        }
    }

    private void deleteAuctionHouse() {
        System.out.println("-------------------------");
        List<AuctionHouse> auctionHouses = auctionHouseMapper.findAll();
        if (auctionHouses.isEmpty()) {
            System.out.println("No auction houses found.");
            auctionHouseManagement();
            return;
        }
        System.out.println("Select an auction house to delete:");
        for (int i = 0; i < auctionHouses.size(); i++) {
            AuctionHouse auctionHouse = auctionHouses.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, auctionHouse.getName(), auctionHouse.getLocation());
        }
        System.out.println("Enter the number of the auction house to delete, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            auctionHouseManagement();
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            auctionHouseManagement();
            return;
        }
        if (index < 0 || index >= auctionHouses.size()) {
            System.out.println("Selection out of range. Try again.");
            auctionHouseManagement();
            return;
        }
        AuctionHouse auctionHouseToDelete = auctionHouses.get(index);
        // Remove all auctions associated with this auction house
        auctionHouseMapper.delete(auctionHouseToDelete);
        System.out.println("Auction house deleted successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        auctionHouseManagement();
    }

    private void editAuctionHouse() {
        System.out.println("-------------------------");
        List<AuctionHouse> auctionHouses = auctionHouseMapper.findAll();
        if (auctionHouses.isEmpty()) {
            System.out.println("No auction houses found.");
            auctionHouseManagement();
            return;
        }
        System.out.println("Select an auction house to edit:");
        for (int i = 0; i < auctionHouses.size(); i++) {
            AuctionHouse auctionHouse = auctionHouses.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, auctionHouse.getName(), auctionHouse.getLocation());
        }
        System.out.println("Enter the number of the auction house to edit, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            auctionHouseManagement();
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            auctionHouseManagement();
            return;
        }
        if (index < 0 || index >= auctionHouses.size()) {
            System.out.println("Selection out of range. Try again.");
            auctionHouseManagement();
            return;
        }
        AuctionHouse auctionHouseToEdit = auctionHouses.get(index);
        System.out.printf("Editing auction house: %s%n", auctionHouseToEdit.getName());
        System.out.println("Enter new name (or press Enter to keep current):");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            auctionHouseToEdit.setName(newName);
        }
        System.out.println("Enter new location (or press Enter to keep current):");
        String newLocation = scanner.nextLine().trim();
        if (!newLocation.isEmpty()) {
            auctionHouseToEdit.setLocation(newLocation);
        }
        auctionHouseMapper.update(auctionHouseToEdit);
        System.out.println("Auction house updated successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        auctionHouseManagement();
    }

    private void viewAllAuctionHouses() {
        List<AuctionHouse> auctionHouses = auctionHouseMapper.findAll();
        if (auctionHouses.isEmpty()) {
            System.out.println("No auction houses found.");
            auctionHouseManagement();
            return;
        }
        System.out.println("\nAuction Houses:");
        for (int i = 0; i < auctionHouses.size(); i++) {
            AuctionHouse auctionHouse = auctionHouses.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, auctionHouse.getName(), auctionHouse.getLocation());
        }
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        auctionHouseManagement();
    }

    private void addAuctionHouse() {
        System.out.println("-------------------------");
        System.out.println("Enter auction house name:");
        String name = scanner.nextLine().trim();
        System.out.println("Enter auction house location:");
        String location = scanner.nextLine().trim();
        AuctionHouse auctionHouse = new AuctionHouse(name, location);
        auctionHouseMapper.create(auctionHouse);
        System.out.println("Auction house created successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        auctionHouseManagement();
    }

    private void objectOfInterestManagement() {
        System.out.println("-------------------------");
        System.out.println("Object of Interest Management:");
        System.out.println("1. Add Object of Interest");
        System.out.println("2. Edit Object of Interest");
        System.out.println("3. Delete Object of Interest");
        System.out.println("4. View all objects of interest");
        System.out.println("5. Go back");
        System.out.println("-------------------------");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();
        switch(choice){
            case "1":
                addObjectOfInterest();
                break;
            case "2":
                editObjectOfInterest();
                break;
            case "3":
                deleteObjectOfInterest();
                break;
            case "4":
                viewAllObjectsOfInterest();
                break;
            case "5":
                adminMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                objectOfInterestManagement();
        }
    }

    private void deleteObjectOfInterest() {
        System.out.println("-------------------------");
        List<ObjectOfInterest> objects = objectMapper.findAll();
        if (objects.isEmpty()) {
            System.out.println("No objects of interest found.");
            objectOfInterestManagement();
            return;
        }
        System.out.println("Select an object of interest to delete:");
        for (int i = 0; i < objects.size(); i++) {
            ObjectOfInterest object = objects.get(i);
            System.out.printf("%d. %s (%s) (Owned by: %s)%n", i + 1, object.getTitle(), object.getType(), object.getInstitution().getName());
        }
        System.out.println("Enter the number of the object to delete, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            objectOfInterestManagement();
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            objectOfInterestManagement();
            return;
        }
        if (index < 0 || index >= objects.size()) {
            System.out.println("Selection out of range. Try again.");
            objectOfInterestManagement();
            return;
        }
        ObjectOfInterest objectToDelete = objects.get(index);
        objectToDelete.getInstitution().removeObject(objectToDelete);
        objectMapper.delete(objectToDelete);
        System.out.println("Object of interest deleted successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        objectOfInterestManagement();
    }

    private void editObjectOfInterest() {
        System.out.println("-------------------------");
        List<ObjectOfInterest> objects = objectMapper.findAll();
        if (objects.isEmpty()) {
            System.out.println("No objects of interest found.");
            objectOfInterestManagement();
            return;
        }
        System.out.println("Select an object of interest to edit:");
        for (int i = 0; i < objects.size(); i++) {
            ObjectOfInterest object = objects.get(i);
            System.out.printf("%d. %s (%s) (Owned by: %s)%n", i + 1, object.getTitle(), object.getType(), object.getInstitution().getName());
        }
        System.out.println("Enter the number of the object to edit, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            objectOfInterestManagement();
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            objectOfInterestManagement();
            return;
        }
        if (index < 0 || index >= objects.size()) {
            System.out.println("Selection out of range. Try again.");
            objectOfInterestManagement();
            return;
        }
        ObjectOfInterest objectToEdit = objects.get(index);
        System.out.printf("Editing object of interest: %s%n", objectToEdit.getTitle());
        System.out.println("Enter new title (or press Enter to keep current):");
        String newTitle = scanner.nextLine().trim();
        if (!newTitle.isEmpty()) {
            objectToEdit.setTitle(newTitle);
        }
        System.out.println("Enter new description (or press Enter to keep current):");
        String newDescription = scanner.nextLine().trim();
        if (!newDescription.isEmpty()) {
            objectToEdit.setDescription(newDescription);
        }
        System.out.println("Enter new type (or press Enter to keep current):");
        String newType = scanner.nextLine().trim();
        if (!newType.isEmpty()) {
            objectToEdit.setType(newType);
        }
        System.out.println("Enter new institution name (or press Enter to keep current):");
        String newInstitutionName = scanner.nextLine().trim();
        if (!newInstitutionName.isEmpty()) {
            InstitutionMapper institutionMapper = new InstitutionMapper();
            Institution newInstitution = institutionMapper.findByName(newInstitutionName);
            if (newInstitution == null) {
                System.out.println("Institution not found. Please create the institution first.");
                objectOfInterestManagement();
                return;
            }
            objectToEdit.setInstitution(newInstitution);
        }
        objectMapper.update(objectToEdit);
        System.out.println("Object of interest updated successfully.");
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        objectOfInterestManagement();
    }

    private void viewAllObjectsOfInterest() {
        List<ObjectOfInterest> objects = objectMapper.findAll();
        if (objects.isEmpty()) {
            System.out.println("No objects of interest found.");
            objectOfInterestManagement();
            return;
        }
        System.out.println("\nObjects of Interest:");
        for (int i = 0; i < objects.size(); i++) {
            ObjectOfInterest object = objects.get(i);
            System.out.printf("%d. %s (%s) (Owned by: %s)%n", i + 1, object.getTitle(), object.getType(), object.getInstitution().getName());
        }
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        objectOfInterestManagement();
    }

    private void addObjectOfInterest() {
        System.out.println("-------------------------");
        System.out.println("Enter object of interest title:");
        String title = scanner.nextLine().trim();
        System.out.println("Enter object of interest description:");
        String description = scanner.nextLine().trim();
        System.out.println("Enter object of interest type:");
        String type = scanner.nextLine().trim();
        System.out.println("Enter institution name:");
        String institutionName = scanner.nextLine().trim();

        InstitutionMapper institutionMapper = new InstitutionMapper();
        Institution institution = institutionMapper.findByName(institutionName);
        if (institution == null) {
            System.out.println("Institution not found. Please create the institution first.");
            adminMenu();
            return;
        }

        ObjectOfInterest objectOfInterest = new ObjectOfInterest(title, description, type, institution);
        objectMapper.create(objectOfInterest);
        System.out.println("Object of interest created successfully.");
        adminMenu();
    }

    public void expertMenu(){
        System.out.println("-------------------------");
        System.out.println("Expert Menu:");
        System.out.println("1. View Institution Information");
        System.out.println("2. Availability Management");
        System.out.println("3. Logout");
        System.out.println("-------------------------");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();
        switch(choice){
            case "1":
                viewInstitutionInformation();
                break;
            case "2":
                manageExpertAvailability((Expert) this.currentUser);
                break;
            case "3":
                this.currentUser = null;
                mainMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                expertMenu();
        }
    }



    private void viewInstitutionInformation() {
        System.out.println("-------------------------");
        System.out.println("Institution Information:");
        System.out.println("1. View Objects of Interest");
        System.out.println("2. View Auctions");
        System.out.println("3. View Viewings");
        System.out.println("4. Go back");
        System.out.println("-------------------------");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();
        switch(choice){
            case "1":
                viewObjectsOfInterest();
                break;
            case "2":
                viewAuctions();
                break;
            case "3":
                viewViewings();
                break;
            case "4":
                expertMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                viewInstitutionInformation();
        }
    }

    private void viewViewings() {
        AuctionMapper auctionMapper = new AuctionMapper();
        List<Auction> auctions = auctionMapper.findAll();
        if (auctions.isEmpty()) {
            System.out.println("No viewings found.");
            viewInstitutionInformation();
            return;
        }
        System.out.println("\nViewings:");
        for (int i = 0; i < auctions.size(); i++) {
            Auction auction = auctions.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, auction.getAuctionType(), auction.getIsOnline() ? "Online" : "In-person");
            if (auction.getViewing() != null) {
                Viewing viewing = auction.getViewing();
                System.out.printf("   Viewing: Start Time: %s, End Time: %s%n", viewing.getStartTime(), viewing.getEndTime());
            } else {
                System.out.println("   No viewing scheduled.");
            }
        }

        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        viewInstitutionInformation();
    }

    private void viewAuctions() {
        AuctionHouseMapper auctionMapper = new AuctionHouseMapper();
        List<List<Auction>> auctions = auctionMapper.findAllAuctionsByAuctionHouse();
        if (auctions.isEmpty()) {
            System.out.println("No auctions found.");
            viewInstitutionInformation();
            return;
        }
        System.out.println("\nAuctions:");
        for (int i = 0; i < auctions.size(); i++) {
            List<Auction> auctionList = auctions.get(i);
            System.out.printf("Auction House %d:%n", i + 1);
            for (int j = 0; j < auctionList.size(); j++) {
                Auction auction = auctionList.get(j);
                System.out.printf("%d. %s (%s)%n", j + 1, auction.getAuctionType(), auction.getIsOnline() ? "Online" : "In-person");
            }
        }
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        viewInstitutionInformation();
    }

    private void viewObjectsOfInterest() {
        InstitutionMapper institutionMapper = new InstitutionMapper();
        List<List<ObjectOfInterest>> objects = institutionMapper.findAllObjectsByInstitution();
        if (objects.isEmpty()) {
            System.out.println("No objects of interest found.");
            viewInstitutionInformation();
            return;
        }
        System.out.println("\nObjects of Interest:");
        for (int i = 0; i < objects.size(); i++) {
            List<ObjectOfInterest> objectList = objects.get(i);
            System.out.printf("Institution %d:%n", i + 1);
            for (int j = 0; j < objectList.size(); j++) {
                ObjectOfInterest object = objectList.get(j);
                System.out.printf("%d. %s (%s)%n", j + 1, object.getTitle(), object.getType());
            }
        }
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        viewInstitutionInformation();
    }

    private void userManagement() {
        System.out.println("-------------------------");
        System.out.println("User Management:");
        System.out.println("1. Client Management");
        System.out.println("2. Expert Management");
        System.out.println("3. View all users");
        System.out.println("4. Go back");
        System.out.println("-------------------------");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();
        switch(choice){
            case "1":
                clientManagement();
                break;
            case "2":
                expertManagement();
                break;
            case "3":
                viewAllUsers();
                break;
            case "4":
                adminMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                userManagement();
        }
    }

    private void viewAllUsers() {
        List<User> users = userMapper.findNonAdministrators();

        if (users.isEmpty()) {
            System.out.println("No users found.");
            userManagement();
            return;
        }

        System.out.println("\nRegistered Users:");
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, u.getEmail(), u.getClass().getSimpleName());
        }

        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        userManagement();

    }

    private void clientManagement() {
        System.out.println("-------------------------");
        System.out.println("Client Management:");
        System.out.println("1. Approve Client");
        System.out.println("2. Revoke Client");
        System.out.println("3. Edit Client");
        System.out.println("4. Delete Client");
        System.out.println("5. Go back");
        System.out.println("-------------------------");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();
        switch(choice){
            case "1":
                approveClient();
                break;
            case "2":
                revokeClient();
                break;
            case "3":
                editClient();
                break;
            case "4":
                deleteClient();
                break;
            case "5":
                userManagement();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                clientManagement();
        }
    }

    private void approveClient() {
        List<Client> pendingClients = userMapper.findNonApprovedClients();

        if (pendingClients.isEmpty()) {
            System.out.println("No clients pending approval.");
            clientManagement(); // Return to client management menu
            return;
        }

        while (true) {
            System.out.println("\nClients pending approval:");
            for (int i = 0; i < pendingClients.size(); i++) {
                Client c = pendingClients.get(i);
                System.out.printf("%d. %s (%s)%n", i + 1, c.getEmail(), c.getAffiliation());
            }
            System.out.println("Enter the number of the client to approve, or 'q' to go back:");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                clientManagement();
                return;
            }

            int index;
            try {
                index = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'q' to quit.");
                continue;
            }

            if (index < 0 || index >= pendingClients.size()) {
                System.out.println("Selection out of range. Try again.");
                continue;
            }

            Client clientToApprove = pendingClients.get(index);
            clientToApprove.setStatus(true);
            userMapper.update(clientToApprove);

            System.out.printf("Client '%s' approved successfully.%n", clientToApprove.getEmail());

            // Ask if admin wants to approve another
            System.out.println("Approve another client? (y/n):");
            String again = scanner.nextLine().trim();
            if (!again.equalsIgnoreCase("y")) {
                clientManagement();
                return;
            }

            // Refresh list in case another admin concurrently approved one
            pendingClients = userMapper.findNonApprovedClients();
            if (pendingClients.isEmpty()) {
                System.out.println("No more clients pending approval.");
                clientManagement();
                return;
            }
        }
    }

    private void revokeClient(){
        List<Client> approvedClients = userMapper.findApprovedClients();

        if (approvedClients.isEmpty()) {
            System.out.println("No clients to revoke.");
            clientManagement(); // Return to client management menu
            return;
        }

        while (true) {
            System.out.println("\nClients to revoke:");
            for (int i = 0; i < approvedClients.size(); i++) {
                Client c = approvedClients.get(i);
                System.out.printf("%d. %s (%s)%n", i + 1, c.getEmail(), c.getAffiliation());
            }
            System.out.println("Enter the number of the client to revoke, or 'q' to go back:");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                clientManagement();
                return;
            }

            int index;
            try {
                index = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'q' to quit.");
                continue;
            }

            if (index < 0 || index >= approvedClients.size()) {
                System.out.println("Selection out of range. Try again.");
                continue;
            }

            Client clientToRevoke = approvedClients.get(index);
            clientToRevoke.setStatus(false);
            userMapper.update(clientToRevoke);

            System.out.printf("Client '%s' revoked successfully.%n", clientToRevoke.getEmail());

            // Ask if admin wants to revoke another
            System.out.println("Revoke another client? (y/n):");
            String again = scanner.nextLine().trim();
            if (!again.equalsIgnoreCase("y")) {
                clientManagement();
                return;
            }

            // Refresh list in case another admin concurrently revoked one
            approvedClients = userMapper.findApprovedClients();
            if (approvedClients.isEmpty()) {
                System.out.println("No more clients to revoke.");
                clientManagement();
                return;
            }
        }

    }

    private void editClient() {
        List<Client> clients = userMapper.findClients();

        if (clients.isEmpty()) {
            System.out.println("No clients found.");
            clientManagement();
            return;
        }

        while (true) {
            System.out.println("\nRegistered Clients:");
            for (int i = 0; i < clients.size(); i++) {
                Client c = clients.get(i);
                System.out.printf("%d. %s (%s)%n", i + 1, c.getEmail(), c.getAffiliation());
            }
            System.out.println("Enter the number of the client to edit, or 'q' to go back:");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                clientManagement();
                return;
            }

            int index;
            try {
                index = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'q' to go back.");
                continue;
            }

            if (index < 0 || index >= clients.size()) {
                System.out.println("Selection out of range. Try again.");
                continue;
            }

            Client client = clients.get(index);
            System.out.printf("Editing client: %s%n", client.getEmail());

            // Email
            System.out.printf("Current email: %s. Enter new email (or press Enter to keep):%n", client.getEmail());
            String newEmail = scanner.nextLine().trim();
            if (!newEmail.isEmpty() && !newEmail.equals(client.getEmail())) {
                if (userMapper.findByEmail(newEmail) != null) {
                    System.out.println("Email already in use. Email not changed.");
                } else {
                    client.setEmail(newEmail);
                }
            }

            // Affiliation
            System.out.printf("Current affiliation: %s. Enter new affiliation (or press Enter to keep):%n", client.getAffiliation());
            String newAffiliation = scanner.nextLine().trim();
            if (!newAffiliation.isEmpty()) {
                client.setAffiliation(newAffiliation);
            }

            // Intent
            System.out.printf("Current intent: %s. Enter new intent (or press Enter to keep):%n", client.getIntent());
            String newIntent = scanner.nextLine().trim();
            if (!newIntent.isEmpty()) {
                client.setIntent(newIntent);
            }

            // Password
            System.out.println("Enter new password (or press Enter to keep current one):");
            String newPassword = scanner.nextLine().trim();
            if (!newPassword.isEmpty()) {
                client.setPassword(newPassword);
            }

            userMapper.update(client);
            System.out.println("Client information updated successfully.");

            System.out.println("Edit another client? (y/n):");
            String again = scanner.nextLine().trim();
            if (!again.equalsIgnoreCase("y")) {
                clientManagement();
                return;
            }

            // Refresh list
            clients = userMapper.findClients();
            if (clients.isEmpty()) {
                System.out.println("No more clients.");
                clientManagement();
                return;
            }
        }
    }

    private void deleteClient() {
        List<Client> clients = userMapper.findClients();

        if (clients.isEmpty()) {
            System.out.println("No clients found.");
            clientManagement();
            return;
        }

        while (true) {
            System.out.println("\nRegistered Clients:");
            for (int i = 0; i < clients.size(); i++) {
                Client c = clients.get(i);
                System.out.printf("%d. %s (%s)%n", i + 1, c.getEmail(), c.getAffiliation());
            }
            System.out.println("Enter the number of the client to delete, or 'q' to go back:");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                clientManagement();
                return;
            }

            int index;
            try {
                index = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'q' to go back.");
                continue;
            }

            if (index < 0 || index >= clients.size()) {
                System.out.println("Selection out of range. Try again.");
                continue;
            }

            Client client = clients.get(index);
            System.out.printf("Deleting client: %s%n", client.getEmail());

            System.out.println("Are you sure you want to delete this client? (y/n):");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("y")) {
                userMapper.delete(client);
                System.out.println("Client deleted successfully.");
            } else {
                System.out.println("Client not deleted.");
            }

            System.out.println("Delete another client? (y/n):");
            String again = scanner.nextLine().trim();
            if (!again.equalsIgnoreCase("y")) {
                clientManagement();
                return;
            }

            // Refresh list
            clients = userMapper.findClients();
            if (clients.isEmpty()) {
                System.out.println("No more clients.");
                clientManagement();
                return;
            }
        }
    }

    private void expertManagement() {
        System.out.println("-------------------------");
        System.out.println("Expert Management:");
        System.out.println("1. Add Expert");
        System.out.println("2. Edit Expert");
        System.out.println("3. Delete Expert");
        System.out.println("4. Manage Expert Availability");
        System.out.println("5. View all experts");
        System.out.println("6. View Service Requests");
        System.out.println("7. Go back");
        System.out.println("-------------------------");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();
        switch(choice){
            case "1":
                registerExpert();
                break;
            case "2":
                editExpert();
                break;
            case "3":
                deleteExpert();
                break;
            case "4":
                System.out.println("Select an expert:");
                List<Expert> experts = userMapper.findExperts();
                if (experts.isEmpty()) {
                    System.out.println("No experts found.");
                    expertManagement();
                    return;
                }
                for (int i = 0; i < experts.size(); i++) {
                    Expert e = experts.get(i);
                    System.out.printf("%d. %s (%s)%n", i + 1, e.getEmail(), e.getAreaOfExpertise());
                }
                System.out.println("Enter the number of the expert to manage availability, or 'q' to go back:");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("q")) {
                    expertManagement();
                    return;
                }
                int index;
                try {
                    index = Integer.parseInt(input) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number or 'q' to go back.");
                    expertManagement();
                    return;
                }
                if (index < 0 || index >= experts.size()) {
                    System.out.println("Selection out of range. Try again.");
                    expertManagement();
                    return;
                }
                Expert selectedExpert = experts.get(index);
                System.out.printf("Managing availability for expert: %s%n", selectedExpert.getEmail());
                manageExpertAvailability(selectedExpert);
                break;
            case "5":
                viewAllExperts();
                break;
            case "6":
                viewServiceRequests();
                break;
            case "7":
                userManagement();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                expertManagement();
        }
    }

    private void viewServiceRequests() {
        System.out.println("-------------------------");
        System.out.println("Service Requests:");
        List<ServiceRequest> serviceRequests = serviceRequestMapper.findAll();
        if (serviceRequests.isEmpty()) {
            System.out.println("No service requests found.");
            expertManagement();
            return;
        }
        for (int i = 0; i < serviceRequests.size(); i++) {
            ServiceRequest sr = serviceRequests.get(i);
            System.out.printf(sr.toString() + "\n");
        }
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        expertManagement();
    }

    private void manageExpertAvailability(Expert expert) {
        System.out.println("----------------------------");
        System.out.println("Manage Expert Availability:");
        System.out.println("1. View Availability");
        System.out.println("2. Add Availability");
        System.out.println("3. Edit Availability");
        System.out.println("4. Delete Availability");
        System.out.println("5. Go back");
        System.out.println("----------------------------");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine().trim();
        switch(choice){
            case "1":
                viewAvailability(expert);
                break;
            case "2":
                addAvailability(expert);
                break;
            case "3":
                editAvailability(expert);
            case "4":
                deleteAvailability(expert);
                break;
            case "5":
                if (currentUser instanceof Administrator) {
                    adminMenu();
                } else {
                    expertMenu();
                }
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                manageExpertAvailability(expert);
        }
    }

    private void deleteAvailability(Expert expert) {
        System.out.println("----------------------------");
        System.out.println("Delete Availability:");
        List<TimeSlot> timeSlots = expert.getAvailability().getTimeSlots();
        if (timeSlots.isEmpty()) {
            System.out.println("No availability found for this expert.");
            manageExpertAvailability(expert);
            return;
        }
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot ts = timeSlots.get(i);
            System.out.printf("%d. Start: %s, End: %s%n", i + 1, ts.getStartTime(), ts.getEndTime());
        }
        System.out.println("Enter the number of the availability to delete, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            manageExpertAvailability(expert);
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            manageExpertAvailability(expert);
            return;
        }
        if (index < 0 || index >= timeSlots.size()) {
            System.out.println("Selection out of range. Try again.");
            manageExpertAvailability(expert);
            return;
        }
        TimeSlot selectedTimeSlot = timeSlots.get(index);
        expert.getAvailability().removeTimeSlot(selectedTimeSlot);
        userMapper.update(expert);
        System.out.println("Availability deleted successfully.");
        manageExpertAvailability(expert);
    }

    private void editAvailability(Expert expert) {
        System.out.println("----------------------------");
        System.out.println("Edit Availability:");
        List<TimeSlot> timeSlots = expert.getAvailability().getTimeSlots();
        if (timeSlots.isEmpty()) {
            System.out.println("No availability found for this expert.");
            manageExpertAvailability(expert);
            return;
        }
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot ts = timeSlots.get(i);
            System.out.printf("%d. Start: %s, End: %s%n", i + 1, ts.getStartTime(), ts.getEndTime());
        }
        System.out.println("Enter the number of the availability to edit, or 'q' to go back:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            manageExpertAvailability(expert);
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or 'q' to go back.");
            manageExpertAvailability(expert);
            return;
        }
        if (index < 0 || index >= timeSlots.size()) {
            System.out.println("Selection out of range. Try again.");
            manageExpertAvailability(expert);
            return;
        }
        TimeSlot selectedTimeSlot = timeSlots.get(index);
        System.out.printf("Editing availability: Start: %s, End: %s%n", selectedTimeSlot.getStartTime(), selectedTimeSlot.getEndTime());
        System.out.println("Enter new start time (YYYY-MM-DD HH:MM):");
        String startTimeInput = scanner.nextLine().trim();
        System.out.println("Enter new end time (YYYY-MM-DD HH:MM):");
        String endTimeInput = scanner.nextLine().trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime;
        LocalDateTime endTime;
        try {
            startTime = LocalDateTime.parse(startTimeInput, formatter);
            endTime = LocalDateTime.parse(endTimeInput, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date/time format. Please use 'YYYY-MM-DD HH:MM'.");
            manageExpertAvailability(expert);
            return;
        }
        selectedTimeSlot.setStartTime(startTime);
        selectedTimeSlot.setEndTime(endTime);
        userMapper.update(expert);
        System.out.println("Availability updated successfully.");
        manageExpertAvailability(expert);
    }

    private void addAvailability(Expert expert) {
        System.out.println("----------------------------");
        System.out.println("Add Availability:");
        System.out.println("Enter start time (YYYY-MM-DD HH:MM):");
        String startTimeInput = scanner.nextLine().trim();
        System.out.println("Enter end time (YYYY-MM-DD HH:MM):");
        String endTimeInput = scanner.nextLine().trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime;
        LocalDateTime endTime;

        try {
            startTime = LocalDateTime.parse(startTimeInput, formatter);
            endTime = LocalDateTime.parse(endTimeInput, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date/time format. Please use 'YYYY-MM-DD HH:MM'.");
            manageExpertAvailability(expert);
            return;
        }

        TimeSlot timeSlot = new TimeSlot(startTime, endTime);
        timeSlotMapper.create(timeSlot);
        expert.getAvailability().addTimeSlot(timeSlot);
        timeSlot.setAvailability(expert.getAvailability());
        timeSlotMapper.update(timeSlot);
        userMapper.update(expert);
        System.out.println("Availability added successfully.");
        manageExpertAvailability(expert);
    }

    private void viewAvailability(Expert expert) {
        System.out.println("----------------------------");
        System.out.println("View Availability:");
        List<TimeSlot> timeSlots = expert.getAvailability().getTimeSlots();
        if (timeSlots.isEmpty()) {
            System.out.println("No availability found for this expert.");
            manageExpertAvailability(expert);
            return;
        }
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot ts = timeSlots.get(i);
            System.out.printf("%d. Start: %s, End: %s%n", i + 1, ts.getStartTime(), ts.getEndTime());
        }
        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        manageExpertAvailability(expert);
    }

    private void viewAllExperts() {
        List<Expert> experts = userMapper.findExperts();

        if (experts.isEmpty()) {
            System.out.println("No experts found.");
            expertManagement();
            return;
        }

        System.out.println("\nRegistered Experts:");
        for (int i = 0; i < experts.size(); i++) {
            Expert e = experts.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, e.getEmail(), e.getAreaOfExpertise());
        }

        System.out.println("Press Enter to go back.");
        scanner.nextLine();
        expertManagement();
    }

    private void deleteExpert() {
        List<Expert> experts = userMapper.findExperts();

        if (experts.isEmpty()) {
            System.out.println("No experts found.");
            expertManagement();
            return;
        }

        while (true) {
            System.out.println("\nRegistered Experts:");
            for (int i = 0; i < experts.size(); i++) {
                Expert e = experts.get(i);
                System.out.printf("%d. %s %n", i + 1, e.getEmail());
            }
            System.out.println("Enter the number of the expert to delete, or 'q' to go back:");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                expertManagement();
                return;
            }

            int index;
            try {
                index = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'q' to go back.");
                continue;
            }

            if (index < 0 || index >= experts.size()) {
                System.out.println("Selection out of range. Try again.");
                continue;
            }

            Expert expert = experts.get(index);
            System.out.printf("Deleting expert: %s%n", expert.getEmail());

            System.out.println("Are you sure you want to delete this expert? (y/n):");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("y")) {
                userMapper.delete(expert);
                System.out.println("Expert deleted successfully.");
            } else {
                System.out.println("Expert not deleted.");
            }

            System.out.println("Delete another expert? (y/n):");
            String again = scanner.nextLine().trim();
            if (!again.equalsIgnoreCase("y")) {
                expertManagement();
                return;
            }

            // Refresh list
            experts = userMapper.findExperts();
            if (experts.isEmpty()) {
                System.out.println("No more experts.");
                expertManagement();
                return;
            }
        }
    }

    private void editExpert() {
        List<Expert> experts = userMapper.findExperts();

        if (experts.isEmpty()) {
            System.out.println("No experts found.");
            expertManagement();
            return;
        }

        while (true) {
            System.out.println("\nRegistered Experts:");
            for (int i = 0; i < experts.size(); i++) {
                Expert e = experts.get(i);
                System.out.printf("%d. %s %n", i + 1, e.getEmail());
            }
            System.out.println("Enter the number of the expert to edit, or 'q' to go back:");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                expertManagement();
                return;
            }

            int index;
            try {
                index = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'q' to go back.");
                continue;
            }

            if (index < 0 || index >= experts.size()) {
                System.out.println("Selection out of range. Try again.");
                continue;
            }

            Expert expert = experts.get(index);
            System.out.printf("Editing expert: %s%n", expert.getEmail());

            // Email
            System.out.printf("Current email: %s. Enter new email (or press Enter to keep):%n", expert.getEmail());
            String newEmail = scanner.nextLine().trim();
            if (!newEmail.isEmpty() && !newEmail.equals(expert.getEmail())) {
                if (userMapper.findByEmail(newEmail) != null) {
                    System.out.println("Email already in use. Email not changed.");
                } else {
                    expert.setEmail(newEmail);
                }
            }

            // Password
            System.out.println("Enter new password (or press Enter to keep current one):");
            String newPassword = scanner.nextLine().trim();
            if (!newPassword.isEmpty()) {
                expert.setPassword(newPassword);
            }

            //Area of expertise
            System.out.printf("Current area of expertise: %s. Enter new area of expertise (or press Enter to keep):%n", expert.getAreaOfExpertise());
            String newAreaOfExpertise = scanner.nextLine().trim();
            if (!newAreaOfExpertise.isEmpty()) {
                expert.setAreaOfExpertise(newAreaOfExpertise);
            }

            // License number
            System.out.printf("Current license number: %s. Enter new license number (or press Enter to keep):%n", expert.getLicenseNumber());
            String newLicenseNumber = scanner.nextLine().trim();
            if (!newLicenseNumber.isEmpty()) {
                expert.setLicenseNumber(newLicenseNumber);
            }

            userMapper.update(expert);
            System.out.println("Expert information updated successfully.");
        }
    }


}
