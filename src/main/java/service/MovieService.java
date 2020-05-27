package service;

import model.Movie;
import model.Theatre;
import repositories.movies.DBMovieRepository;
import repositories.DBTheatreRepository;
import repositories.movies.MovieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieService {
    private MovieRepository movieRepository;
    private java.util.List<Theatre> backupTheatres = new ArrayList<>();

    private MovieService() {

        movieRepository = MovieRepository.build(MovieRepository.Type.DB);

        backupTheatres.add(new Theatre(0, "Union", "Bucuresti", 450, "1,2,3,4,5"));
        backupTheatres.add(new Theatre(0, "Pro", "Bucuresti", 550, "1,2,3,4,5"));
        backupTheatres.add(new Theatre(0, "Cine Globe", "Bucuresti", 650, "1,2,3,4,5"));
    }

    public List<Movie> getMovies() {

        Optional<java.util.List<Movie>> movies = movieRepository.getMovies();
        return movies.get();
    }

    public void  updateNoOfSeats(int seatNo, String locationId) {
        DBTheatreRepository t = DBTheatreRepository.getInstance();
        t.updateNoOfSeats(String.valueOf(seatNo), locationId);
    }

    public void updatePrice() {
        DBMovieRepository movieRepository = DBMovieRepository.getInstance();
        movieRepository.updatePrice();
    }

    public void replaceTheatre(Theatre theatre) {

        DBTheatreRepository theatreRepository = DBTheatreRepository.getInstance();
        theatreRepository.deleteTheatre(theatre.getBuildingName());
        theatreRepository.addTheatre(backupTheatres.get(0));
        backupTheatres.remove(0);
    }

    public List<Theatre> getTheatres() {
        DBTheatreRepository t = DBTheatreRepository.getInstance();

        return t.getTheatres();
    }

    public Optional<Movie> findMovieByTitle(String title) {

        return movieRepository.findMovieByTitle(title);
    }

    public static MovieService getInstance() {
        return MovieService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static MovieService INSTANCE = new MovieService();
    }
}
