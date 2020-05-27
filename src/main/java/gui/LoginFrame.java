package gui;

import model.User;
import service.AuditService;
import service.LoginService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField textField1 = new JTextField(10);
    private JPasswordField passwordField1 = new JPasswordField(10);

    public LoginFrame() {
        setLayout(new GridLayout(3, 1));

        JPanel p1 = new JPanel();
        add(p1);

        JPanel p2 = new JPanel();
        add(p2);

        JPanel p3 = new JPanel();
        add(p3);

        JLabel e1 = new JLabel("Username");
        p1.add(e1);
        p1.add(textField1);

        JLabel e2 = new JLabel("Password");
        p2.add(e2);
        p2.add(passwordField1);

        JButton loginButton = new JButton("Login");
        p3.add(loginButton);
        JButton registerButton = new JButton("Register");
        p3.add(registerButton);


        loginButton.addActionListener(ev -> login());
        registerButton.addActionListener(ev1 -> new RegistrationFrame());

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void login() {
        String username = textField1.getText();
        String password = String.valueOf(passwordField1.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are mandatory!");
        } else {

            LoginService service = LoginService.getInstance();

            User user = new User(0, username, password, "");

            if (service.login(user)) {
                JOptionPane.showMessageDialog(null, "You are now logged in");

                Runnable target = new AuditService("Login");
                Thread t = new Thread(target);
                t.start();
                new MainPageFrame(user.getUsername());
            } else {
                JOptionPane.showMessageDialog(null, "Ops! You must register first! ");
            }
        }
    }
}
