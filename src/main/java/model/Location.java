package model;

public class Location {
    int locationId;
    String buildingName;
    String city;
    int capacity;

    public Location() {
    }

    public Location(int locationId, String buildingName, String city, int capacity) {
        this.locationId = locationId;
        this.buildingName = buildingName;
        this.city = city;
        this.capacity = capacity;
    }

    public int getLocationId() {
        return locationId;
    }



    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getCity() {
        return city;
    }

    public int getCapacity() {
        return capacity;
    }
}
