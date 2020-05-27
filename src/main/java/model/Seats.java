package model;

public class Seats {
    private int id;
    private int catA;
    private int catB;
    private int catC;
    private int  locationId;

    public Seats(int id, int catA, int catB, int catC, int locationId) {
        this.id = id;
        this.catA = catA;
        this.catB = catB;
        this.catC = catC;
        this.locationId = locationId;
    }

    public int getId() {
        return id;
    }

    public int getCatA() {
        return catA;
    }

    public int getCatB() {
        return catB;
    }

    public int getCatC() {
        return catC;
    }
}
