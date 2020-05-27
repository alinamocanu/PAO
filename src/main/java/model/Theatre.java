package model;

import repositories.movies.DBMovieRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Theatre extends Location {
    List<Optional<Movie>> availableMovies;

    public Theatre() {
    }

    public Theatre(int locationId, String buildingName, String city, int capacity, String availableMovies) {
        super(locationId, buildingName, city, capacity);

        List<String> movies = Arrays.asList(availableMovies.split(","));
        DBMovieRepository m = DBMovieRepository.getInstance();

        this.availableMovies = movies.stream()
                .map(m::findMovieById)
                .collect(Collectors.toList());
    }

    public List<Optional<Movie>> getAvailableMovies() {
        return availableMovies;
    }

   // public void setAvailableMovies(List<Movie> availableMovies) {
   //     this.availableMovies = availableMovies;
   // }
}
