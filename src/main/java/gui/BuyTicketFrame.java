package gui;

import model.Ticket;
import model.bookingDetails;
import model.Concert;
import model.Movie;
import model.Location;
import model.Seats;
import model.Theatre;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class BuyTicketFrame extends JFrame {
    private JPanel p1 = new JPanel();
    private Theatre theatre = new Theatre();
    private Boolean categoryChosen = false;
    private String seat;
    private JTextField cardNo = new JTextField();
    private JTextField securityCode = new JTextField();

    private JLabel locationName = new JLabel();
    private JLabel cardNumber = new JLabel("Credit Card Number: ");
    private JLabel security = new JLabel("Security Code: ");

    private JButton getTicket = new JButton(" Get Ticket!");
    private JButton catA = new JButton("cat A");
    private JButton catB = new JButton("cat B");
    private JButton catC = new JButton("cat C");

    private AccountService accountService = AccountService.getInstance();
    private MovieService movieService = MovieService.getInstance();
    private ConcertService concertService = ConcertService.getInstance();
    private TicketService ticketService = TicketService.getInstance();

    private int noOfTickets = 0;

    public BuyTicketFrame(Movie movie, String username) {
        p1.setLayout(new GridLayout(11, 1));
        locationName.setText("STEP 1: Choose your favorite Cinema: ");
        add(p1);
        p1.add(locationName);
        displayPossibleLocations(movie);



        getTicket.addActionListener(ev -> {
            if (validateTicket()) {
                 String seatNo = bookSeatMovie(theatre);
                 new TicketFrame(movie, theatre, seatNo);

                 noOfTickets = ticketService.getLastId();
                 noOfTickets++;
                 Ticket ticket = new Ticket(noOfTickets, movie.getPrice(), movie.getMovieName(), theatre.getBuildingName(), seatNo);
                 storeTicket(ticket);

                 Date date = new Date();
                 discount(date);


                 bookingDetails details = new bookingDetails(noOfTickets, accountService.findUserByUsername(username), date);
                 storeBookingDetails(details);

                Runnable target = new AuditService("Ticket Bought For Movie");
                Thread t = new Thread(target);
                t.start();
            }
        });

        p1.add(cardNumber);
        p1.add(cardNo);

        p1.add(security);
        p1.add(securityCode);

        p1.add(getTicket);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public BuyTicketFrame(Concert concert, String username) {
        p1.setLayout(new GridLayout(11, 1));
        JLabel location = new JLabel(getConcertLocation(concert.getEventId()).getBuildingName() + ", " + getConcertLocation(concert.getEventId()).getCity());
        locationName.setText("Location: ");

        getTicket.addActionListener(ev -> {
            if (validateTicket()) {
                if (seat != null) {
                     new TicketFrame(concert, getConcertLocation(concert.getEventId()), seat);

                     int price = concert.getPrice();
                     if (seat.contains("A"))
                         price += 200;
                     else if (seat.contains("B"))
                         price += 100;

                     noOfTickets = ticketService.getLastId();
                     noOfTickets++;
                     Ticket ticket = new Ticket(noOfTickets, price, concert.getConcertName(), getConcertLocation(concert.getEventId()).getBuildingName(), seat);
                     storeTicket(ticket);

                     Date date = new Date();
                     discount(date);

                     bookingDetails details = new bookingDetails(noOfTickets, accountService.findUserByUsername(username), date);
                     storeBookingDetails(details);

                    Runnable target = new AuditService("Ticket Bought For Concert");
                    Thread t = new Thread(target);
                    t.start();
                }
            }
        });

        add(p1);
        p1.add(locationName);
        p1.add(location);

        addCategories(String.valueOf(getConcertLocation(concert.getEventId()).getLocationId()));

        p1.add(cardNumber);
        p1.add(cardNo);

        p1.add(security);
        p1.add(securityCode);

        p1.add(getTicket);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void displayPossibleLocations(Movie movie) {
        java.util.List<Theatre> theatres = movieService.getTheatres();

        for (Theatre theatre : theatres) {
            for (Optional<Movie> m : theatre.getAvailableMovies())
                if (m.isPresent()) {
                    if (m.get().getMovieName().equals(movie.getMovieName())) {
                        JButton btn = new JButton(theatre.getBuildingName() + ", " + theatre.getCity());

                        btn.addActionListener(ev -> {
                            this.theatre = theatre;

                            Runnable target = new AuditService("Theatre selected");
                            Thread t = new Thread(target);
                            t.start();

                            categoryChosen = true;
                        });

                        p1.add(btn);
                    }
                }
        }
    }

    public Location getConcertLocation(int id) {
        ConcertService service  = ConcertService.getInstance();
        return service.getConcertLocation(id);
    }

    public void addCategories(String id) {
        p1.add(catA);
        catA.addActionListener(ev1 -> seat = bookSeatConcert(id, catA.getText()));

        p1.add(catB);
        catB.addActionListener(ev2 -> seat = bookSeatConcert(id, catB.getText()));

        p1.add(catC);
        catC.addActionListener(ev3 -> seat = bookSeatConcert(id, catC.getText()));
    }

    public String bookSeatConcert(String id, String category) {
        categoryChosen = true;

        Seats seats = concertService.getSeatsByLocationId(id);

        int noOfSeats = 0;

        switch (category) {
            case "cat A": {
                noOfSeats = seats.getCatA();
                break;
            }
            case "cat B": {
                noOfSeats = seats.getCatB();
                break;
            }
            case "cat C": {
                noOfSeats = seats.getCatC();
                break;
            }
        }

        if (noOfSeats != 0 ) {
            noOfSeats--;
            int finalNoOfSeats = noOfSeats;
            getTicket.addActionListener(ev ->
                    concertService.updateNoOfSeats(String.valueOf(finalNoOfSeats), category, String.valueOf(seats.getId())));

            Runnable target = new AuditService("Selected Seat");
            Thread t = new Thread(target);
            t.start();

            return category + " " + noOfSeats;
        } else {
            JOptionPane.showMessageDialog(null, "There are no seats left!");
            return null;
        }
    }

    public String bookSeatMovie(Theatre th) {
        int seatNo = th.getCapacity();
        seatNo--;
        movieService.updateNoOfSeats(seatNo, String.valueOf(th.getLocationId()));
        if (seatNo == 0)
            replaceTheatre(theatre);

        return String.valueOf(seatNo);
    }

    public Boolean validateTicket() {

        if (securityCode.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter your security code!");
            return false;
        }

        if (cardNo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter your cardNumber!");
            return false;
        }

        if (!categoryChosen) {
            JOptionPane.showMessageDialog(null, "You must pick a category!");
            return false;
        }

        Runnable target = new AuditService("Entered card details");
        Thread t = new Thread(target);
        t.start();

        categoryChosen = false;
        return true;
    }

    public void storeTicket(Ticket ticket) {
        ticketService.storeTicket(ticket);
    }

    public void storeBookingDetails(bookingDetails details) {
        ticketService.storeBookingDetails(details);
    }

    public void discount (Date date) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.MONTH, 30);

        c.set(Calendar.DATE, 5);

        c.set(Calendar.YEAR, 2020);

        Date dateOne = c.getTime();

        if (date.after(dateOne)){
            movieService.updatePrice();
            concertService.updatePrice();
        }
    }

    public void replaceTheatre(Theatre theatre) {
        movieService.replaceTheatre(theatre);
    }
}