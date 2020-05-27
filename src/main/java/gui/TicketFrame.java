package gui;

import model.Concert;
import model.Movie;
import model.Location;
import model.Theatre;

import javax.swing.*;
import java.awt.*;

public class TicketFrame extends JFrame {
    private JLabel eventName = new JLabel();
    private JLabel date = new JLabel();
    private JLabel startTime = new JLabel();
    private JLabel duration = new JLabel();
    private JLabel buildingName  = new JLabel();
    private JLabel seat = new JLabel();

    public TicketFrame(Concert concert, Location location, String seatNo) {
        setLayout(new GridLayout(6, 1));

        eventName.setText(concert.getConcertName());
        add(eventName);

        date.setText(concert.getDate());
        add(date);

        startTime.setText(concert.getStartTime());
        add(startTime);

        duration.setText(concert.getDuration());
        add(duration);

        buildingName.setText(location.getBuildingName() + ", " + location.getCity());
        add(buildingName);

        seat.setText(seatNo);
        add(seat);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public TicketFrame(Movie movie, Theatre theatre, String seatNo) {
        setLayout(new GridLayout(6, 1));

        eventName.setText(movie.getMovieName());
        add(eventName);

        date.setText(movie.getDate());
        add(date);

        startTime.setText(movie.getStartTime());
        add(startTime);

        duration.setText(movie.getDuration());
        add(duration);

        buildingName.setText(theatre.getBuildingName());
        add(buildingName);

        seat.setText(seatNo);
        add(seat);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
