package service;

import model.User;
import repositories.*;
import repositories.tickets.DBTicketRepository;
import repositories.tickets.TicketRepository;
import repositories.users.DBUserRepository;
import repositories.users.UserRepository;

import java.util.Optional;

public class AccountService {
    private UserRepository userRepository;

    private AccountService() {
        userRepository = UserRepository.build(UserRepository.Type.DB);
    }

    public void deleteAccount(String username) {
        UserRepository uRep = DBUserRepository.getInstance();
        Optional<User> user = uRep.findUserByUsername(username);

        DBBookingDetailsRepository bookingDetailsRepository = DBBookingDetailsRepository.getInstance();

        TicketRepository ticketRepository = DBTicketRepository.getInstance();
        if (user.isPresent()) {
            ticketRepository.deleteTicket(bookingDetailsRepository.getTicketId(user.get().getId()));

            bookingDetailsRepository.deleteBookingDetails(user.get().getId());
        }

        uRep.deleteUser(username);
    }

    public Optional<User> findUserByUsername(String username) {
         return userRepository.findUserByUsername(username);
    }

    public static AccountService getInstance() {
        return AccountService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static AccountService INSTANCE = new AccountService();
    }
}
