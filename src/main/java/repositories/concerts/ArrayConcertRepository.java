package repositories.concerts;

import model.Concert;
import model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArrayConcertRepository implements ConcertRepository {
    private java.util.List<Concert> concerts = new ArrayList<>();

    private ArrayConcertRepository() {

    }

    @Override
    public void addConcert(Concert concert) {
        concerts.add(concert);
    }

    @Override
    public Optional<List<Concert>> getConcerts() {
        concerts.add(new Concert(1, "2020-04-10", "20:00", "2h 30 min", "alicia-keys.jpg", "Alicia Keys", "A.L.I.C.I.A Tour", 150));
        concerts.add(new Concert(2, "2020-10-10", "17:00", "3h 45 min", "andrea-bocelli.jpg", "Andrea Bocelli", "Music For Hope", 230));
        concerts.add(new Concert(3, "2020-07-15", "21:00", "3h 20 min", "ed_sheeran.jpg", "Ed Sheeran", "Divide Tour", 390));
        concerts.add(new Concert(4, "2020-05-19", "19:00", "3h 45 min", "lara_fabian.jpg", "Lara Fabian", "50 Tour", 120));
        concerts.add(new Concert(5, "2020-09-01", "21:30", "2h 15 min", "bon_jovi.jpg", "Bon Jovi", "Now And Forever Tour", 400));

        return Optional.ofNullable(concerts);
    }

    @Override
    public  Optional<Concert> findConcertByArtist(String artist) {
        for (Concert concert : concerts) {
            if (concert.getArtist().contains(artist)) {
                System.out.println(concert.getConcertName());
                return Optional.of(concert);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Location> getLocation(String concertId) {
        return Optional.empty();
    }

    @Override
    public void updatePrice() {

    }

    public static ArrayConcertRepository getInstance() {
        return ArrayConcertRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static ArrayConcertRepository INSTANCE = new ArrayConcertRepository();
    }
}
