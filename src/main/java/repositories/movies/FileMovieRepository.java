package repositories.movies;

import model.Movie;
import exceptions.InexistentUserFileException;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileMovieRepository implements MovieRepository {
    private final String file = "MOVIES";

    private FileMovieRepository() {

    }

    @Override
    public Optional<List<Movie>> getMovies() {
        Path path = Paths.get(file);
        Movie movie;
        List<Movie> movies = new ArrayList<>();

        try {
            if (!Files.exists(path)) {
                throw new InexistentUserFileException();
            }
            var list = Files.readAllLines(path);
            for (String m : list) {
                String[] attr = m.split(",");

                movie = new Movie();
                movie.setEventId(Integer.parseInt(attr[0]));
                movie.setMovieName(attr[5]);
                movie.setPrice(Integer.parseInt(attr[6]));
                movie.setDate(attr[1]);
                movie.setDuration(attr[3]);
                movie.setStartTime(attr[2]);
                movie.setImageSrc(attr[4]);

                movies.add(movie);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.of(movies);
    }

    @Override
    public void addMovie(Movie movie) {
        try (PrintStream out = new PrintStream(file)) {
            out.println(movie.getEventId() + "," + movie.getMovieName() + "," + movie.getPrice() + "," + movie.getDate()
            + "," + movie.getDuration() + "," + movie.getStartTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Movie> findMovieByTitle(String title) {
        Path path = Paths.get(file);
        Movie movie = null;

        try {
            if (!Files.exists(path)) {
                throw new InexistentUserFileException();
            }
            var list = Files.readAllLines(path);
            for (String m : list) {
                String[] attr = m.split(",");
                if (attr[5].contains(title)) {
                    movie = new Movie();
                    movie.setEventId(Integer.parseInt(attr[0]));
                    movie.setMovieName(attr[5]);
                    movie.setPrice(Integer.parseInt(attr[6]));
                    movie.setDate(attr[1]);
                    movie.setDuration(attr[3]);
                    movie.setStartTime(attr[2]);

                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(movie);
    }

    @Override
    public Optional<Movie> findMovieById(String movieId) {
        Path path = Paths.get(file);
        Movie movie = null;

        try {
            if (!Files.exists(path)) {
                throw new InexistentUserFileException();
            }
            var list = Files.readAllLines(path);
            for (String m : list) {
                String[] attr = m.split(",");
                if (attr[0].equals(movieId)) {
                    movie = new Movie();
                    movie.setEventId(Integer.parseInt(attr[0]));
                    movie.setMovieName(attr[1]);
                    movie.setPrice(Integer.parseInt(attr[2]));
                    movie.setDate(attr[3]);
                    movie.setDuration(attr[4]);
                    movie.setStartTime(attr[5]);

                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(movie);
    }

    @Override
    public void updatePrice() {

    }

    public static FileMovieRepository getInstance() {
        return FileMovieRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static FileMovieRepository INSTANCE = new FileMovieRepository();
    }
}
