package repositories;

import model.bookingDetails;
import managers.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBBookingDetailsRepository {

    private DBBookingDetailsRepository() {

    }

    public void addBookingDetails(bookingDetails details) {
        String sql = "INSERT INTO booking_details VALUES (NULL, ?, ?)";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, String.valueOf(details.getUserId()));
            statement.setString(2, details.getDate());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTicketId(int userId) {
        String sql = "SELECT ticketId FROM booking_details WHERE userId = ?";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, String.valueOf(userId));

            ResultSet set = statement.executeQuery(); // in RS avem randurile din tabela

            if (set.next()) {

                return set.getInt("ticketId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void deleteBookingDetails(int userId) {
        String sql = "DELETE FROM booking_details WHERE userId = ?;";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, String.valueOf(userId));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static DBBookingDetailsRepository getInstance() {
        return DBBookingDetailsRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static  DBBookingDetailsRepository INSTANCE = new  DBBookingDetailsRepository();
    }
}
