package repositories.users;

import exceptions.InexistentUserFileException;
import model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileUserRepository implements UserRepository {

    private final String file = "USERS";

    private FileUserRepository() {

    }

    @Override
    public void addUser(User user) {
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter("USERS", true))
           ) {
            writer.newLine();
            writer.write(user.getId() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getEmail());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        Path path = Paths.get(file);
        User user = null;

        try {
            if (!Files.exists(path)) {
                throw new InexistentUserFileException();
            }
            var list = Files.readAllLines(path);
            if (!list.isEmpty()) {
                for (String u : list) {
                    if (!u.equals("")) {
                        String[] attr = u.split(",");
                        if (attr[1].equals(username)) {
                            user = new User();
                            user.setId(Integer.parseInt(attr[0]));
                            user.setUsername(attr[1]);
                            user.setPassword(attr[2]);
                            user.setEmail(attr[3]);

                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(user);
    }

    @Override
    public int getLastId() {
        Path path = Paths.get(file);

        try {
            if (!Files.exists(path)) {
                throw new InexistentUserFileException();
            }
            var list = Files.readAllLines(path);
            if (!list.isEmpty()) {
                String u = list.get(list.size() - 1);
                String[] attr = u.split(",");

                return Integer.parseInt(attr[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deleteUser(String username) {

    }

    public static FileUserRepository getInstance() {
        return FileUserRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static FileUserRepository INSTANCE = new FileUserRepository();
    }
}