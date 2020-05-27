package repositories.tickets;

import model.Ticket;

public interface TicketRepository {
    void addTicket(Ticket ticket);
    int getLastId();
    void deleteTicket(int ticketId);

    static TicketRepository build(TicketRepository.Type type) {
        switch (type) {
            case DB: return DBTicketRepository.getInstance();
            case FILE: return FileTicketRepository.getInstance();
            case ARRAY: return ArrayTicketRepository.getInstance();
        }

        throw new RuntimeException("No such type");
    }

    enum Type {
        DB, FILE, ARRAY
    }
}
