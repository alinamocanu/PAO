package repositories.concerts;

import model.Concert;
import exceptions.InexistentUserFileException;
import model.Location;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileConcertRepository implements ConcertRepository {
    private final String file = "CONCERTS";

    private FileConcertRepository() {

    }

    @Override
    public void addConcert(Concert concert) {
        try (PrintStream out = new PrintStream(file)) {
            out.println(concert.getEventId() + "," + concert.getConcertName() + "," + concert.getPrice() + "," + concert.getDate()
                    + "," + concert.getDuration() + "," + concert.getStartTime() + "," + concert.getArtist());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<List<Concert>> getConcerts() {
        Path path = Paths.get(file);
        Concert concert;
        List<Concert> concerts = new ArrayList<>();

        try {
            if (!Files.exists(path)) {
                throw new InexistentUserFileException();
            }
            var list = Files.readAllLines(path);
            for (String c : list) {
                String[] attr = c.split(",");

                concert = new Concert();
                concert.setEventId(Integer.parseInt(attr[0]));
                concert.setConcertName(attr[6]);
                concert.setPrice(Integer.parseInt(attr[7]));
                concert.setDate(attr[1]);
                concert.setDuration(attr[3]);
                concert.setStartTime(attr[2]);
                concert.setArtist(attr[5]);
                concert.setImageSrc(attr[4]);

                concerts.add(concert);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.of(concerts);
    }

    @Override
    public Optional<Concert> findConcertByArtist(String artist) {
        Path path = Paths.get(file);
        Concert concert = null;

        try {
            if (!Files.exists(path)) {
                throw new InexistentUserFileException();
            }
            var list = Files.readAllLines(path);
            for (String m : list) {
                String[] attr = m.split(",");
                if (attr[5].contains(artist)) {
                    concert = new Concert();
                    concert.setEventId(Integer.parseInt(attr[0]));
                    concert.setConcertName(attr[6]);
                    concert.setPrice(Integer.parseInt(attr[7]));
                    concert.setDate(attr[1]);
                    concert.setDuration(attr[3]);
                    concert.setStartTime(attr[2]);
                    concert.setArtist(attr[5]);

                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(concert);
    }

    @Override
    public Optional<Location> getLocation(String concertId) {
        return Optional.empty();
    }

    @Override
    public void updatePrice() {

    }

    public static FileConcertRepository getInstance() {
        return FileConcertRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static FileConcertRepository INSTANCE = new FileConcertRepository();
    }
}
