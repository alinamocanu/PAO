package model;

public class Ticket {
    private int id;
    private int price;
    private String eventName;
    private String Location;
    private String seat;

    public Ticket(int id, int price, String eventName, String location, String seat) {
        this.id = id;
        this.price = price;
        this.eventName = eventName;
        Location = location;
        this.seat = seat;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getEventName() {
        return eventName;
    }

    public String getLocation() {
        return Location;
    }

    public String getSeat() {
        return seat;
    }
}
