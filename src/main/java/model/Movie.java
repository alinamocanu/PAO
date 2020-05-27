package model;

public class Movie extends Event {

    private String movieTitle;

    public Movie(int eventId, String date, String startTime, String duration, String imageSrc, String movieTitle, int price) {
        super(eventId, date, startTime, duration, imageSrc, price);
        this.movieTitle = movieTitle;
    }

    public Movie() {}

    public String getMovieName() {
        return movieTitle;
    }

    public void setMovieName(String movieName) {
        this.movieTitle = movieName;
    }
}
