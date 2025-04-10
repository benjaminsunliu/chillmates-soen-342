package app;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.users.*;
import app.catalogs.*;
import app.auction.*;
import app.services.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Institution {
    private String name;

    @OneToMany
    private List<User> users;

    @OneToMany
    private List<ObjectOfInterest> ownedObjects;

    @Id
    @GeneratedValue
    private Long id;

    public Institution(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.ownedObjects = new ArrayList<>();
    }

    public Institution() {

    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }
    public String getName() {
        return this.name;
    }
    public List<User> getUsers() {
        return this.users;
    }
    public List<ObjectOfInterest> getOwnedObjects() {
        return this.ownedObjects;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
    public void addObject(ObjectOfInterest object) {
        this.ownedObjects.add(object);
    }

    public List<ObjectOfInterest> getObjects() {
        return this.ownedObjects;
    }
    public void setObjects(List<ObjectOfInterest> objects) {
        this.ownedObjects = objects;
    }
    public ObjectOfInterest removeObject(ObjectOfInterest object) {
        if (this.ownedObjects.remove(object)) {
            return object;
        }
        return null;
    }


    /*
    private String name;

    private UserCatalog userCatalog;
    private ObjectCatalog objectCatalog;
    private AuctionCatalog auctionCatalog;
    private AuctionHouseCatalog auctionHouseCatalog;
    private ViewingCatalog viewingCatalog;

    private Scanner scanner;

    public Institution(String name) { 
        this.name = name;
        this.userCatalog = new UserCatalog();
        this.objectCatalog = new ObjectCatalog();
        this.auctionCatalog = new AuctionCatalog();
        this.auctionHouseCatalog = new AuctionHouseCatalog();
        this.viewingCatalog = new ViewingCatalog();

        this.scanner = new Scanner(System.in);

        // Iteration 1 Hardcoded data
        userCatalog.addUser(new Administrator("admin@demo", "admin"));
        userCatalog.addUser(new Expert("expert@demo", "expert", "LIC-123", "Paintings, Calligraphy"));
        userCatalog.addUser(new Client("client@demo", "client", "Gallery.Inc", "Buying antiques", true));

        objectCatalog.addObject("Mona Lisa", "Painting by Leonardo Da Vinci", "Painting", true);
        objectCatalog.addObject("The Starry Night", "Painting by Vincent Van Gogh", "Painting", false);
        objectCatalog.addObject("The Persistence of Memory", "Painting by Salvador Dali", "Painting", true);
    }

    public static void main(String[] args) {
        Institution institution = new Institution("Art Advisory Institution");
        institution.run();
    }

    public void run(){
        while (true){
            System.out.println("\n--- Welcome to " + this.name + " ---");
            System.out.println("1. Login");
            System.out.println("2. Sign up as Client");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice){
                case "1":
                    User loggedInUser = login();
                    
                    if (loggedInUser != null){
                        System.out.println("Login Successfull! Role: " + loggedInUser.getRole());
                        if (loggedInUser instanceof Administrator){
                            adminMenu((Administrator) loggedInUser);
                        }
                        else if (loggedInUser instanceof Expert){
                            expertMenu((Expert) loggedInUser);
                        }
                        else if (loggedInUser instanceof Client){
                            clientMenu((Client) loggedInUser);
                        }
                        else{
                            System.out.println("Invalid user role");
                        }
                    }
                    else{
                        System.out.println("Login Failed. Please try again.");
                    }
                    break;
                case "2":
                    clientSignUp();
                    break;
                case "3":
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private User login(){
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        User found = userCatalog.findByEmail(email); 
        if (found != null && found.getPassword().equals(password)){
            if (found.getRole() == "Client" && ((Client) found).getStatus()){
                return found;
            }
            else if (found.getRole() == "Administrator" || found.getRole() == "Expert"){
                return found;
            }
            else{
                System.out.println("Account not approved yet. Please wait for approval.");
                return null;
            }
        }
        else{
            System.out.println("Invalid email or password.");
            return null;
        }
    }

    private void clientSignUp(){
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine().trim();
        System.out.print("Enter affiliation: ");
        String affiliation = scanner.nextLine().trim();
        System.out.print("Enter intent: ");
        String intent = scanner.nextLine().trim();
        Boolean status = false;

        Boolean dupeNotFound = !userCatalog.verifyDupe(email);

        if (dupeNotFound){
            User createdUser = userCatalog.createUser(email, password, affiliation, intent, status);
            System.out.println("Client account created successfully! Your " + createdUser.getRole() + " account is pending approval.");
        }
        else{
            System.out.println("Email already taken. Please try again.");
        }
    }

    private void adminMenu(Administrator admin){
        while (true){
            System.out.println("\n--- Administrator Menu ---");
            System.out.println("1. View all users");
            System.out.println("2. View all objects");
            System.out.println("3. Add Object of Interest");
            System.out.println("4. Add Expert");
            System.out.println("5. Approve Client");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch(choice){
                case "1":
                    System.out.println("\n--- All Users ---");
                    displayUsers();
                    break;
                case "2":
                    System.out.println("\n--- All Objects ---");
                    viewObjects();
                    break;
                case "3":
                    addObjectFlow();
                    break;
                case "4":
                    //addExpert();
                    break;
                case "5":
                    //approveClient();
                    break;
                case "6":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayUsers(){
        for (User user : userCatalog.getUsers()){
            System.out.println(user);
        }
    }
    private void viewObjects(){
        Object[] objects = objectCatalog.getAvailableObjects();
        displayObjects(objects);
    }

    private void displayObjects(Object[] objects){
        for (Object object : objects){
            System.out.println(object);
        }
    }

    private void addObjectFlow(){
        System.out.println("Enter object title: ");
        String title = scanner.nextLine().trim();
        System.out.println("Enter object description: ");
        String description = scanner.nextLine().trim();
        System.out.println("Enter object type: ");
        String type = scanner.nextLine().trim();
        System.out.println("Is object owned by institution? (1: Yes/2: No): ");
        Boolean isOwnedByInstitution = scanner.nextLine().trim().equals("1");

        Object newObject = objectCatalog.addObject(title, description, type, isOwnedByInstitution);
        System.out.println("Object added successfully: " + newObject.toString());
    }

    private void expertMenu(Expert expert){
        while(true){
            System.out.println("\n--- Expert Menu ---");
            System.out.println("1. View all objects");
            System.out.println("2. Add Availability");
            System.out.println("3. View My Availabilities");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch(choice){
                case "1":
                    viewObjects();
                    break;
                case "2":
                    //addAvailability(expert);
                    break;
                case "3":
                    //viewAvailabilities(expert);
                    break;
                case "4":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void clientMenu(Client client){
        while (true){
            System.out.println("\n--- Client Menu ---");
            System.out.println("1. View all objects");
            System.out.println("2. Search Objects");
            System.out.println("3. Search Auctions");
            System.out.println("4. Request Service");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch(choice){
                case "1":
                    viewObjects();
                    break;
                case "2":
                    //searchObjects();
                    break;
                case "3":
                    //searchAuctions();
                    break;
                case "4":
                    //requestService(client);
                    break;
                case "5":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }*/

    
}