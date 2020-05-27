package repositories.movies;

import model.Movie;

import java.util.*;

class SortByPrice implements Comparator<Movie> {
    public int compare(Movie a, Movie b) {
        return a.getPrice() - b.getPrice();
    }
}

public class ArrayMovieRepository implements MovieRepository {

    private java.util.List<Movie> movies = new ArrayList<>();

    private ArrayMovieRepository() {

    }

    @Override
    public Optional<List<Movie>> getMovies() {
        movies.add(new Movie(1, "2020-05-10", "20:00", "2h 14 min", "a_star_is_born.jpg", "A Star Is Born", 35));
        movies.add(new Movie(2, "2020-07-22", "19:45", "1h 45 min", "edward_scissorshands.jpg", "Edward Scissorshands", 40));
        movies.add(new Movie(3, "2020-10-01", "19:30", "2h 22 min", "forrest_gump.jpg", "Forrest Gump", 60));
        movies.add(new Movie(4, "2020-08-10", "15:00", "1h 56 min", "the_incredibles.jpg", "The Incredibles", 35));
        movies.add(new Movie(5, "2020-12-07", "21:00", "1h 47 min", "the_truman_show.jpg", "The Truman Show", 35));

        movies.sort(new SortByPrice());
        return Optional.ofNullable(movies);
    }

    @Override
    public void addMovie(Movie movie) {
       movies.add(movie);
    }

    @Override
    public Optional<Movie> findMovieByTitle(String title) {
        for (Movie movie : movies) {
            if (movie.getMovieName().contains(title)) {
                System.out.println(movie.getMovieName());
                return Optional.of(movie);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Movie> findMovieById(String movieId) {
        for (Movie movie : movies) {
            if (String.valueOf(movie.getEventId()).equals(movieId)) {
                System.out.println(movie.getEventId() + " " + movie.getMovieName());
                return Optional.of(movie);
            }
        }
        return Optional.empty();
    }

    @Override
    public  void updatePrice() {

    }

    public static ArrayMovieRepository getInstance() {
        return  ArrayMovieRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static  ArrayMovieRepository INSTANCE = new  ArrayMovieRepository();
    }
}
