package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.IMDB;
import org.example.enums.AccountType;
import org.example.enums.Genre;
import org.example.input.production.Rating;
import org.example.interfaces.InterfaceMethodes;
import org.example.interfaces.VideoPlayer;
import org.example.strategy.Review;
import org.example.user.Staff;
import org.example.user.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

@Getter
@Setter
@AllArgsConstructor
public class Movie extends Production {
    private String duration;
    private int releaseYear;
    private JButton addFavorite, removeFavorite, trailerButton;

    public Movie() {
        List<String> directors = new ArrayList<>();
        List<String> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        List<Rating> ratings = new ArrayList<>();
        double averageRating = 0;
        this.setAverageRating(averageRating);
        this.setDirectors(directors);
        this.setActors(actors);
        this.setGenres(genres);
        this.setRatings(ratings);
    }

    private void addRatingUI(User<?> user, JFrame frame) {
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame rateFrame = interfaceMethodes.createFrame(400, 400, "Rate");
        rateFrame.getContentPane().setBackground(Color.decode("#000011"));
        JLabel ratingLabel1 = interfaceMethodes.createLabel("Rating: ", 50, 50, 100, 30, "#E1DE07");
        ratingLabel1.setFont(new Font("Impact Regular", Font.BOLD, 20));
        rateFrame.add(ratingLabel1);

        JLabel ratingLabel2 = interfaceMethodes.createLabel("Comment: ", 50, 100, 150, 30, "#E1DE07");
        ratingLabel2.setFont(new Font("Impact Regular", Font.BOLD, 20));
        rateFrame.add(ratingLabel2);

        Integer[] ratings = {1, 2, 3, 4, 5, 6, 7, 8, 9 ,10};
        JComboBox<Integer> ratingBox = new JComboBox<>(ratings);
        ratingBox.setBounds(275, 50, 50, 30);
        ratingBox.setBackground(Color.decode("#000011"));
        ratingBox.setForeground(Color.decode("#E1DE07"));
        ratingBox.setFocusable(false);
        ratingBox.setFont(new Font("Impact Regular", Font.BOLD, 15));
        rateFrame.add(ratingBox);

        JTextArea comment = new JTextArea();
        comment.setBounds(50, 130, 300, 120);
        comment.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        comment.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JButton submit = interfaceMethodes.createButton("Submit", 150, 275, 100, 30, "#2F4F4F", "#E1DE07", null);
        submit.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        submit.addActionListener(e1 -> {
            if (comment.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Comment cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Rating rating = new Rating(user.getUsername(), (Integer)ratingBox.getSelectedItem(), comment.getText());
                this.getRatings().add(rating);
                JOptionPane.showMessageDialog(null, "Rating submitted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                rateFrame.dispose();
                frame.dispose();
            }
        });

        rateFrame.add(submit);
        rateFrame.add(comment);
        rateFrame.add(ratingBox);
        rateFrame.setVisible(true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void displayInfoUI(User<?> user) {
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(850, 500, "Movie");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel title = interfaceMethodes.createLabel(this.getTitle(), 20, 5, 500, 50, "#E1DE07");
        title.setFont(new Font("Impact Regular", Font.BOLD, 25));

        double averageRatingD = this.getAverageRating();
        String formattedRating = String.format("%.1f", averageRatingD);
        formattedRating = formattedRating.substring(0, formattedRating.indexOf('.') + 2);

        JLabel averageRating = interfaceMethodes.createLabel("Grade: " + formattedRating, 650, 5, 200, 50, "#ffffff");
        averageRating.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JLabel releaseYear = interfaceMethodes.createLabel("Release Year: " + this.getReleaseYear(), 20, 50, 200, 20, "#ffffff");
        releaseYear.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel duration = interfaceMethodes.createLabel("Duration: " + this.getDuration(), 20, 70, 200, 20, "#ffffff");
        duration.setFont(new Font("Impact Regular", Font.BOLD, 15));

        StringBuilder directorsString = new StringBuilder();
        for (String director : this.getDirectors()) {
            directorsString.append(director).append(", ");
        }
        JLabel directors = interfaceMethodes.createLabel("Directors: " + directorsString.substring(0, directorsString.length() - 2),
                20, 90, 400, 20, "#ffffff");
        directors.setFont(new Font("Impact Regular", Font.BOLD, 15));

        StringBuilder actorsString = new StringBuilder();
        for (String actor : this.getActors()) {
            actorsString.append(actor).append(", ");
        }
        JLabel actors = interfaceMethodes.createLabel("Actors: " + actorsString.substring(0, actorsString.length() - 2),
                20, 110, 400, 20, "#ffffff");
        actors.setFont(new Font("Impact Regular", Font.BOLD, 15));

        StringBuilder genresString = new StringBuilder();
        for (Genre genre : this.getGenres()) {
            genresString.append(genre).append(", ");
        }
        JLabel genres = interfaceMethodes.createLabel("Genres: " + genresString.substring(0, genresString.length()- 2),
                20, 130, 400, 20, "#ffffff");
        genres.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JTextArea plot = new JTextArea("Plot: " + this.getPlot());
        plot.setBounds(20, 150, 520, 60);
        plot.setBackground(Color.decode("#000011"));
        plot.setForeground(Color.decode("#ffffff"));
        plot.setFont(new Font("Impact Regular", Font.BOLD, 13));
        plot.setLineWrap(true);
        plot.setWrapStyleWord(true);
        plot.setEditable(false);


        JLabel ratingLabel = interfaceMethodes.createLabel("Ratings: ", 20, 220, 600, 20, "#ffffff");
        ratingLabel.setFont(new Font("Impact Regular", Font.BOLD, 18));

        JPanel ratingsPanel = new JPanel();
        ratingsPanel.setLayout(new BoxLayout(ratingsPanel, BoxLayout.Y_AXIS));
        ratingsPanel.setBackground(Color.decode("#000011"));

        int y = 240;
        if (this.getRatings().isEmpty()) {
            JLabel ratingLabel1 = interfaceMethodes.createLabel("Be the first!", 20, y, 600, 20, "#E1DE07");
            ratingLabel1.setFont(new Font("Impact Regular", Font.BOLD, 15));
            ratingsPanel.add(ratingLabel1);
        } else {
            for (Rating rating : this.getRatings()) {
                JLabel ratingLabel1 = interfaceMethodes.createLabel(rating.getRating() + " by " + rating.getUsername(), 20, y, 600, 20, "#E1DE07");
                ratingLabel1.setFont(new Font("Impact Regular", Font.BOLD, 15));
                ratingsPanel.add(ratingLabel1);
                y += 20;

                JLabel ratingLabel2 = interfaceMethodes.createLabel(rating.getComment(), 20, y, 600, 20, "#ffffff");
                ratingLabel2.setFont(new Font("Impact Regular", Font.BOLD, 15));
                ratingsPanel.add(ratingLabel2);
                y += 25;
            }
        }

        JScrollPane scrollPane = new JScrollPane(ratingsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20, 240, 500, 150);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.setBorder(new LineBorder(Color.decode("#000011"), 1));

        frame.add(scrollPane);

        removeFavorite = interfaceMethodes.createButton("Rm from favorites", 600, 400, 150, 30, "#2F4F4F", "#F53F20", null);
        removeFavorite.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        addFavorite = interfaceMethodes.createButton("Add to favorites", 600, 400, 150, 30, "#2F4F4F", "#E1DE07", null);
        addFavorite.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

        removeFavorite.addActionListener(e -> {
            SortedSet<Object> favorites = (SortedSet<Object>) user.getFavorites();
            favorites.remove(this);
            JOptionPane.showMessageDialog(null, "Movie removed from favorites!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.remove(removeFavorite);
            frame.add(addFavorite);
            frame.repaint();
        });
        addFavorite.addActionListener(e -> {
            SortedSet<Object> favorites = (SortedSet<Object>) user.getFavorites();
            favorites.add(this);
            JOptionPane.showMessageDialog(null, "Movie added to favorites!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.remove(addFavorite);
            frame.add(removeFavorite);
            frame.repaint();
        });

        if(user.getFavorites().contains(this)){
            frame.add(removeFavorite);
        }else{
            frame.add(addFavorite);
        }

        if (user.getUserType().equals(AccountType.Regular)){
            for (Rating rating : this.getRatings()) {
                if (rating.getUsername().equals(user.getUsername())) {
                    JButton removeRating = interfaceMethodes.createButton("Rm rating", 490, 400, 100, 30, "#2F4F4F", "#F53F20", null);
                    removeRating.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
                    removeRating.addActionListener(e -> {
                        this.getRatings().remove(rating);
                        this.addRatingUI(user, frame);
                    });
                    frame.add(removeRating);
                }
            }
            JButton rate = interfaceMethodes.createButton("Rate", 490, 400, 100, 30, "#2F4F4F", "#E1DE07", null);
            rate.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
            rate.addActionListener(e -> {
                this.addRatingUI(user, frame);
                Review review = new Review();
                user.setExperience(user.getExperience() + review.calculateExperience());
                try {
                    for (User<?> user1 : IMDB.getInstance().getUsers()){
                        if (user1.getUserType().equals(AccountType.Admin) || user1.getUserType().equals(AccountType.Contributor)){
                            if (((Staff<?>) user1).getContributions().contains(this)){
                                IMDB.getInstance().getNotificationSubject().notifyObservers(user1, "The movie " + this.getTitle() + " what you added has a new rating!");
                            }
                        }
                        if (user1.getFavorites().contains(this)){
                            IMDB.getInstance().getNotificationSubject().notifyObservers(user1, "The movie " + this.getTitle() + " what is in your favorites has a new rating!");
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            frame.add(rate);
        }

        ImageIcon image = this.getImage();
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBounds(550, 50, 300, 300);

        trailerButton = interfaceMethodes.createButton("Trailer", 600, 360, 150, 30, "#2F4F4F", "#E1DE07", null);
        trailerButton.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        if (this.getTrailer() != null) {
            VideoPlayer videoPlayer = new VideoPlayer(this.getTrailer());
            trailerButton.addActionListener(e -> videoPlayer.play());
            frame.add(trailerButton);
        }
        frame.add(imageLabel);
        frame.add(title);
        frame.add(averageRating);
        frame.add(releaseYear);
        frame.add(duration);
        frame.add(directors);
        frame.add(actors);
        frame.add(genres);
        frame.add(plot);
        frame.add(ratingLabel);
        frame.setVisible(true);
    }

    public void updateMovieUI(){
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(800, 650, "Update Movie");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel titleLabel = interfaceMethodes.createLabel("Title: ", 50, 50, 100, 30, "#E1DE07");
        titleLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JLabel releaseLabel = interfaceMethodes.createLabel("Release Year: ", 420, 50, 150, 30, "#E1DE07");
        releaseLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JTextField title = new JTextField(this.getTitle());
        title.setBounds(150, 50, 250, 30);
        title.setFont(new Font("Impact Regular", Font.BOLD, 15));
        title.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        title.setBackground(Color.DARK_GRAY);
        title.setForeground(Color.WHITE);

        JTextField release = new JTextField(this.getReleaseYear() + "");
        release.setBounds(575, 50, 150, 30);
        release.setFont(new Font("Impact Regular", Font.BOLD, 15));
        release.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        release.setBackground(Color.DARK_GRAY);
        release.setForeground(Color.WHITE);

        JLabel plotLabel = interfaceMethodes.createLabel("Plot: ", 50, 100, 100, 30, "#E1DE07");
        plotLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JTextArea plot = new JTextArea(this.getPlot());
        plot.setBounds(150, 100, 500, 100);
        plot.setFont(new Font("Impact Regular", Font.BOLD, 13));
        plot.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        plot.setBackground(Color.DARK_GRAY);
        plot.setForeground(Color.WHITE);
        plot.setLineWrap(true);
        plot.setWrapStyleWord(true);

        JLabel durationLabel = interfaceMethodes.createLabel("Duration: ", 50, 220, 100, 30, "#E1DE07");
        durationLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JTextField duration = new JTextField(this.getDuration());
        duration.setBounds(150, 220, 250, 30);
        duration.setFont(new Font("Impact Regular", Font.BOLD, 15));
        duration.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        duration.setBackground(Color.DARK_GRAY);
        duration.setForeground(Color.WHITE);


        // Add/Update/Rename Directors
        JLabel directorsLabel = interfaceMethodes.createLabel("Directors: ", 125, 250, 100, 30, "#E1DE07");
        directorsLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        DefaultListModel<String> directorsModel = new DefaultListModel<>();
        for (String director : this.getDirectors()) {
            directorsModel.addElement(director);
        }

        JList<String> directors = new JList<>(directorsModel);
        directors.setBounds(70, 290, 200, 100);
        directors.setFont(new Font("Impact Regular", Font.BOLD, 15));
        directors.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        directors.setBackground(Color.DARK_GRAY);
        directors.setFocusable(false);
        directors.setForeground(Color.WHITE);

        JScrollPane directorsPane = new JScrollPane(directors);
        directorsPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        directorsPane.setFont(new Font("Impact Regular", Font.BOLD, 15));
        directorsPane.setBounds(70, 290, 200, 250);
        directorsPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        directorsPane.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

        directors.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                JFrame frame1 = interfaceMethodes.createFrame(400, 200, "Update Director");
                frame1.getContentPane().setBackground(Color.decode("#000011"));

                JTextField director = new JTextField(directors.getSelectedValue());
                director.setBounds(50, 50, 300, 30);
                director.setFont(new Font("Impact Regular", Font.BOLD, 15));
                director.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
                director.setBackground(Color.DARK_GRAY);
                director.setForeground(Color.WHITE);

                JButton update = interfaceMethodes.createButton("Update", 70, 100, 100, 30, "#2F4F4F", "#E1DE07", null);
                update.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
                update.addActionListener(e1 -> {
                    if (director.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Director cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        directorsModel.setElementAt(director.getText(), directors.getSelectedIndex());
                        frame.repaint();
                        frame1.dispose();
                    }
                });
                JButton delete = interfaceMethodes.createButton("Delete", 225, 100, 100, 30, "#2F4F4F", "#F53F20", null);
                delete.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
                delete.addActionListener(e1 -> {
                    directorsModel.removeElementAt(directors.getSelectedIndex());
                    frame.repaint();
                    frame1.dispose();
                });

                frame1.add(director);
                frame1.add(update);
                frame1.add(delete);
                frame1.setVisible(true);
            }
        });

        JButton addDirector = interfaceMethodes.createButton("+", 280, 290, 30, 30, "#2F4F4F", "#E1DE07", null);
        addDirector.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));

        addDirector.addActionListener(e -> {
            JFrame frame1 = interfaceMethodes.createFrame(400, 200, "Add Director");
            frame1.getContentPane().setBackground(Color.decode("#000011"));

            JTextField director = new JTextField();
            director.setBounds(50, 50, 300, 30);
            director.setFont(new Font("Impact Regular", Font.BOLD, 15));
            director.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
            director.setBackground(Color.DARK_GRAY);
            director.setForeground(Color.WHITE);

            JButton add = interfaceMethodes.createButton("Add", 150, 100, 100, 30, "#2F4F4F", "#E1DE07", null);
            add.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
            add.addActionListener(e1 -> {
                if (director.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Director cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    directorsModel.addElement(director.getText());
                    frame.repaint();
                    frame1.dispose();
                }
            });

            frame1.add(director);
            frame1.add(add);
            frame1.setVisible(true);
        });


        // Add/Update/Rename Actors
        JLabel actorsLabel = interfaceMethodes.createLabel("Actors: ", 400, 250, 100, 30, "#E1DE07");
        actorsLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        DefaultListModel<String> actorsModel = new DefaultListModel<>();
        for (String actor : this.getActors()) {
            actorsModel.addElement(actor);
        }

        JList<String> actors = new JList<>(actorsModel);
        actors.setBounds(345, 290, 200, 100);
        actors.setFont(new Font("Impact Regular", Font.BOLD, 15));
        actors.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        actors.setBackground(Color.DARK_GRAY);
        actors.setFocusable(false);
        actors.setForeground(Color.WHITE);

        JScrollPane actorsPane = new JScrollPane(actors);
        actorsPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        actorsPane.setFont(new Font("Impact Regular", Font.BOLD, 15));
        actorsPane.setBounds(345, 290, 200, 250);
        actorsPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        actorsPane.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

        actors.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                JFrame frame1 = interfaceMethodes.createFrame(400, 200, "Update Actor");
                frame1.getContentPane().setBackground(Color.decode("#000011"));

                JTextField actor = new JTextField(actors.getSelectedValue());
                actor.setBounds(50, 50, 300, 30);
                actor.setFont(new Font("Impact Regular", Font.BOLD, 15));
                actor.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
                actor.setBackground(Color.DARK_GRAY);
                actor.setForeground(Color.WHITE);

                JButton update = interfaceMethodes.createButton("Update", 70, 100, 100, 30, "#2F4F4F", "#E1DE07", null);
                update.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
                update.addActionListener(e1 -> {
                    if (actor.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Actor cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        actorsModel.setElementAt(actor.getText(), actors.getSelectedIndex());
                        frame.repaint();
                        frame1.dispose();
                    }
                });
                JButton delete = interfaceMethodes.createButton("Delete", 225, 100, 100, 30, "#2F4F4F", "#F53F20", null);
                delete.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
                delete.addActionListener(e1 -> {
                    actorsModel.removeElementAt(actors.getSelectedIndex());
                    frame.repaint();
                    frame1.dispose();
                });

                frame1.add(actor);
                frame1.add(update);
                frame1.add(delete);
                frame1.setVisible(true);
            }
        });

        JButton addActor = interfaceMethodes.createButton("+", 560, 290, 30, 30, "#2F4F4F", "#E1DE07", null);
        addActor.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));
        addActor.addActionListener(e -> {
            JFrame frame1 = interfaceMethodes.createFrame(400, 200, "Add Actor");
            frame1.getContentPane().setBackground(Color.decode("#000011"));

            JTextField actor = new JTextField();
            actor.setBounds(50, 50, 300, 30);
            actor.setFont(new Font("Impact Regular", Font.BOLD, 15));
            actor.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
            actor.setBackground(Color.DARK_GRAY);
            actor.setForeground(Color.WHITE);

            JButton add = interfaceMethodes.createButton("Add", 150, 100, 100, 30, "#2F4F4F", "#E1DE07", null);
            add.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
            add.addActionListener(e1 -> {
                if (actor.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Actor cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    actorsModel.addElement(actor.getText());
                    frame.repaint();
                    frame1.dispose();
                }
            });

            frame1.add(actor);
            frame1.add(add);
            frame1.setVisible(true);
        });


        // Add/Update Genres
        int y = 260;
        for (Genre genre : Genre.values()) {
            JCheckBox genreBox = new JCheckBox(genre.toString());
            genreBox.setBounds(630, y, 150, 20);
            genreBox.setForeground(Color.WHITE);
            genreBox.setFont(new Font("Impact Regular", Font.BOLD, 13));
            genreBox.setFocusable(false);
            genreBox.setBackground(Color.decode("#000011"));
            if (this.getGenres().contains(genre)) {
                genreBox.setSelected(true);
            }
            frame.add(genreBox);
            y += 20;
        }

        JButton update = interfaceMethodes.createButton("Update", 300, 550, 100, 30, "#2F4F4F", "#E1DE07", null);
        update.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

        update.addActionListener(e -> {
            if (title.getText().isEmpty() || release.getText().isEmpty() || plot.getText().isEmpty() || duration.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                this.setTitle(title.getText());
                try {
                    this.setReleaseYear(Integer.parseInt(release.getText()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Release year must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                this.setPlot(plot.getText());
                this.setDuration(duration.getText());
                this.getDirectors().clear();
                for (int i = 0; i < directorsModel.size(); i++) {
                    this.getDirectors().add(directorsModel.get(i));
                }
                this.getActors().clear();
                for (int i = 0; i < actorsModel.size(); i++) {
                    this.getActors().add(actorsModel.get(i));
                }
                this.getGenres().clear();
                for (Component component : frame.getContentPane().getComponents()) {
                    if (component instanceof JCheckBox) {
                        if (((JCheckBox) component).isSelected()) {
                            this.getGenres().add(Genre.valueOf(((JCheckBox) component).getText()));
                        }
                    }
                }
                frame.dispose();
            }
        });

        frame.add(titleLabel);
        frame.add(title);
        frame.add(releaseLabel);
        frame.add(release);
        frame.add(plotLabel);
        frame.add(plot);
        frame.add(durationLabel);
        frame.add(duration);
        frame.add(directorsLabel);
        frame.add(directorsPane);
        frame.add(addDirector);
        frame.add(actorsLabel);
        frame.add(actorsPane);
        frame.add(addActor);
        frame.add(update);
        frame.setVisible(true);
    }
    @Override
    public void displayInfo() {
        System.out.println("Type: " + this.getType());
        System.out.println("Title: " + this.getTitle());
        System.out.println("Average Rating: " + this.getAverageRating());
        System.out.println("Release Year: " + this.getReleaseYear());
        System.out.println("Duration: " + this.getDuration());
        System.out.print("Directors: ");
        for (int i = 0; i < this.getDirectors().size(); i++) {
            System.out.print(this.getDirectors().get(i) + " ");
        }
        System.out.println();
        System.out.println("Actors: ");
        for (int i = 0; i < this.getActors().size(); i++) {
            System.out.print(this.getActors().get(i) + " ");
        }
        System.out.println();
        System.out.println("Genres: ");
        for (int i = 0; i < this.getGenres().size(); i++) {
            System.out.print(this.getGenres().get(i) + " ");
        }
        System.out.println();
        System.out.println("Plot: " + this.getPlot());
        System.out.println("Ratings: ");
        for (int i = 0; i < this.getRatings().size(); i++) {
            System.out.println(this.getRatings().get(i).getRating() + " by " + this.getRatings().get(i).getUsername());
            System.out.println(this.getRatings().get(i).getComment());
            System.out.println();
        }
        System.out.print("-".repeat(64));
        System.out.println();
    }

}
