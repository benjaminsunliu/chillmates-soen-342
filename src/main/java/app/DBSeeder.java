package app;

import app.auction.*;
import app.mappers.*;
import app.services.*;
import app.users.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class DBSeeder {
    public static void main(String[] args) {
        file();
        seed();
    }

    public static void file(){
        String filename = "soen342.mv.db";
        java.io.File file = new java.io.File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void seed() {
        UserMapper userMapper = new UserMapper();
        AvailabilityMapper availabilityMapper = new AvailabilityMapper();
        TimeSlotMapper timeSlotMapper = new TimeSlotMapper();
        ServiceRequestMapper serviceRequestMapper = new ServiceRequestMapper();
        ObjectMapper objectMapper = new ObjectMapper();
        AuctionMapper auctionMapper = new AuctionMapper();
        AuctionHouseMapper auctionHouseMapper = new AuctionHouseMapper();
        InstitutionMapper institutionMapper = new InstitutionMapper();
        ViewingMapper viewingMapper = new ViewingMapper();

        // Create institutions
        Institution institution1 = new Institution("Institution 1");
        institutionMapper.create(institution1);

        // Create users
        User admin = new Administrator("admin", "admin");
        Expert expert = new Expert("expert@example.com", "ExpertPass", "Expert Affiliation", "Expert Intent");
        Client client = new Client("client@example.com", "ClientPass", "Client Affiliation", "Client Intent", false);
        Expert expert2 = new Expert("expert2@example.com", "Expert2Pass", "Second Affiliation", "Second Intent");
        Client client2 = new Client("client2@example.com", "Client2Pass", "Second Client Affiliation", "Second Client Intent", true);
        userMapper.create(admin);
        userMapper.create(expert);
        userMapper.create(client);
        userMapper.create(expert2);
        userMapper.create(client2);

        Availability availability = new Availability(expert);
        availabilityMapper.create(availability);
        Availability availability2 = new Availability(expert2);
        availabilityMapper.create(availability2);

        TimeSlot ts1 = new TimeSlot(LocalDateTime.of(2025, 4, 1, 14, 0),
                LocalDateTime.of(2025, 4, 1, 16, 0));
        TimeSlot ts2 = new TimeSlot(LocalDateTime.of(2025, 4, 2, 10, 0),
                LocalDateTime.of(2025, 4, 2, 12, 0));
        TimeSlot ts3 = new TimeSlot(LocalDateTime.of(2025, 4, 3, 10, 0),
                LocalDateTime.of(2025, 4, 3, 12, 0));
        TimeSlot ts4 = new TimeSlot(LocalDateTime.of(2025, 4, 4, 9, 0), LocalDateTime.of(2025, 4, 4, 11, 0));
        TimeSlot ts5 = new TimeSlot(LocalDateTime.of(2025, 4, 5, 13, 0), LocalDateTime.of(2025, 4, 5, 15, 0));
        timeSlotMapper.create(ts1);
        timeSlotMapper.create(ts2);
        timeSlotMapper.create(ts4);
        timeSlotMapper.create(ts5);

        ts1.setAvailability(availability);
        ts2.setAvailability(availability);
        ts4.setAvailability(availability2);
        ts5.setAvailability(availability2);
        timeSlotMapper.update(ts1);
        timeSlotMapper.update(ts2);
        timeSlotMapper.update(ts4);
        timeSlotMapper.update(ts5);

        availability.addTimeSlot(ts1);
        availability.addTimeSlot(ts2);
        availability2.addTimeSlot(ts4);
        availability2.addTimeSlot(ts5);

        expert.setAvailability(availability);
        userMapper.update(expert);
        expert2.setAvailability(availability2);
        userMapper.update(expert2);

        ServiceRequest serviceRequest = new ServiceRequest("Consultation", expert, client, ts1);
        serviceRequestMapper.create(serviceRequest);
        ServiceRequest serviceRequest2 = new ServiceRequest("Appraisal", client, ts2);
        serviceRequestMapper.create(serviceRequest2);
        ServiceRequest serviceRequest3 = new ServiceRequest("Verification", expert2, client2, ts4);
        serviceRequestMapper.create(serviceRequest3);
        ServiceRequest serviceRequest4 = new ServiceRequest("Inspection", client2, ts5);
        serviceRequestMapper.create(serviceRequest4);

        ObjectOfInterest objectOfInterest = new ObjectOfInterest("Object Name", "Object Description", "test", institution1);
        objectMapper.create(objectOfInterest);
        institution1.addObject(objectOfInterest);
        institutionMapper.update(institution1);

        Institution institution2 = new Institution("Institution 2");
        institutionMapper.create(institution2);
        ObjectOfInterest obj2 = new ObjectOfInterest("Historic Coin", "A coin from the 1700s", "category", institution2);
        ObjectOfInterest obj3 = new ObjectOfInterest("Ancient Manuscript", "Old text", "category", institution2);
        objectMapper.create(obj2);
        objectMapper.create(obj3);
        institution2.addObject(obj2);
        institution2.addObject(obj3);
        institutionMapper.update(institution2);

        AuctionHouse house1 = new AuctionHouse("Heritage Auctions", "New York");
        AuctionHouse house2 = new AuctionHouse("Antique Emporium", "London");
        auctionHouseMapper.create(house1);
        auctionHouseMapper.create(house2);

        Auction auction1 = new Auction("Vintage Clock", house1,false);
        Auction auction2 = new Auction("Rare Painting", house1, true);
        Auction auction3 = new Auction("Antique Vase", house2,true);
        Auction auction4 = new Auction("Classic Sculpture", house2, false);
        Auction auction5 = new Auction("Retro Camera", house1, true);
        Auction auction6 = new Auction("Medieval Sword", house2, false);
        auctionMapper.create(auction1);
        auctionMapper.create(auction2);
        auctionMapper.create(auction3);
        auctionMapper.create(auction4);
        auctionMapper.create(auction5);
        auctionMapper.create(auction6);
        house1.addAuction(auction1);
        house1.addAuction(auction2);
        house2.addAuction(auction3);
        house2.addAuction(auction4);
        house1.addAuction(auction5);
        house2.addAuction(auction6);

        Viewing viewing1 = new Viewing(LocalDateTime.of(2025, 4, 1, 14, 0),
                LocalDateTime.of(2025, 4, 1, 16, 0), auction1);
        viewingMapper.create(viewing1);
        Viewing viewing2 = new Viewing(LocalDateTime.of(2025, 4, 2, 10, 0),
                LocalDateTime.of(2025, 4, 2, 12, 0), auction2);
        viewingMapper.create(viewing2);
        Viewing viewing3 = new Viewing(LocalDateTime.of(2025, 4, 6, 11, 0),
                LocalDateTime.of(2025, 4, 6, 13, 0), auction5);
        Viewing viewing4 = new Viewing(LocalDateTime.of(2025, 4, 7, 15, 0),
                LocalDateTime.of(2025, 4, 7, 17, 0), auction6);
        viewingMapper.create(viewing3);
        viewingMapper.create(viewing4);

        auction1.setViewing(viewing1);
        auction2.setViewing(viewing2);
        auction5.setViewing(viewing3);
        auction6.setViewing(viewing4);
        auctionMapper.update(auction1);
        auctionMapper.update(auction2);
        auctionMapper.update(auction5);
        auctionMapper.update(auction6);

        viewingMapper.close();
        auctionHouseMapper.close();
        auctionMapper.close();
        userMapper.close();
        availabilityMapper.close();
        timeSlotMapper.close();
        serviceRequestMapper.close();
        objectMapper.close();
        institutionMapper.close();
    }
}