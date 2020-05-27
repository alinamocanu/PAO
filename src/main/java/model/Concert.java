package model;

public class Concert extends Event {
   private  String artist;
   private String concertName;

   public Concert(int eventId, String date, String startTime, String duration, String imageSrc, String artist, String concertName, int startingPrice) {
      super(eventId, date, startTime, duration, imageSrc, startingPrice);
      this.artist = artist;
      this.concertName = concertName;
   }

   public Concert() {}

   public void setConcertName(String concertName)
   {
      this.concertName = concertName;
   }

   public String getConcertName() {
      return concertName;
   }

   public String getArtist() {
      return artist;
   }

   public void setArtist(String artist) {
      this.artist = artist;
   }
}
