package gui;

import model.Concert;
import model.Movie;
import service.AccountService;
import service.AuditService;
import service.ConcertService;
import service.MovieService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Optional;

public class MainPageFrame extends JFrame {

    MovieService movieService = MovieService.getInstance();
    ConcertService concertService = ConcertService.getInstance();

    java.util.List<Movie> movies = movieService.getMovies();
    java.util.List<Concert> concerts = concertService.getConcerts();


    private JTextField movieSearch = new JTextField(30);
    private JTextField concertSearch = new JTextField(30);

    private JLabel image1;
    private JLabel image2;
    private JLabel image3;
    private JLabel image4;
    private JLabel image5;
    private JLabel image6;
    private JLabel image7;
    private JLabel image8;
    private JLabel image9;
    private JLabel image10;

    java.util.List<JLabel> titles = new ArrayList<>();

    private void addEventOnForm(JLabel title, JLabel image, JPanel p,Movie movie, Concert concert, String username) {
        p.add(image);
        title.setPreferredSize(new Dimension(150, 50));
        p.add(title);

        title.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (movie != null)
                    new EventInfoFrame(movie, username);
                else
                    new EventInfoFrame(concert, username);

                Runnable target = new AuditService("Event Selected");
                Thread t = new Thread(target);
                t.start();
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {
                title.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent e) {
                title.setForeground(Color.black);
            }

        });

    }


    private void createUIComponents() {
        image1 = new JLabel(new ImageIcon(movies.get(0).getImageSrc()));
        image2 = new JLabel(new ImageIcon(movies.get(1).getImageSrc()));
        image3 = new JLabel(new ImageIcon(movies.get(2).getImageSrc()));
        image4 = new JLabel(new ImageIcon(movies.get(3).getImageSrc()));
        image5 = new JLabel(new ImageIcon(movies.get(4).getImageSrc()));

        image6 = new JLabel(new ImageIcon(concerts.get(0).getImageSrc()));
        image7 = new JLabel(new ImageIcon(concerts.get(1).getImageSrc()));
        image8 = new JLabel(new ImageIcon(concerts.get(2).getImageSrc()));
        image9 = new JLabel(new ImageIcon(concerts.get(3).getImageSrc()));
        image10 = new JLabel(new ImageIcon(concerts.get(4).getImageSrc()));
    }


    public MainPageFrame(String username) {
        setLayout(new GridLayout(4, 3, 30, 30));

        JPanel p1 = new JPanel();
        add(p1);

        JPanel p2 = new JPanel();
        add(p2);

        JPanel p3 = new JPanel();
        add(p3);

        JPanel p4 = new JPanel();
        add(p4);

        JPanel p5 = new JPanel();
        add(p5);

        JPanel p6 = new JPanel();
        add(p6);

        JPanel p7 = new JPanel();
        add(p7);

        JPanel p8 = new JPanel();
        add(p8);

        JPanel p9 = new JPanel();
        add(p9);

        JPanel p10 = new JPanel();
        add(p10);

        JPanel p11 = new JPanel();
        add(p11);

        p1.setLayout(new GridLayout(5, 1));

        JButton deleteAccount = new JButton("Delete Account&History");
        p1.add(deleteAccount);

        JLabel searchMovie = new JLabel("Search Your Favorite Movie!");
        p1.add(searchMovie);
        p1.add(movieSearch);

        JLabel searchConcert = new JLabel("Search Your Favorite Artist!");
        p1.add(searchConcert);
        p1.add(concertSearch);

        deleteAccount.addActionListener(ev -> deleteAccount(username));

        createUIComponents();

        JLabel title1 = new JLabel(movies.get(0).getMovieName());
        addEventOnForm(title1, image1, p2, movies.get(0), null, username);

        JLabel title2 = new JLabel(movies.get(1).getMovieName());
        addEventOnForm(title2, image2, p3, movies.get(1), null, username);

        JLabel title3 = new JLabel(movies.get(2).getMovieName());
        addEventOnForm(title3, image3, p4, movies.get(2), null, username);

        JLabel title4 = new JLabel(movies.get(3).getMovieName());
        addEventOnForm(title4, image4, p5, movies.get(3), null, username );

        JLabel title5 = new JLabel(movies.get(4).getMovieName());
        addEventOnForm(title5, image5, p6, movies.get(4), null, username );

        JLabel title6 = new JLabel(concerts.get(0).getConcertName());
        addEventOnForm(title6, image6, p7, null, concerts.get(0), username );

        JLabel title7 = new JLabel(concerts.get(1).getConcertName());
        addEventOnForm(title7, image7, p8, null, concerts.get(1), username );

        JLabel title8 = new JLabel(concerts.get(2).getConcertName());
        addEventOnForm(title8, image8, p9, null, concerts.get(2), username);

        JLabel title9 = new JLabel(concerts.get(3).getConcertName());
        addEventOnForm(title9, image9, p10, null, concerts.get(3), username);

        JLabel title10 = new JLabel(concerts.get(4).getConcertName());
        addEventOnForm(title10, image10, p11, null, concerts.get(4), username);

        titles.add(title1);
        titles.add(title2);
        titles.add(title3);
        titles.add(title4);
        titles.add(title5);
        titles.add(title6);
        titles.add(title7);
        titles.add(title8);
        titles.add(title9);
        titles.add(title10);


        movieSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    findMovie();

                    Runnable target = new AuditService("Searched A Movie");
                    Thread t = new Thread(target);
                    t.start();
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        concertSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    findConcert();

                    Runnable target = new AuditService("Searched A Concert");
                    Thread t = new Thread(target);
                    t.start();
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void deleteAccount(String username) {
        AccountService accountService = AccountService.getInstance();
        accountService.deleteAccount(username);

        Runnable target = new AuditService("Delete Account");
        Thread t = new Thread(target);
        t.start();
    }

    public void findMovie() {
        String inputText = movieSearch.getText().trim();
        Optional<Movie> movie = movieService.findMovieByTitle(inputText);

        if (movie.isPresent()) {
            Movie m = movie.get();
            System.out.println(m.getMovieName());
             for (JLabel title : titles) {
                 title.setForeground(Color.black);

                 if (title.getText().equals(m.getMovieName()))
                      title.setForeground(Color.pink);
             }
        }
    }

    public void findConcert() {
        String inputText = concertSearch.getText().trim();
        Optional<Concert> concert = concertService.findConcertByArtist(inputText);

        if (concert.isPresent()) {
            Concert c = concert.get();
            System.out.println(c.getConcertName());

            for (JLabel title : titles) {
                title.setForeground(Color.black);

                if (title.getText().equals(c.getConcertName()))
                    title.setForeground(Color.pink);
            }
        }
    }
}
