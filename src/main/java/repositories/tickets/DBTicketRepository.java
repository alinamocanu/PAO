package repositories.tickets;

import model.Ticket;
import managers.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBTicketRepository implements TicketRepository {

    private DBTicketRepository() {

    }

    @Override
    public void addTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets VALUES (NULL, ?, ?, ?, ?)";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, String.valueOf(ticket.getPrice()));
            statement.setString(2, ticket.getEventName());
            statement.setString(3, ticket.getLocation());
            statement.setString(4, ticket.getSeat());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteTicket(int ticketId) {
        String sql = "DELETE FROM tickets WHERE id = ?;";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, String.valueOf(ticketId));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLastId() {
        return 0;
    }

    public static DBTicketRepository getInstance() {
        return DBTicketRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static DBTicketRepository INSTANCE = new DBTicketRepository();
    }
}
