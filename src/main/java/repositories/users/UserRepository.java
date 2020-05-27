package repositories.users;

import model.User;

import java.util.Optional;

public interface UserRepository {

    void addUser(User user);
    Optional<User> findUserByUsername(String username);
    int getLastId();
    void deleteUser(String username);

    static UserRepository build(Type type) {
        switch (type) {
            case DB: return DBUserRepository.getInstance();
            case FILE: return FileUserRepository.getInstance();
            case ARRAY: return ArrayUserRepository.getInstance();
        }

        throw new RuntimeException("No such type");
    }

    enum Type {
        DB, FILE, ARRAY
    }
}