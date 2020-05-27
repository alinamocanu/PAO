package service;

import model.Ticket;
import model.bookingDetails;
import repositories.DBBookingDetailsRepository;
import repositories.tickets.TicketRepository;

public class TicketService {
    TicketRepository ticketRepository;

    private TicketService() {
       ticketRepository = TicketRepository.build(TicketRepository.Type.DB);
    }

    public void storeTicket(Ticket ticket) {
        ticketRepository.addTicket(ticket);
    }

    public void storeBookingDetails(bookingDetails details) {
        DBBookingDetailsRepository b = DBBookingDetailsRepository.getInstance();
        b.addBookingDetails(details);
    }

    public int getLastId() {
        return ticketRepository.getLastId();
    }

    public static TicketService getInstance() {
        return TicketService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static TicketService INSTANCE = new TicketService();
    }
}
