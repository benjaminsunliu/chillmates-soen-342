package app;
import app.auction.Auction;
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

    public Console(){
        this.scanner = new Scanner(System.in);
        this.userMapper = new UserMapper();
        this.availabilitymapper = new AvailabilityMapper();
        this.timeSlotMapper = new TimeSlotMapper();
        this.serviceRequestMapper = new ServiceRequestMapper();
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
                //objectOfInterestManagement();
                break;
            case "3":
                //auctionHouseManagement();
                break;
            case "4":
                //auctionManagement();
                break;
            case "5":
                //viewingManagement();
                break;
            case "6":
                //serviceRequestManagement();
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
                //availabilityManagement();
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
                expertManagement();
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
