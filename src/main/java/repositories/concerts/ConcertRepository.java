package repositories.concerts;

import model.Concert;
import model.Location;

import java.util.List;
import java.util.Optional;

public interface ConcertRepository {
    void addConcert(Concert concert);
    Optional<List<Concert>> getConcerts();
    Optional<Concert> findConcertByArtist(String artist);
    Optional<Location> getLocation(String concertId);
    void updatePrice();

    static ConcertRepository build(ConcertRepository.Type type) {
        switch (type) {
            case DB: return DBConcertRepository.getInstance();
            case FILE: return FileConcertRepository.getInstance();
            case ARRAY: return ArrayConcertRepository.getInstance();
        }

        throw new RuntimeException("No such type");
    }

    enum Type {
        DB, FILE, ARRAY
    }
}
