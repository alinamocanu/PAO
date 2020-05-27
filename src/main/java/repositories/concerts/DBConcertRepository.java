package repositories.concerts;

import model.Concert;
import model.Location;
import managers.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBConcertRepository implements ConcertRepository {

    private DBConcertRepository() {

    }

    @Override
    public Optional<List<Concert>> getConcerts() {
        String sql = "SELECT * FROM concerts";
        List<Concert> Concerts = new ArrayList<>();

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
                ) {
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                int id = set.getInt("id");
                String date = set.getString("date");
                String startTime = set.getString("start_time");
                String duration = set.getString("duration");
                String imageSrc = set.getString("image_source");
                String artistName = set.getString("artist");
                String concertName = set.getString("concert_name");
                String startingPrice = set.getString("starting_price");

                Concert concert =  new Concert(id, date, startTime, duration, imageSrc, artistName, concertName, Integer.parseInt(startingPrice));
                Concerts.add(concert);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(Concerts);
    }

    public void addConcert(Concert concert) {
        String sql = "INSERT INTO movies VALUES (NULL, ?, ?, ?,?,?,?)";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, concert.getDate());
            statement.setString(2, concert.getStartTime());
            statement.setString(3, concert.getDuration());
            statement.setString(4, concert.getImageSrc());
            statement.setString(5, concert.getArtist());
            statement.setString(6, concert.getConcertName());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Concert> findConcertByArtist(String artist) {
        String sql = "SELECT * FROM concerts WHERE artist = ?";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, artist);

            ResultSet set = statement.executeQuery();

            if (set.next()) {
                int id = set.getInt("id");
                String date = set.getString("date");
                String startTime = set.getString("start_time");
                String duration = set.getString("duration");
                String imageSrc = set.getString("image_source");
                String artistName = set.getString("artist");
                String concertName = set.getString("concert_name");
                String startingPrice = set.getString("starting_price");

                return Optional.of(new Concert(id, date, startTime, duration, imageSrc, artistName, concertName, Integer.parseInt(startingPrice)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Location> getLocation(String concertId) {
        String sql = "SELECT * FROM concerts JOIN concert_locations ON concert_locations.id = concerts.locationId WHERE concerts.id = ?" ;

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
             ) {
            statement.setString(1, concertId);

            ResultSet set = statement.executeQuery();

            if (set.next()) {
                String locationId = set.getString("concert_locations.id");
                String concertLocation = set.getString("location_name");
                String city = set.getString("city");
                String capacity = set.getString("capacity");

                return Optional.of(new Location(Integer.parseInt(locationId), concertLocation, city, Integer.parseInt(capacity)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void updatePrice() {
        String sql = "UPDATE concerts SET starting_price = ?";

        try(  Connection con = DBConnectionManager.getInstance().createConnection();
              PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, "100");

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBConcertRepository getInstance() {
        return DBConcertRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static DBConcertRepository INSTANCE = new DBConcertRepository();
    }
}
