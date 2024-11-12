package org.example.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.IMDB;
import org.example.enums.RequestTypes;
import org.example.interfaces.InterfaceMethodes;
import org.example.strategy.RequestAccept;
import org.example.user.Staff;
import org.example.user.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private RequestTypes type;
    private Date createdDate;
    private String username;
    private String description;
    private String to;
    private String actorName;
    private String movieTitle;
    private int id;

    public void printRequest() {
        System.out.println("Request type: " + type);
        System.out.println("Created date: " + createdDate);
        System.out.println("Created by: " + username);
        System.out.println("Description: " + description);
        if (actorName != null)
            System.out.println("Actor name: " + actorName);
        if (movieTitle != null)
            System.out.println("Movie title: " + movieTitle);
        System.out.println("-".repeat(64));
    }

    public void printRequestUI(IMDB imdb, DefaultListModel<String> listModel, String request, boolean toRemove) {
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(400, 350, "Request");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel title = interfaceMethodes.createLabel("Request type: " + type, 20, 5, 500, 50, "#E1DE07");
        title.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel createdDate = interfaceMethodes.createLabel("Created date: " + this.createdDate, 20, 50, 500, 50, "#ffffff");
        createdDate.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel username = interfaceMethodes.createLabel("Created by: " + this.username, 20, 100, 500, 50, "#ffffff");
        username.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JTextArea description = new JTextArea("Description: " + this.description);
        description.setBounds(20, 150, 350, 50);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBackground(Color.decode("#000011"));
        description.setForeground(Color.decode("#ffffff"));
        description.setFont(new Font("Impact Regular", Font.BOLD, 15));


        if (actorName != null) {
            JLabel actorName = interfaceMethodes.createLabel("Actor name: " + this.actorName, 20, 200, 500, 50, "#ffffff");
            actorName.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(actorName);
        }

        if (movieTitle != null) {
            JLabel movieTitle = interfaceMethodes.createLabel("Movie title: " + this.movieTitle, 20, 200, 500, 50, "#ffffff");
            movieTitle.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(movieTitle);
        }

        if (toRemove) {
            JButton accept = interfaceMethodes.createButton("Accept", 240, 250, 100, 30, "#2f2f2f", "#00E006", null);
            accept.setBorder(new LineBorder(Color.decode("#00E006")));
            accept.addActionListener(e -> {
                User<?> user = imdb.findUser(this.username);
                imdb.getNotificationSubject().notifyObservers(user, "Your request has been resolved!");
                listModel.removeElement(request);
                RequestHolder.removeRequest(this);
                if (movieTitle != null || actorName != null) {
                    User<?> user1 = imdb.findUser(this.username);
                    RequestAccept requestAccept = new RequestAccept();
                    user1.setExperience(user1.getExperience() + requestAccept.calculateExperience());
                }
                frame.dispose();
            });


            JButton decline = interfaceMethodes.createButton("Decline", 20, 250, 100, 30, "#2f2f2f", "#F53F20", null);
            decline.setBorder(new LineBorder(Color.decode("#F53F20")));
            decline.addActionListener(e -> {
                User<?> user = imdb.findUser(this.username);
                imdb.getNotificationSubject().notifyObservers(user, "Your request has declined!");
                RequestHolder.removeRequest(this);
                listModel.removeElement(request);
                frame.dispose();
            });

            frame.add(accept);
            frame.add(decline);
        } else {
            JButton Remove = interfaceMethodes.createButton("Remove", 150, 250, 100, 30, "#2f2f2f", "#00E006", null);
            Remove.setBorder(new LineBorder(Color.decode("#00E006")));
            Remove.addActionListener(e -> {
                if (RequestHolder.getRequests().contains(this)) {
                    RequestHolder.removeRequest(this);
                } else {
                    User<?> user = imdb.findUser(this.to);
                    ((Staff<?>) user).getRequests().remove(this);
                }

                listModel.removeElement(request);
                frame.dispose();
            });
            frame.add(Remove);
        }

        frame.add(title);
        frame.add(createdDate);
        frame.add(username);
        frame.add(description);
        frame.setVisible(true);
    }
}
