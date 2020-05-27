package model;

public abstract class Event {
    private int eventId;
    private String date;
    private String startTime;
    private String duration;
    private String imageSrc;
    private int price;


    public Event(int eventId, String date, String startTime, String duration, String imageSrc, int price) {
        this.eventId = eventId;
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
        this.imageSrc = imageSrc;
        this.price = price;
    }

    public Event() {}


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
