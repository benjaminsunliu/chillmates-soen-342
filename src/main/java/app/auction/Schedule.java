package app.auction;


public class Schedule {
    private String startTime;
    private String endTime;
    private AuctionHouse organizer;
    private Auction scheduledAuction;

    public Schedule(String startTime, String endTime, AuctionHouse organizer, Auction scheduledAuction) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.organizer = organizer;
        this.scheduledAuction = scheduledAuction;
    }

    public String getStartTime() {
        return this.startTime;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public AuctionHouse getAuctionHouse() {
        return this.organizer;
    }
    public Auction getAuction() {
        return this.scheduledAuction;
    }

    @Override
    public String toString() {
        return "Start time: " + this.startTime + "\nEnd time: " + this.endTime + "\nAuction house: " + this.organizer + "\nAuction: " + this.scheduledAuction;
    }
}