package repositories;

import model.Movie;
import model.Theatre;
import managers.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBTheatreRepository {

    private DBTheatreRepository() {

    }

    public List<Theatre> getTheatres() {
        String sql = "SELECT * FROM theatres";
        List<Theatre> Theatres = new ArrayList<>();

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("theatre_name");
                String availableMovies = set.getString("available_movies");
                String city = set.getString("city");
                String capacity = set.getString("capacity");

                Theatre theatre =  new Theatre(id, name, city, Integer.parseInt(capacity), availableMovies);
                Theatres.add(theatre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Theatres;
    }

    public void updateNoOfSeats(String noOfSeatsLeft, String theatreId) {
        String sql = "UPDATE theatres SET capacity = ? WHERE id = ?";

        try(  Connection con = DBConnectionManager.getInstance().createConnection();
              PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, noOfSeatsLeft);
            statement.setString(2, theatreId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTheatre(String theatreName) {
        String sql = "DELETE FROM theatres WHERE theatre_name = ?;";
        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, theatreName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTheatre(Theatre theatre) {
        String sql = "INSERT INTO theatres VALUES (NULL, ?, ?, ?, ?)";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, theatre.getBuildingName());
            StringBuilder s = new StringBuilder();
            for(Optional<Movie> movie : theatre.getAvailableMovies())
                if (movie.isPresent()) {
                    s.append(",").append(movie.get().getEventId());
                    String availableMovies = s.substring(1);

                    statement.setString(2, availableMovies);
                    statement.setString(3, theatre.getCity());
                    statement.setString(4, String.valueOf(theatre.getCapacity()));

                    statement.executeUpdate();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static DBTheatreRepository getInstance() {
        return DBTheatreRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static DBTheatreRepository INSTANCE = new DBTheatreRepository ();
    }
}
