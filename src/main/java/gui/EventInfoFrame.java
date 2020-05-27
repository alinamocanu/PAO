package gui;

import model.Concert;
import model.Movie;
import service.AuditService;
import javax.swing.*;
import java.awt.*;

public class EventInfoFrame extends  JFrame{
    private JPanel p1 = new JPanel();
    private JButton bookBtn = new JButton("Book Tickets");

    private JLabel title = new JLabel();
    private JLabel date = new JLabel();
    private JLabel startTime = new JLabel();
    private JLabel duration = new JLabel();
    private JLabel price = new JLabel();
    private JLabel priceB = new JLabel();
    private JLabel priceC = new JLabel();

    public EventInfoFrame(Movie movie, String username) {
        p1.setLayout(new GridLayout(6, 1));

        title.setText(movie.getMovieName());
        date.setText("Premiere: " + movie.getDate());
        startTime.setText("Starts at: " + movie.getStartTime());
        duration.setText("Duration: " + movie.getDuration());


        add(p1);

        p1.add(title);
        p1.add(date);
        p1.add(startTime);
        p1.add(duration);

        price.setText("Price: " + movie.getPrice() + "lei");
        p1.add(price);

        p1.add(bookBtn);
        buyTicket(bookBtn, movie, username);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public EventInfoFrame(Concert concert, String username) {
        p1.setLayout(new GridLayout(9, 1));

        JLabel artist = new JLabel();
        title.setText(concert.getConcertName());
        artist.setText(concert.getArtist());
        date.setText(concert.getDate());
        startTime.setText("Starts at: " + concert.getStartTime());
        duration.setText("Duration: " + concert.getDuration());

        add(p1);

        p1.add(title);
        p1.add(date);
        p1.add(startTime);
        p1.add(duration);
        p1.add(artist);

        price.setText("Cat A: " + (concert.getPrice() + 200) + " lei");
        p1.add(price);

        priceB.setText("Cat B: " + (concert.getPrice() + 100) + " lei");
        p1.add(priceB);

        priceC.setText("Cat C: " + concert.getPrice() + " lei");
        p1.add(priceC);

        p1.add(bookBtn);
        buyTicket(bookBtn, concert, username);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void buyTicket(JButton btn, Movie movie, String username) {
        btn.addActionListener(ev -> {
            new BuyTicketFrame(movie, username);

            Runnable target = new AuditService("Buying Ticket For Movie");
            Thread t = new Thread(target);
            t.start();
        });
    }

    public void buyTicket(JButton btn, Concert concert, String username) {
        btn.addActionListener(ev -> {
            new BuyTicketFrame(concert, username);

            Runnable target = new AuditService("Buying Ticket For Concert");
            Thread t = new Thread(target);
            t.start();
        });
    }
}
