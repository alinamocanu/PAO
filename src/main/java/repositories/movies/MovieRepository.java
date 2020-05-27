package repositories.movies;

import model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    Optional<List<Movie>> getMovies();
    void addMovie(Movie movie);
    Optional<Movie> findMovieByTitle(String title);
    Optional<Movie> findMovieById(String movieId);
    void updatePrice();

    static MovieRepository build(Type type) {
        switch (type) {
            case DB: return DBMovieRepository.getInstance();
            case FILE: return FileMovieRepository.getInstance();
            case ARRAY: return ArrayMovieRepository.getInstance();
        }

        throw new RuntimeException("No such type");
    }

    enum Type {
        DB, FILE, ARRAY
    }
}
