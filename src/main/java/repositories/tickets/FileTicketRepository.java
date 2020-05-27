package repositories.tickets;

import model.Ticket;
import exceptions.InexistentUserFileException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTicketRepository implements TicketRepository {

    private FileTicketRepository() {

    }

    @Override
    public void addTicket(Ticket ticket) {
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter("TICKETS", true))
            ) {
            writer.newLine();
            writer.write(ticket.getId() + "," + ticket.getPrice() + "," + ticket.getEventName() + "," + ticket.getLocation() + "," + ticket.getSeat());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLastId() {
        String file = "TICKETS";
        Path path = Paths.get(file);

        try {
            if (!Files.exists(path)) {
                throw new InexistentUserFileException();
            }
            var list = Files.readAllLines(path);
            if (!list.isEmpty()) {
                String u = list.get(list.size() - 1);
                if (!u.equals("")) {
                    String[] attr = u.split(",");

                    return Integer.parseInt(attr[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deleteTicket(int ticketId) {

    }

    public static FileTicketRepository getInstance() {
        return FileTicketRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static FileTicketRepository INSTANCE = new FileTicketRepository();
    }
}
