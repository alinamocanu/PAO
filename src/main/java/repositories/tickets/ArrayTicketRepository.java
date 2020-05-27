package repositories.tickets;

import model.Ticket;

import java.util.ArrayList;

public class ArrayTicketRepository implements TicketRepository {

    private java.util.List<Ticket> tickets = new ArrayList<>();

    private ArrayTicketRepository() {

    }

    @Override
    public void addTicket(Ticket ticket) {
           tickets.add(ticket);
    }

    @Override
    public int getLastId() {
        if(!tickets.isEmpty()) {
            return (tickets.size() - 1);
        }
        return 0;
    }

    @Override
    public void deleteTicket(int titcketId) {

    }

    public static ArrayTicketRepository getInstance() {
        return ArrayTicketRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static ArrayTicketRepository INSTANCE = new ArrayTicketRepository();
    }
}
