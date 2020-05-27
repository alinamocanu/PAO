package service;

import model.Concert;
import model.Location;
import model.Seats;
import repositories.concerts.ConcertRepository;
import repositories.concerts.DBConcertRepository;
import repositories.DBSeatRepository;

import java.util.List;
import java.util.Optional;

public class ConcertService {
    private ConcertRepository concertRepository;

    private ConcertService() {
        concertRepository = ConcertRepository.build(ConcertRepository.Type.DB);
    }

    public List<Concert> getConcerts() {
        Optional<java.util.List<Concert>> concerts = concertRepository.getConcerts();

        return concerts.get();
    }

    public Location getConcertLocation(int id) {
        String concertId = String.valueOf(id);
        DBConcertRepository c = DBConcertRepository.getInstance();

        return c.getLocation(concertId).get();
    }

    public Seats getSeatsByLocationId(String id) {
        DBSeatRepository s = DBSeatRepository.getInstance();
        return s.getSeatsByLocationId(id);
    }

    public void updatePrice() {
        DBConcertRepository concertRepository = DBConcertRepository.getInstance();
        concertRepository.updatePrice();
    }

    public void updateNoOfSeats(String noOfSeatsLeft, String category, String id) {
        DBSeatRepository s =  DBSeatRepository.getInstance();
        s.updateNoOfSeats(noOfSeatsLeft, category, id);
    }

    public Optional<Concert> findConcertByArtist(String artist) {
        return concertRepository.findConcertByArtist(artist);
    }

    public static ConcertService getInstance() {
        return ConcertService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static ConcertService INSTANCE = new ConcertService();
    }
}
