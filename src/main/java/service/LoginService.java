package service;

import model.User;
import repositories.users.UserRepository;

import java.util.Optional;

public class LoginService {

    private UserRepository userRepository;

    private LoginService() {
        userRepository = UserRepository.build(UserRepository.Type.DB);
    }

    public boolean login(User user) {
        Optional<User> result = userRepository.findUserByUsername(user.getUsername());

        if (result.isPresent()) {
            User u = result.get();
            return u.getPassword().equals(user.getPassword());
        }

        return false;
    }

    public int getLastId() {
       return userRepository.getLastId();
    }

    public void register(User user) {
        userRepository.addUser(user);
    }

    public static LoginService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static LoginService INSTANCE = new LoginService();
    }
}