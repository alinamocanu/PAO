package repositories.users;

import model.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ArrayUserRepository implements UserRepository {

        private Set<User> users = new HashSet<>();

        private ArrayUserRepository() {

        }

        @Override
        public void addUser(User user) {
            users.add(user);
        }

        @Override
        public Optional<User> findUserByUsername(String username) {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return Optional.of(user);
                }
            }
            return Optional.empty();
        }

        @Override
        public int getLastId() {
            if (!users.isEmpty())
                return (users.size()-1);
            return 0;
        }

        @Override
        public void deleteUser(String username) {

        }

        public static ArrayUserRepository getInstance() {
            return SingletonHolder.INSTANCE;
        }

        private static class SingletonHolder {
            private static ArrayUserRepository INSTANCE = new ArrayUserRepository();
        }
    }

