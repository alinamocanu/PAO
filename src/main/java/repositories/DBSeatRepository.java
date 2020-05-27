package repositories;

import model.Seats;
import managers.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBSeatRepository {
    private DBSeatRepository() {

    }
    public Seats getSeatsByLocationId(String locationId) {
        String sql = "SELECT * FROM concert_locations JOIN seats ON concert_locations.id = seats.location_id WHERE concert_locations.id = ?" ;

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, locationId);

            ResultSet set = statement.executeQuery();
            if (set.next()) {
                int seatId = Integer.parseInt(set.getString("seats.id"));
                int catA = Integer.parseInt(set.getString("cat_A"));
                int catB = Integer.parseInt(set.getString("cat_B"));
                int catC = Integer.parseInt(set.getString("cat_C"));
                int idLocation = Integer.parseInt(set.getString("location_id"));
                return new Seats(seatId, catA, catB, catC, idLocation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateNoOfSeats(String noOfSeatsLeft, String category, String id) {
        String sql = "";
        switch (category) {
            case "cat A": {
                 sql = "UPDATE seats SET cat_A = ? WHERE id = ?";
                 break;
            }
            case "cat B": {
                 sql = "UPDATE seats SET cat_B = ? WHERE id = ?";
                 break;
            }
            case "cat C": {
                sql = "UPDATE seats SET cat_C = ? WHERE id = ?";
                break;
            }
        }

        try(  Connection con = DBConnectionManager.getInstance().createConnection();
              PreparedStatement statement = con.prepareStatement(sql)
              ) {
            statement.setString(1, noOfSeatsLeft);
            statement.setString(2, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBSeatRepository getInstance() {
        return DBSeatRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static DBSeatRepository INSTANCE = new DBSeatRepository ();
    }
}
