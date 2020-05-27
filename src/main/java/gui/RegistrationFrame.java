package gui;

import model.User;
import service.AuditService;
import service.LoginService;

import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame{


    private JTextField textField1 = new JTextField(10);
    private JPasswordField passwordField1 = new JPasswordField(10);
    private JTextField textField2 = new JTextField(10);

    int id = 0;

    public RegistrationFrame() {
        setLayout(new GridLayout(4, 1));

        JPanel p1 = new JPanel();
        add(p1);

        JPanel p2 = new JPanel();
        add(p2);

        JPanel p3 = new JPanel();
        add(p3);

        JPanel p4 = new JPanel();
        add(p4);

        JLabel e1 = new JLabel("Username");
        p1.add(e1);
        p1.add(textField1);

        JLabel e2 = new JLabel("Password");
        p2.add(e2);
        p2.add(passwordField1);

        JLabel e3 = new JLabel("E-mail");
        p3.add(e3);
        p3.add(textField2);

        JButton registerButton = new JButton("Register");
        p4.add(registerButton);


        registerButton.addActionListener(ev -> register());

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void register() {
        String username = textField1.getText();
        String password = String.valueOf(passwordField1.getPassword());
        String email = textField2.getText();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
             JOptionPane.showMessageDialog(null, "All fields are mandatory");
        } else {
            LoginService service = LoginService.getInstance();

            id = service.getLastId();
            id++;

            User user = new User(id, username, password, email);

            service.register(user);

            Runnable target = new AuditService("Register");
            Thread t = new Thread(target);
            t.start();

            JOptionPane.showMessageDialog(null, "Registration complete!");
        }
    }
}
