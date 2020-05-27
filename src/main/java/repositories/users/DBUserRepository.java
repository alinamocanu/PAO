package repositories.users;

import managers.DBConnectionManager;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DBUserRepository  implements UserRepository {

    private DBUserRepository() {

    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO users VALUES (NULL, ?, ?, ?)";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, username);

            ResultSet set = statement.executeQuery(); // in RS avem randurile din tabela

            if (set.next()) {
                int id = set.getInt("id");
                String u = set.getString("username");
                String p = set.getString("password");
                String e = set.getString("e-mail");

                return Optional.of(new User(id, u, p, e));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?;";
        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLastId() {
        return 0;
    }

    public static DBUserRepository getInstance() {
        return DBUserRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static DBUserRepository INSTANCE = new DBUserRepository();
    }
}