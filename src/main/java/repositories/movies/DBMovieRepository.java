package repositories.movies;

import model.Movie;
import managers.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBMovieRepository implements MovieRepository {

    private DBMovieRepository() {

    }

    @Override
    public Optional<List<Movie>> getMovies() {
        String sql = "SELECT * FROM movies";
        List<Movie> Movies = new ArrayList<>();

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
                String name = set.getString("title");
                String price = set.getString("price");

                Movie movie =  new Movie(id, date, startTime, duration, imageSrc, name, Integer.parseInt(price));
                Movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(Movies);
    }

    @Override
    public void addMovie(Movie movie) {
        String sql = "INSERT INTO movies VALUES (NULL, ?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, movie.getDate());
            statement.setString(2, movie.getStartTime());
            statement.setString(3, movie.getDuration());
            statement.setString(4, movie.getImageSrc());
            statement.setString(5, movie.getMovieName());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Movie> findMovieByTitle(String title) {
        String sql = "SELECT * FROM movies WHERE title = ?";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, title);

            ResultSet set = statement.executeQuery();

            if (set.next()) {
                int id = set.getInt("id");
                String date = set.getString("date");
                String startTime = set.getString("start_time");
                String duration = set.getString("duration");
                String imageSrc = set.getString("image_source");
                String movieName = set.getString("title");
                String price = set.getString("price");

                return Optional.of(new Movie(id, date, startTime, duration, imageSrc, movieName, Integer.parseInt(price)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Movie> findMovieById(String movieId) {
        String sql = "SELECT * FROM movies WHERE id = ?";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, movieId);

            ResultSet set = statement.executeQuery();

            if (set.next()) {
                int id = set.getInt("id");
                String date = set.getString("date");
                String startTime = set.getString("start_time");
                String duration = set.getString("duration");
                String imageSrc = set.getString("image_source");
                String name = set.getString("title");
                String price = set.getString("price");

                return Optional.of(new Movie(id, date, startTime, duration, imageSrc, name, Integer.parseInt(price)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void updatePrice() {
        String sql = "UPDATE movies SET price = ?";

        try(  Connection con = DBConnectionManager.getInstance().createConnection();
              PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, "20");

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBMovieRepository getInstance() {
        return DBMovieRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static DBMovieRepository INSTANCE = new DBMovieRepository();
    }
}
