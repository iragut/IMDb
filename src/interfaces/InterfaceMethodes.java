package org.example.interfaces;


import lombok.NoArgsConstructor;
import org.example.IMDB;
import org.example.entities.Movie;
import org.example.entities.Production;
import org.example.entities.Series;
import org.example.enums.AccountType;
import org.example.input.production.Rating;
import org.example.user.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;

@NoArgsConstructor
public class InterfaceMethodes {
    public JFrame createFrame(int width, int height, String title){
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        ImageIcon icon = new ImageIcon("src/main/java/org/example/img/imdb.png");
        frame.setIconImage(icon.getImage());
        return frame;
    }

    public JPanel createPanel(int x, int y, int width, int height, String color){
        JPanel panel = new JPanel();
        panel.setBounds(x, y, width, height);
        panel.setBackground(Color.decode(color));
        return panel;
    }

    public JLabel createLabel(String text, int x, int y, int width, int height, String color){
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setForeground(Color.decode(color));
        return label;
    }

    public JFrame addInformationUser(JFrame frame, User<?> user){
        JLabel experience;
        JLabel username = this.createLabel("Username: " + user.getUsername(),
                21, 20, 300, 50, "#ffffff");
        username.setFont(new Font("Impact Regular", Font.BOLD, 15));
        if (user.getUserType().equals(AccountType.Admin)) {
            experience = this.createLabel("Exp: -", 221, 40, 300, 50, "#ffffff");
        } else {
            experience = this.createLabel("Exp: " + user.getExperience(),
                    221, 40, 300, 50, "#ffffff");
        }
        experience.setFont(new Font("Impact Regular", Font.BOLD, 15));

        frame.add(username);
        frame.add(experience);
        return frame;
    }

    public JTextField createTextField(int x, int y, int width, int height){
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        textField.setFont(new Font("Impact Regular", Font.BOLD, 15));
        return textField;
    }

    public JButton createButton(String text, int x, int y, int width, int height, String backgroundColor, String textColor, ActionListener actionListener){
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setBackground(Color.decode(backgroundColor));
        button.setForeground(Color.decode(textColor));
        button.addActionListener(actionListener);
        button.setFocusable(false);
        button.setFont(new Font("Impact Regular", Font.BOLD, 15));
        return button;
    }

    public JFrame selectMovie(JFrame frame, IMDB imdb, User<?> user){
        Random random = new Random();
        int index, index1;

        // Movie 1
        do {
            index = random.nextInt(imdb.getProductions().size());
        } while (!imdb.getProductions().get(index).getType().equals("Movie") ||
                imdb.getProductions().get(index).getTitle().length() >= 15);

        JLabel movie1 = this.createLabel(imdb.getProductions().get(index).getTitle() + "     Grade: " +
                        imdb.getProductions().get(index).getAverageRating(), 320, 70, 300, 50, "#58E074");
        movie1.setFont(new Font("Impact Regular", Font.BOLD, 16));
        JLabel releaseYear = this.createLabel("Release Year:  " + ((Movie) imdb.getProductions().get(index)).getReleaseYear(),
                320, 100, 300, 50, "#ffffff");
        releaseYear.setFont(new Font("Impact Regular", Font.BOLD, 12));

        JLabel duration = this.createLabel("Duration:  " + ((Movie) imdb.getProductions().get(index)).getDuration(),
                320, 120, 300, 50, "#ffffff");
        duration.setFont(new Font("Impact Regular", Font.BOLD, 12));

        String genresString = imdb.getProductions().get(index).getGenres().toString();
        JLabel genres = this.createLabel("Genres:  " + genresString.substring(1, genresString.length() - 1),
                320, 140, 300, 50, "#ffffff");
        genres.setFont(new Font("Impact Regular", Font.BOLD, 12));

        String director = imdb.getProductions().get(index).getDirectors().get(0);
        JLabel directorLabel = this.createLabel("Directors:  " + director + ", etc.",
                320, 170, 300, 50, "#ffffff");
        directorLabel.setFont(new Font("Impact Regular", Font.BOLD, 15));

        String actor = imdb.getProductions().get(index).getActors().get(0);
        JLabel actorLabel = this.createLabel("Actors:  " + actor + ", etc.",
                320, 190, 300, 50, "#ffffff");
        actorLabel.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel ratingLabel = this.createLabel("Rating:", 320, 210, 300, 50, "#ffffff");

        if (imdb.getProductions().get(index).getRatings().isEmpty()) {
            JLabel rating = this.createLabel("Be the first !", 320, 230, 300, 50, "#ffffff");
            rating.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(rating);
        } else if (imdb.getProductions().get(index).getRatings().size() == 1){
            Rating rating = imdb.getProductions().get(index).getRatings().get(0);
            JLabel rating1 = this.createLabel(rating.getRating() + " by " + rating.getUsername(), 320, 230, 300, 50, "#ffffff");
            rating1.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(rating1);
        } else {
            Rating rating = imdb.getProductions().get(index).getRatings().get(0);
            JLabel rating1 = this.createLabel(rating.getRating() + " by " + rating.getUsername(), 320, 230, 300, 50, "#ffffff");
            rating1.setFont(new Font("Impact Regular", Font.BOLD, 15));
            rating = imdb.getProductions().get(index).getRatings().get(1);
            JLabel rating2 = this.createLabel(rating.getRating() + " by " + rating.getUsername() , 320, 250, 300, 50, "#ffffff");
            rating2.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(rating1);
            frame.add(rating2);
        }

        // Movie 2
        do {
            index1 = random.nextInt(imdb.getProductions().size());
        } while (index1 == index || (!imdb.getProductions().get(index1).getType().equals("Movie") ||
                imdb.getProductions().get(index).getTitle().length() >= 15));

        JLabel movie2 = this.createLabel(imdb.getProductions().get(index1).getTitle() + "     Grade: " +
                imdb.getProductions().get(index1).getAverageRating(), 800, 70, 280, 50, "#58E074");
        movie2.setFont(new Font("Impact Regular", Font.BOLD, 16));

        JLabel releaseYear1 = this.createLabel("Release Year:  " + ((Movie) imdb.getProductions().get(index1)).getReleaseYear(),
                800, 100, 300, 50, "#ffffff");
        releaseYear1.setFont(new Font("Impact Regular", Font.BOLD, 12));

        JLabel duration1 = this.createLabel("Duration:  " + ((Movie) imdb.getProductions().get(index1)).getDuration(),
                800, 120, 300, 50, "#ffffff");
        duration1.setFont(new Font("Impact Regular", Font.BOLD, 12));

        String genresString1 = imdb.getProductions().get(index1).getGenres().toString();
        JLabel genres1 = this.createLabel("Genres:  " + genresString1.substring(1, genresString1.length() - 1),
                800, 140, 300, 50, "#ffffff");
        genres1.setFont(new Font("Impact Regular", Font.BOLD, 12));

        String director1 = imdb.getProductions().get(index1).getDirectors().get(0);
        JLabel directorLabel1 = this.createLabel("Directors:  " + director1 + ", etc.",
                800, 170, 300, 50, "#ffffff");
        directorLabel1.setFont(new Font("Impact Regular", Font.BOLD, 15));

        String actor1 = imdb.getProductions().get(index1).getActors().get(0);
        JLabel actorLabel1 = this.createLabel("Actors:  " + actor1 + ", etc.",
                800, 190, 300, 50, "#ffffff");
        actorLabel1.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel ratingLabel1 = this.createLabel("Rating:", 800, 210, 300, 50, "#ffffff");

        if (imdb.getProductions().get(index1).getRatings().isEmpty()) {
            JLabel rating = this.createLabel("Be the first !", 800, 230, 300, 50, "#ffffff");
            rating.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(rating);
        } else if (imdb.getProductions().get(index1).getRatings().size() == 1){
            Rating rating = imdb.getProductions().get(index1).getRatings().get(0);
            JLabel rating1 = this.createLabel(rating.getRating() + " by " + rating.getUsername(),
                    800, 230, 300, 50, "#ffffff");
            rating1.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(rating1);
        } else {
            Rating rating = imdb.getProductions().get(index1).getRatings().get(0);
            JLabel rating1 = this.createLabel(rating.getRating() + " by " + rating.getUsername(),
                    800, 230, 300, 50, "#ffffff");
            rating1.setFont(new Font("Impact Regular", Font.BOLD, 15));
            rating = imdb.getProductions().get(index1).getRatings().get(1);
            JLabel rating2 = this.createLabel(rating.getRating() + " by " + rating.getUsername() ,
                    800, 250, 300, 50, "#ffffff");
            rating2.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(rating1);
            frame.add(rating2);
        }

        final int finalIndex = index;
        Production production = imdb.getProductions().get(index);
        JButton button;
        if (imdb.getProductions().get(index).getImage() == null) {
            button = new JButton("No image available");
            button.setFocusable(false);
            button.setBounds(570, 40, 210, 270);
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.decode("#ffffff"));
            button.addActionListener(e -> {
                if (imdb.getProductions().contains(production)){
                    production.displayInfoUI(user);
                } else {
                    JOptionPane.showMessageDialog(null, "This movie is not available anymore");
                }
            });
            button.setFont(new Font("Impact Regular", Font.BOLD, 15));
        } else {
            button = new JButton(imdb.getProductions().get(index).getImage());
            button.setBounds(570, 40, 210, 270);
            button.setFocusable(false);
            button.setBackground(Color.decode("#2f2f2f"));
            button.addActionListener(e -> {
                if (imdb.getProductions().contains(production)){
                    production.displayInfoUI(user);
                } else {
                    JOptionPane.showMessageDialog(null, "This movie is not available anymore");
                }
            });
        }
        frame.add(button);

        frame.add(movie1);
        frame.add(releaseYear);
        frame.add(duration);
        frame.add(genres);
        frame.add(directorLabel);
        frame.add(actorLabel);
        frame.add(ratingLabel);

        frame.add(movie2);
        frame.add(releaseYear1);
        frame.add(duration1);
        frame.add(genres1);
        frame.add(directorLabel1);
        frame.add(actorLabel1);
        frame.add(ratingLabel1);

        return frame;
    }

    public JFrame selectSeries(JFrame frame, IMDB imdb, User<?> user){
        Random random = new Random();
        int index, index1;

        // Series 1
        do {
            index = random.nextInt(imdb.getProductions().size());
        } while (!imdb.getProductions().get(index).getType().equals("Series") ||
                imdb.getProductions().get(index).getTitle().length() >= 15);

        JLabel series1 = this.createLabel(imdb.getProductions().get(index).getTitle() + "     Grade: " +
                imdb.getProductions().get(index).getAverageRating(), 320, 330, 300, 50, "#58E074");
        series1.setFont(new Font("Impact Regular", Font.BOLD, 17));

        JLabel releaseYear = this.createLabel("Release Year:  " + ((Series) imdb.getProductions().get(index)).getReleaseYear(),
                320, 360, 300, 50, "#ffffff");
        releaseYear.setFont(new Font("Impact Regular", Font.BOLD, 12));

        JLabel duration = this.createLabel("Number of seasons:  " + ((Series) imdb.getProductions().get(index)).getNumSeasons(),
                320, 380, 300, 50, "#ffffff");
        duration.setFont(new Font("Impact Regular", Font.BOLD, 12));

        String genresString = imdb.getProductions().get(index).getGenres().toString();
        JLabel genres = this.createLabel("Genres:  " + genresString.substring(1, genresString.length() - 1),
                320, 400, 300, 50, "#ffffff");
        genres.setFont(new Font("Impact Regular", Font.BOLD, 12));

        String director = imdb.getProductions().get(index).getDirectors().get(0);
        JLabel directorLabel = this.createLabel("Directors:  " + director + ", etc.",
                320, 420, 300, 50, "#ffffff");
        directorLabel.setFont(new Font("Impact Regular", Font.BOLD, 15));

        String actor = imdb.getProductions().get(index).getActors().get(0);
        JLabel actorLabel = this.createLabel("Actors:  " + actor + ", etc.",
                320, 440, 300, 50, "#ffffff");
        actorLabel.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel ratingLabel = this.createLabel("Rating:", 320, 480, 300, 50, "#ffffff");

        if (imdb.getProductions().get(index).getRatings().isEmpty()) {
            JLabel rating = this.createLabel("Be the first !", 320, 500, 300, 50, "#ffffff");
            rating.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(rating);
        } else if (imdb.getProductions().get(index).getRatings().size() == 1){
            Rating rating = imdb.getProductions().get(index).getRatings().get(0);
            JLabel rating1 = this.createLabel(rating.getRating() + " by " + rating.getUsername(),
                    320, 500, 300, 50, "#ffffff");
            rating1.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(rating1);
        } else {
            Rating rating = imdb.getProductions().get(index).getRatings().get(0);
            JLabel rating1 = this.createLabel(rating.getRating() + " by " + rating.getUsername(),
                    320, 500, 300, 50, "#ffffff");
            rating1.setFont(new Font("Impact Regular", Font.BOLD, 15));
            rating = imdb.getProductions().get(index).getRatings().get(1);
            JLabel rating2 = this.createLabel(rating.getRating() + " by " + rating.getUsername() ,
                    320, 520, 300, 50, "#ffffff");
            rating2.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(rating1);
            frame.add(rating2);
        }

        // Series 2
        do {
            index1 = random.nextInt(imdb.getProductions().size());
        } while (index1 == index || (!imdb.getProductions().get(index1).getType().equals("Series") ||
                imdb.getProductions().get(index).getTitle().length() >= 15));

        JLabel series2 = this.createLabel(imdb.getProductions().get(index1).getTitle() + "     Grade: " +
                imdb.getProductions().get(index1).getAverageRating(), 800, 330, 300, 50, "#58E074");
        series2.setFont(new Font("Impact Regular", Font.BOLD, 16));

        JLabel releaseYear1 = this.createLabel("Release Year:  " + ((Series) imdb.getProductions().get(index1)).getReleaseYear(),
                800, 360, 300, 50, "#ffffff");
        releaseYear1.setFont(new Font("Impact Regular", Font.BOLD, 12));

        JLabel duration1 = this.createLabel("Number of seasons:  " + ((Series) imdb.getProductions().get(index1)).getNumSeasons(),
                800, 380, 300, 50, "#ffffff");
        duration1.setFont(new Font("Impact Regular", Font.BOLD, 12));

        String genresString1 = imdb.getProductions().get(index1).getGenres().toString();
        JLabel genres1 = this.createLabel("Genres:  " + genresString1.substring(1, genresString1.length() - 1),
                800,400, 300, 50, "#ffffff");
        genres1.setFont(new Font("Impact Regular", Font.BOLD, 12));

        String director1 = imdb.getProductions().get(index1).getDirectors().get(0);
        JLabel directorLabel1 = this.createLabel("Directors:  " + director1 + ", etc.",
                800, 420, 300, 50, "#ffffff");
        directorLabel1.setFont(new Font("Impact Regular", Font.BOLD, 15));

        String actor1 = imdb.getProductions().get(index1).getActors().get(0);
        JLabel actorLabel1 = this.createLabel("Actors:  " + actor1 + ", etc.",
                800, 440, 300, 50, "#ffffff");
        actorLabel1.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel ratingLabel1 = this.createLabel("Rating:", 800, 480, 300, 50, "#ffffff");

        if (imdb.getProductions().get(index1).getRatings().isEmpty()) {
            JLabel rating = this.createLabel("Be the first !", 800, 500, 300, 50, "#ffffff");
            rating.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(rating);
        } else if (imdb.getProductions().get(index1).getRatings().size() == 1){
            Rating rating = imdb.getProductions().get(index1).getRatings().get(0);
            JLabel rating1 = this.createLabel(rating.getRating() + " by " + rating.getUsername(),
                    800, 500, 300, 50, "#ffffff");
            rating1.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(rating1);
        } else {
            Rating rating = imdb.getProductions().get(index1).getRatings().get(0);
            JLabel rating1 = this.createLabel(rating.getRating() + " by " + rating.getUsername(),
                    800, 500, 300, 50, "#ffffff");
            rating1.setFont(new Font("Impact Regular", Font.BOLD, 15));
            rating = imdb.getProductions().get(index1).getRatings().get(1);
            JLabel rating2 = this.createLabel(rating.getRating() + " by " + rating.getUsername() ,
                    800, 520, 300, 50, "#ffffff");
            rating2.setFont(new Font("Impact Regular", Font.BOLD, 15));
            frame.add(rating1);
            frame.add(rating2);
        }

        final int finalIndex = index;
        Production production = imdb.getProductions().get(index);
        JButton button;
        if (imdb.getProductions().get(index).getImage() == null) {
            button = new JButton("No image available");
            button.setFocusable(false);
            button.setBounds(570, 325, 210, 270);
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.decode("#ffffff"));
            button.addActionListener(e -> {
                if (imdb.getProductions().contains(production)){
                    production.displayInfoUI(user);
                } else {
                    JOptionPane.showMessageDialog(null, "This series is not available anymore");
                }
            });
            button.setFont(new Font("Impact Regular", Font.BOLD, 15));
        } else {
            button = new JButton(imdb.getProductions().get(index).getImage());
            button.setFocusable(false);
            button.setBounds(570, 320, 210, 270);
            button.setBackground(Color.decode("#2f2f2f"));
            button.addActionListener(e -> {
                if (imdb.getProductions().contains(production)){
                    production.displayInfoUI(user);
                } else {
                    JOptionPane.showMessageDialog(null, "This series is not available anymore");
                }
            });
        }
        frame.add(button);

        frame.add(series1);
        frame.add(releaseYear);
        frame.add(duration);
        frame.add(genres);
        frame.add(directorLabel);
        frame.add(actorLabel);
        frame.add(ratingLabel);

        frame.add(series2);
        frame.add(releaseYear1);
        frame.add(duration1);
        frame.add(genres1);
        frame.add(directorLabel1);
        frame.add(actorLabel1);
        frame.add(ratingLabel1);

        return frame;
    }

    public void settings(User<?> user){
        JFrame frame = this.createFrame(500, 300, "Settings");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel username = this.createLabel("Username: " + user.getUsername(),
                20, 20, 300, 50, "#ffffff");
        username.setFont(new Font("Impact Regular", Font.BOLD, 20));
        username.setForeground(Color.decode("#E1DE07"));

        JLabel name = this.createLabel("Name: " + user.getInformation().getName(),
                20, 60, 300, 50, "#ffffff");
        name.setFont(new Font("Impact Regular", Font.BOLD, 20));
        name.setForeground(Color.decode("#E1DE07"));

        JLabel country = this.createLabel("Country: " + user.getInformation().getCountry(),
                20, 100, 300, 50, "#ffffff");
        country.setFont(new Font("Impact Regular", Font.BOLD, 20));
        country.setForeground(Color.decode("#E1DE07"));

        JLabel age = this.createLabel("Age: " + user.getInformation().getAge(),
                20, 140, 300, 50, "#ffffff");
        age.setFont(new Font("Impact Regular", Font.BOLD, 20));
        age.setForeground(Color.decode("#E1DE07"));

        JLabel exp = this.createLabel("Exp: " + user.getExperience(),
                150, 140, 300, 50, "#ffffff");
        exp.setFont(new Font("Impact Regular", Font.BOLD, 20));
        exp.setForeground(Color.decode("#E1DE07"));

        JLabel gender = this.createLabel("Gender: " + user.getInformation().getGender(),
                20, 180, 300, 50, "#ffffff");
        gender.setFont(new Font("Impact Regular", Font.BOLD, 20));
        gender.setForeground(Color.decode("#E1DE07"));

        JButton update = this.createButton("Update", 200, 220, 100, 30,
                "#2F4F4F", "#000011", null);
        update.setFont(new Font("Impact Regular", Font.BOLD, 15));
        update.setForeground(Color.decode("#ffffff"));
        update.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        update.addActionListener(e -> {
            JFrame frame1 = this.createFrame(500, 300, "Update");
            frame1.getContentPane().setBackground(Color.decode("#000011"));

            JLabel name1 = this.createLabel("Name: " , 20, 20, 200, 50, "#ffffff");
            name1.setFont(new Font("Impact Regular", Font.BOLD, 20));
            name1.setForeground(Color.decode("#E1DE07"));

            JTextField nameField = new JTextField(user.getInformation().getName());
            nameField.setBounds(140, 30, 300, 30);
            nameField.setFont(new Font("Impact Regular", Font.BOLD, 15));
            nameField.setBackground(Color.DARK_GRAY);
            nameField.setForeground(Color.WHITE);
            nameField.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

            JLabel country1 = this.createLabel("Country: " , 20, 60, 200, 50, "#ffffff");
            country1.setFont(new Font("Impact Regular", Font.BOLD, 20));
            country1.setForeground(Color.decode("#E1DE07"));

            JTextField countryField = new JTextField(user.getInformation().getCountry());
            countryField.setBounds(140, 70, 300, 30);
            countryField.setFont(new Font("Impact Regular", Font.BOLD, 15));
            countryField.setBackground(Color.DARK_GRAY);
            countryField.setForeground(Color.WHITE);
            countryField.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

            JLabel age1 = this.createLabel("Age: " , 20, 100, 200, 50, "#ffffff");
            age1.setFont(new Font("Impact Regular", Font.BOLD, 20));
            age1.setForeground(Color.decode("#E1DE07"));

            JTextField ageField = new JTextField(user.getInformation().getAge().toString());
            ageField.setBounds(140, 110, 300, 30);
            ageField.setFont(new Font("Impact Regular", Font.BOLD, 15));
            ageField.setBackground(Color.DARK_GRAY);
            ageField.setForeground(Color.WHITE);
            ageField.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

            JLabel gender1 = this.createLabel("Gender: ", 20, 140, 200, 50, "#ffffff");
            gender1.setFont(new Font("Impact Regular", Font.BOLD, 20));
            gender1.setForeground(Color.decode("#E1DE07"));

            JTextField genderField = new JTextField(user.getInformation().getGender());
            genderField.setBounds(140, 150, 300, 30);
            genderField.setFont(new Font("Impact Regular", Font.BOLD, 15));
            genderField.setBackground(Color.DARK_GRAY);
            genderField.setForeground(Color.WHITE);
            genderField.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

            JButton update1 = this.createButton("Update", 200, 190, 100, 30,
                    "#2F4F4F", "#000011", null);
            update1.setFont(new Font("Impact Regular", Font.BOLD, 15));
            update1.setForeground(Color.decode("#ffffff"));
            update1.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

            update1.addActionListener(e1 -> {
                user.getInformation().setName(nameField.getText());
                user.getInformation().setCountry(countryField.getText());
                user.getInformation().setAge(Integer.parseInt(ageField.getText()));
                user.getInformation().setGender(genderField.getText());
                frame1.dispose();
                frame.repaint();
                this.settings(user);
            });


            frame1.add(name1);
            frame1.add(nameField);
            frame1.add(country1);
            frame1.add(countryField);
            frame1.add(age1);
            frame1.add(ageField);
            frame1.add(gender1);
            frame1.add(genderField);
            frame1.add(update1);
            frame1.setVisible(true);
        });


        frame.add(username);
        frame.add(name);
        frame.add(country);
        frame.add(age);
        frame.add(gender);
        frame.add(update);
        frame.add(exp);
        frame.setVisible(true);
    }
}
