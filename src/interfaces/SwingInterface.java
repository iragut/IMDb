package org.example.interfaces;

import org.example.IMDB;
import org.example.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingInterface implements ActionListener {
    IMDB imdb;
    JPasswordField password;
    JFrame frame;
    JLabel emailLabel, passwordLabel, welcomeLabel, textLabel;
    JTextField email;
    JButton loginButton;
    JCheckBox showPassword;

    public SwingInterface(IMDB imdb) {
        this.imdb = imdb;
        ImageIcon icon = new ImageIcon("src/main/java/org/example/img/imdb.png");
        frame = new JFrame("IMDB");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setIconImage(icon.getImage());

        welcomeLabel = new JLabel("Welcome back to IMDB!");
        welcomeLabel.setBounds(340, 50, 300, 50);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        textLabel = new JLabel("Enter your credentials!");
        textLabel.setBounds(370, 100, 300, 50);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.BOLD, 15));

        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(270, 150, 100, 50);
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font("Arial", Font.BOLD, 15));

        email = new JTextField();
        email.setBounds(350, 160, 250, 30);
        email.setFont(new Font("Arial", Font.BOLD, 15));

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(270, 200, 100, 50);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));

        password = new JPasswordField();
        password.setBounds(350, 210, 250, 30);
        password.setFont(new Font("Arial", Font.BOLD, 15));

        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(350, 240, 150, 30);
        showPassword.setFont(new Font("Arial", Font.BOLD, 15));
        showPassword.setBackground(Color.DARK_GRAY);
        showPassword.setForeground(Color.WHITE);
        showPassword.addActionListener(this);
        showPassword.setFocusable(false);


        loginButton = new JButton("Sign in");
        loginButton.setBounds(400, 270, 100, 30);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);
        loginButton.addActionListener(this);
        loginButton.setFocusable(false);

        frame.add(textLabel);
        frame.add(welcomeLabel);
        frame.add(emailLabel);
        frame.add(email);
        frame.add(passwordLabel);
        frame.add(password);
        frame.add(showPassword);
        frame.add(loginButton);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showPassword){
            if (showPassword.isSelected()){
                password.setEchoChar((char)0);
            } else {
                password.setEchoChar('*');
            }
        } else if (e.getSource() == loginButton){
            boolean found = false;
            String emailText = email.getText();
            String passwordText = new String(password.getPassword());
            for (int i = 0; i < imdb.getUsers().size(); i++) {
                User<?> user = imdb.getUsers().get(i);
                if (user.getInformation().getCredentials().getEmail().equals(emailText)) {
                    found = true;
                    if (user.getInformation().getCredentials().getPassword().equals(passwordText)) {
                        frame.dispose();
                        user.UserInterface(imdb);
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(null, "Email not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}