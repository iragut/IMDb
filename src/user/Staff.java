package org.example.user;

import lombok.Getter;
import lombok.Setter;
import org.example.IMDB;
import org.example.entities.*;
import org.example.enums.AccountType;
import org.example.enums.Genre;
import org.example.input.account.AccountInput;
import org.example.interfaces.InterfaceMethodes;
import org.example.request.Request;
import org.example.request.RequestHolder;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import java.util.*;

@Getter
@Setter
public abstract class Staff<T extends Comparable<T>> extends User<T> implements StaffInterface {
    private List<Request> requests;
    private SortedSet<T> contributions;

    public void addContribution(Object objects) {
        AccountInput accountInput = new AccountInput();
        contributions = accountInput.addContribution(contributions, objects);
    }

    @SuppressWarnings("unchecked")
    public void removeContributionsSystem(Object objects, IMDB imdb) {
        for (User<?> user : imdb.getUsers()) {
            if (!user.getUserType().equals(AccountType.Regular)) {
                ((Staff<T>) user).getContributions().remove(objects);
            }
        }
    }

    public void removeFavoritesSystem(Object objects, IMDB imdb) {
        for (User<?> user : imdb.getUsers()) {
            user.getFavorites().remove(objects);
        }
    }


    public void manageRequest(List<Request> requests, IMDB imdb){
        for (Request request : requests){
            request.printRequest();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the request you want to solve: ");
        while (true) {
            try {
                int number = scanner.nextInt();
                scanner.nextLine();
                if (number > 0 && number <= requests.size()) {
                    System.out.println("What you want to do with the request: ");
                    System.out.println("1) Solve it");
                    System.out.println("2) Reject it");
                    System.out.println("3) Back");
                    int number2 = scanner.nextInt();
                    scanner.nextLine();
                    if (number2 == 1) {
                        Request request = requests.get(number - 1);
                        User<?> user = imdb.findUser(request.getUsername());
                        requests.remove(request);
                        imdb.getNotificationSubject().notifyObservers(user, "Your request has been solved");
                        return;
                    } else if (number2 == 2) {
                        Request request = requests.get(number - 1);
                        User<?> user = imdb.findUser(request.getUsername());
                        requests.remove(request);
                        imdb.getNotificationSubject().notifyObservers(user, "Your request has been rejected");
                        return;
                    } else if (number2 == 3) {
                        return;
                    } else {
                        System.out.println("Please select a valid command");
                    }
                } else {
                    System.out.println("Please select a request from the list");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input or failed to parse input");
                scanner.nextLine();
                return;
            }
        }
    }

    private Movie createMovie(){
        Movie movie = new Movie();
        double averageRating = 0.0;
        movie.setAverageRating(averageRating);
        movie.setType("Movie");
        Scanner scanner = new Scanner(System.in);
        int numberOfEntities;
        System.out.println("Enter the title of the movie: ");
        movie.setTitle(scanner.nextLine());
        System.out.println("Enter the plot of the movie: ");
        movie.setPlot(scanner.nextLine());
        System.out.println("Enter the release year of the movie: ");
        try {
            movie.setReleaseYear(scanner.nextInt());
            scanner.nextLine();
            System.out.println("Enter the duration of the movie: ");
            movie.setDuration(scanner.nextLine());
            System.out.println("Enter the number of directors of the movie: ");
            numberOfEntities = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < numberOfEntities; i++) {
                System.out.println("Enter director " + (i + 1) + ": ");
                movie.getDirectors().add(scanner.nextLine());
            }
            System.out.println("Enter the number of actors of the movie: ");
            numberOfEntities = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < numberOfEntities; i++) {
                System.out.println("Enter actor " + (i + 1) + ": ");
                movie.getActors().add(scanner.nextLine());
            }
            System.out.println("Enter how many genres has the movie: ");
            numberOfEntities = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < numberOfEntities; i++) {
                System.out.println("Enter genre " + (i + 1) + ": ");
                movie.getGenres().add(movie.stringToGenre(scanner.nextLine()));
            }
        } catch (Exception e) {
            System.out.println("Invalid input or failed to parse input");
            System.out.println("Returning to main menu");
            scanner.nextLine();
            return null;
        }
        System.out.println("The movie has been created successfully");
        return movie;
    }

    private Series createSeries(){
        Series series = new Series();
        double averageRating = 0.0;
        series.setAverageRating(averageRating);
        series.setType("Series");
        Scanner scanner = new Scanner(System.in);
        int numberOfEntities;
        System.out.println("Enter the title of the series: ");
        series.setTitle(scanner.nextLine());
        System.out.println("Enter the plot of the series: ");
        series.setPlot(scanner.nextLine());
        System.out.println("Enter the release year of the series: ");
        try {
            series.setReleaseYear(scanner.nextInt());
            scanner.nextLine();
            System.out.println("Enter the number of seasons of the series: ");
            series.setNumSeasons(scanner.nextInt());
            scanner.nextLine();
            for (int i = 0; i < series.getNumSeasons(); i++) {
                System.out.println("Enter the number of episodes of season " + (i + 1) + ": ");
                numberOfEntities = scanner.nextInt();
                scanner.nextLine();
                List<Episode> episodes = new ArrayList<>();
                for (int j = 0; j < numberOfEntities; j++) {
                    System.out.println("Enter the name of episode " + (j + 1) + ": ");
                    String episodeName = scanner.nextLine();
                    Episode episode = new Episode();
                    series.getSeasons().putIfAbsent("Season " + (i + 1), episodes);
                    System.out.println("Enter the duration of episode " + (j + 1) + ": ");
                    episode.setDuration(scanner.nextLine());
                    episode.setEpisodeName(episodeName);
                    series.getSeasons().get("Season " + (i + 1)).add(episode);
                }
            }
            System.out.println("Enter the number of directors of the series: ");
            numberOfEntities = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < numberOfEntities; i++) {
                System.out.println("Enter director" + (i + 1) + ": ");
                series.getDirectors().add(scanner.nextLine());
            }
            System.out.println("Enter the number of actors of the series: ");
            numberOfEntities = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < numberOfEntities; i++) {
                System.out.println("Enter actor" + (i + 1) + ": ");
                series.getActors().add(scanner.nextLine());
            }
            System.out.println("Enter how many genres has the series: ");
            numberOfEntities = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < numberOfEntities; i++) {
                System.out.println("Enter genre" + (i + 1) + ": ");
                series.getGenres().add(series.stringToGenre(scanner.nextLine()));
            }
        } catch (Exception e) {
            System.out.println("Invalid input or failed to parse input");
            System.out.println("Returning to main menu");
            scanner.nextLine();
            return null;
        }
        System.out.println("The series has been created successfully");
        return series;
    }

    public Actor createActor(){
        Actor actor = new Actor();
        Scanner scanner = new Scanner(System.in);
        int numberOfEntities;
        System.out.println("Enter the name of the actor: ");
        actor.setName(scanner.nextLine());
        System.out.println("Enter the biography of the actor: ");
        actor.setBiography(scanner.nextLine());
        try {
            System.out.println("Enter the number of performance the actor has played in: ");
            numberOfEntities = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < numberOfEntities; i++) {
                Performance performance = new Performance();
                System.out.println("Enter the name of production " + (i + 1) + ": ");
                performance.setTitle((scanner.nextLine()));
                System.out.println("Enter the type of production " + (i + 1) + ": ");
                performance.setType(scanner.nextLine());
                actor.getPerformances().add(performance);
            }
        } catch (Exception e) {
            System.out.println("Invalid input or failed to parse input");
            System.out.println("Returning to main menu");
            scanner.nextLine();
            return null;
        }
        System.out.println("The actor has been created successfully");
        return actor;
    }

    public Production createProduction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose that production you want to create: ");
        System.out.println("1) Movie");
        System.out.println("2) Series");
        System.out.println("3) Back");
        while (true) {
            try {
                int number = scanner.nextInt();
                if (number == 1) {
                    return this.createMovie();
                } else if (number == 2) {
                    return this.createSeries();
                } else if (number == 3) {
                    break;
                } else {
                    System.out.println("Please select a valid command");
                }
            } catch(InputMismatchException e) {
                System.out.println("Please select a valid command");
                scanner.nextLine();
            }
        }
        return null;
    }
    @Override
    public void addProductionSystem(Production p, IMDB imdb) {
        imdb.getProductions().add(p);
    }

    @Override
    public void addActorSystem(Actor a, IMDB imdb) {
        imdb.getActors().add(a);
    }

    @Override
    public void removeProductionSystem(Production production, IMDB imdb) {
        imdb.getProductions().remove(production);
        System.out.println("Production has been removed successfully");
    }

    @Override
    public void removeActorSystem(Actor actor, IMDB imdb) {
        imdb.getActors().remove(actor);
        System.out.println("Actor has been removed successfully");
    }

    public void updateMovie(Movie movie){
        Scanner scanner = new Scanner(System.in);
        int numberOfEntities;
        System.out.println("Choose what you want to update: ");
        while (true){
            try{
                System.out.println("1) Title");
                System.out.println("2) Plot");
                System.out.println("3) Release year");
                System.out.println("4) Duration");
                System.out.println("5) Directors");
                System.out.println("6) Actors");
                System.out.println("7) Genres");
                System.out.println("8) Back");
                int number = scanner.nextInt();
                scanner.nextLine();
                if (number == 1) {
                    System.out.println("Enter the new title of the movie: ");
                    movie.setTitle(scanner.nextLine());
                    System.out.println("The title has been updated successfully");
                } else if (number == 2) {
                    System.out.println("Enter the new plot of the movie: ");
                    movie.setPlot(scanner.nextLine());
                    System.out.println("The plot has been updated successfully");
                } else if (number == 3) {
                    System.out.println("Enter the new release year of the movie: ");
                    movie.setReleaseYear(scanner.nextInt());
                    System.out.println("The release year has been updated successfully");
                } else if (number == 4) {
                    System.out.println("Enter the new duration of the movie: ");
                    movie.setDuration(scanner.nextLine());
                    System.out.println("The duration has been updated successfully");
                } else if (number == 5) {
                    System.out.println("Enter the new number of directors of the movie: ");
                    numberOfEntities = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < numberOfEntities; i++) {
                        System.out.println("Enter director " + (i + 1) + ": ");
                        movie.getDirectors().add(scanner.nextLine());
                    }
                    System.out.println("The directors have been updated successfully");
                } else if (number == 6) {
                    System.out.println("Enter the new number of actors of the movie: ");
                    numberOfEntities = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < numberOfEntities; i++) {
                        System.out.println("Enter actor " + (i + 1) + ": ");
                        movie.getActors().add(scanner.nextLine());
                    }
                    System.out.println("The actors have been updated successfully");
                } else if (number == 7) {
                    System.out.println("Enter the new number of genres of the movie: ");
                    numberOfEntities = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < numberOfEntities; i++) {
                        System.out.println("Enter genre " + (i + 1) + ": ");
                        movie.getGenres().add(movie.stringToGenre(scanner.nextLine()));
                    }
                    System.out.println("The genres have been updated successfully");
                } else if (number == 8) {
                    return;
                } else {
                    System.out.println("Please select a valid command");
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid input or failed to parse input");
                scanner.nextLine();
                return;
            }
        }
    }
    public void updateSeries(Series series){
        Scanner scanner = new Scanner(System.in);
        int numberOfEntities;
        System.out.println("Choose what you want to update: ");
        while (true) {
            try {
                System.out.println("1) Title");
                System.out.println("2) Plot");
                System.out.println("3) Release year");
                System.out.println("4) Number of seasons");
                System.out.println("5) Directors");
                System.out.println("6) Actors");
                System.out.println("7) Genres");
                System.out.println("8) Back");
                int number = scanner.nextInt();
                scanner.nextLine();
                if (number == 1) {
                    System.out.println("Enter the new title of the series: ");
                    series.setTitle(scanner.nextLine());
                    System.out.println("The title has been updated successfully");
                } else if (number == 2) {
                    System.out.println("Enter the new plot of the series: ");
                    series.setPlot(scanner.nextLine());
                    System.out.println("The plot has been updated successfully");
                } else if (number == 3) {
                    System.out.println("Enter the new release year of the series: ");
                    series.setReleaseYear(scanner.nextInt());
                    System.out.println("The release year has been updated successfully");
                } else if (number == 4) {
                    System.out.println("Enter the new number of seasons of the series: ");
                    series.setNumSeasons(scanner.nextInt());
                    System.out.println("The number of seasons has been updated successfully");
                } else if (number == 5) {
                    System.out.println("Enter the new number of directors of the series: ");
                    numberOfEntities = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < numberOfEntities; i++) {
                        System.out.println("Enter director" + (i + 1) + ": ");
                        series.getDirectors().add(scanner.nextLine());
                    }
                    System.out.println("The directors have been updated successfully");
                } else if (number == 6) {
                    System.out.println("Enter the new number of actors of the series: ");
                    numberOfEntities = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < numberOfEntities; i++) {
                        System.out.println("Enter actor" + (i + 1) + ": ");
                        series.getActors().add(scanner.nextLine());
                    }
                    System.out.println("The actors have been updated successfully");
                } else if (number == 7) {
                    System.out.println("Enter the new number of genres of the series: ");
                    numberOfEntities = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < numberOfEntities; i++) {
                        System.out.println("Enter genre" + (i + 1) + ": ");
                        series.getGenres().add(series.stringToGenre(scanner.nextLine()));
                    }
                    System.out.println("The genres have been updated successfully");
                } else if (number == 8) {
                    return;
                } else {
                    System.out.println("Please select a valid command");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input or failed to parse input");
                scanner.nextLine();
                return;
            }
        }
    }

    @Override
    public void updateProduction(IMDB imdb) {
        Production production = imdb.findProduction();
        if (production == null) {
            System.out.println("Production not found!");
        } else if (!contributions.contains(production)) {
            System.out.println("You are not allowed to update this production!");
        } else if (production.getType().equals("Movie")) {
            this.updateMovie(((Movie) production));
        } else if (production.getType().equals("Series")) {
            this.updateSeries(((Series) production));
        }
    }

    @Override
    public void updateActor(IMDB imdb) {
        Actor actor = imdb.findActor();
        if (actor == null){
            System.out.println("Actor not found!");
        } else if (!contributions.contains(actor)){
            System.out.println("You are not allowed to update this actor!");
        } else {
            Scanner scanner = new Scanner(System.in);
            int numberOfEntities;
            System.out.println("Choose what you want to update: ");
            while (true) {
                try {
                    System.out.println("1) Name");
                    System.out.println("2) Biography");
                    System.out.println("3) Performances");
                    System.out.println("4) Back");
                    int number = scanner.nextInt();
                    scanner.nextLine();
                    if (number == 1) {
                        System.out.println("Enter the new name of the actor: ");
                        actor.setName(scanner.nextLine());
                        System.out.println("The name has been updated successfully");
                    } else if (number == 2) {
                        System.out.println("Enter the new biography of the actor: ");
                        actor.setBiography(scanner.nextLine());
                        System.out.println("The biography has been updated successfully");
                    } else if (number == 3) {
                        System.out.println("Enter the new number of performances of the actor: ");
                        numberOfEntities = scanner.nextInt();
                        scanner.nextLine();
                        for (int i = 0; i < numberOfEntities; i++) {
                            Performance performance = new Performance();
                            System.out.println("Enter the name of production " + (i + 1) + ": ");
                            performance.setTitle((scanner.nextLine()));
                            System.out.println("Enter the type of production " + (i + 1) + ": ");
                            performance.setType(scanner.nextLine());
                            actor.getPerformances().add(performance);
                        }
                        System.out.println("The performances have been updated successfully");
                    } else if (number == 4) {
                        return;
                    } else {
                        System.out.println("Please select a valid command");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input or failed to parse input");
                    scanner.nextLine();
                    return;
                }
            }

        }
    }

    public void removeProductionUI(IMDB imdb){
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(600, 300, "Delete Production/Actor");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        String[] productions = new String[getContributions().size()];
        for (int i = 0; i < getContributions().size(); i++) {
            if (getContributions().toArray()[i] instanceof Actor) {
                String actor = "Actor: " + ((Actor) getContributions().toArray()[i]).getName();
                productions[i] = actor;
            } else if (getContributions().toArray()[i] instanceof Production) {
                String prod = "Production: " + ((Production) getContributions().toArray()[i]).getTitle();
                productions[i] = prod;
            }
        }

        JLabel title = interfaceMethodes.createLabel("Choose the production or actor you want to delete: ", 70, 5, 500, 50, "#E1DE07");
        title.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JComboBox<String> productionsList = new JComboBox<>(productions);
        productionsList.setBounds(150, 50, 300, 30);
        productionsList.setFont(new Font("Impact Regular", Font.BOLD, 15));
        productionsList.setFocusable(false);

        JButton delete = interfaceMethodes.createButton("Delete", 250, 100, 100, 30, "#2f2f2f", "#ffffff", null);
        delete.setBorder(BorderFactory.createLineBorder(Color.decode("#48E5FB"), 1));
        delete.setFont(new Font("Impact Regular", Font.BOLD, 20));
        delete.addActionListener(e -> {
            if (productionsList.getSelectedItem() != null) {
                String production = productionsList.getSelectedItem().toString();
                if (production.contains("Actor")) {
                    String actor = production.substring(7);
                    for (Actor actor1 : imdb.getActors()) {
                        if (actor1.getName().equals(actor)) {
                            this.removeActorSystem(actor1, imdb);
                            this.removeContributionsSystem(actor1, imdb);
                            this.removeFavoritesSystem(actor1, imdb);
                            break;
                        }
                    }
                } else if (production.contains("Production")) {
                    String prod = production.substring(12);
                    for (Production production1 : imdb.getProductions()) {
                        if (production1.getTitle().equals(prod)) {
                            this.removeProductionSystem(production1, imdb);
                            this.removeContributionsSystem(production1, imdb);
                            this.removeFavoritesSystem(production1, imdb);
                            break;
                        }
                    }
                }
                frame.dispose();
            }
        });

        frame.add(title);
        frame.add(productionsList);
        frame.add(delete);
        frame.setVisible(true);
    }

    public void createMovieUI(String name, IMDB imdb){
        Movie movie = new Movie();
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(800, 650, "Update Movie");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel titleLabel = interfaceMethodes.createLabel("Title: ", 50, 50, 100, 30, "#E1DE07");
        titleLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JLabel releaseLabel = interfaceMethodes.createLabel("Release Year: ", 420, 50, 150, 30, "#E1DE07");
        releaseLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JTextField title = new JTextField(name);
        title.setBounds(150, 50, 250, 30);
        title.setFont(new Font("Impact Regular", Font.BOLD, 15));
        title.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        title.setBackground(Color.DARK_GRAY);
        title.setForeground(Color.WHITE);

        JTextField release = new JTextField();
        release.setBounds(575, 50, 150, 30);
        release.setFont(new Font("Impact Regular", Font.BOLD, 15));
        release.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        release.setBackground(Color.DARK_GRAY);
        release.setForeground(Color.WHITE);

        JLabel plotLabel = interfaceMethodes.createLabel("Plot: ", 50, 100, 100, 30, "#E1DE07");
        plotLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JTextArea plot = new JTextArea();
        plot.setBounds(150, 100, 500, 100);
        plot.setFont(new Font("Impact Regular", Font.BOLD, 13));
        plot.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        plot.setBackground(Color.DARK_GRAY);
        plot.setForeground(Color.WHITE);
        plot.setLineWrap(true);
        plot.setWrapStyleWord(true);

        JLabel durationLabel = interfaceMethodes.createLabel("Duration: ", 50, 220, 100, 30, "#E1DE07");
        durationLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JTextField duration = new JTextField();
        duration.setBounds(150, 220, 250, 30);
        duration.setFont(new Font("Impact Regular", Font.BOLD, 15));
        duration.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        duration.setBackground(Color.DARK_GRAY);
        duration.setForeground(Color.WHITE);


        // Add Directors
        JLabel directorsLabel = interfaceMethodes.createLabel("Directors: ", 125, 250, 100, 30, "#E1DE07");
        directorsLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        DefaultListModel<String> directorsModel = new DefaultListModel<>();

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


        // Add Actors
        JLabel actorsLabel = interfaceMethodes.createLabel("Actors: ", 400, 250, 100, 30, "#E1DE07");
        actorsLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        DefaultListModel<String> actorsModel = new DefaultListModel<>();

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


        // Add Genres
        int y = 260;
        for (Genre genre : Genre.values()) {
            JCheckBox genreBox = new JCheckBox(genre.toString());
            genreBox.setBounds(630, y, 150, 20);
            genreBox.setForeground(Color.WHITE);
            genreBox.setFont(new Font("Impact Regular", Font.BOLD, 13));
            genreBox.setFocusable(false);
            genreBox.setBackground(Color.decode("#000011"));
            frame.add(genreBox);
            y += 20;
        }

        JButton add = interfaceMethodes.createButton("Add", 300, 550, 100, 30, "#2F4F4F", "#E1DE07", null);
        add.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

        add.addActionListener(e -> {
            if (title.getText().isEmpty() || release.getText().isEmpty() || plot.getText().isEmpty() || duration.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                movie.setTitle(title.getText());
                try {
                    movie.setReleaseYear(Integer.parseInt(release.getText()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Release year must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                movie.setPlot(plot.getText());
                movie.setDuration(duration.getText());
                movie.getDirectors().clear();
                for (int i = 0; i < directorsModel.size(); i++) {
                    movie.getDirectors().add(directorsModel.get(i));
                }
                movie.getActors().clear();
                for (int i = 0; i < actorsModel.size(); i++) {
                    movie.getActors().add(actorsModel.get(i));
                }
                movie.getGenres().clear();
                for (Component component : frame.getContentPane().getComponents()) {
                    if (component instanceof JCheckBox) {
                        if (((JCheckBox) component).isSelected()) {
                            movie.getGenres().add(Genre.valueOf(((JCheckBox) component).getText()));
                        }
                    }
                }
                movie.setType("Movie");
                this.addProductionSystem(movie, imdb);
                this.addContribution(movie);
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
        frame.add(add);
        frame.setVisible(true);
    }

    public void createSeriesUI(String name, IMDB imdb){
        Series series = new Series();
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(800, 600, "Add Series");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel plot = interfaceMethodes.createLabel("Plot: ", 70, 5, 500, 50, "#E1DE07");
        plot.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JTextField plotField = interfaceMethodes.createTextField(190, 15, 300, 30);
        plotField.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel releaseYear = interfaceMethodes.createLabel("Release Year: ", 70, 50, 500, 50, "#E1DE07");
        releaseYear.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JTextField releaseYearField = interfaceMethodes.createTextField(190, 60, 300, 30);
        releaseYearField.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel numSeasons = interfaceMethodes.createLabel("Nr. of seasons: ", 70, 100, 500, 50, "#E1DE07");
        numSeasons.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JTextField numSeasonsField = interfaceMethodes.createTextField(190, 110, 300, 30);
        numSeasonsField.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel directorsLabel = interfaceMethodes.createLabel("Nr. of directors: ", 60, 150, 500, 50, "#E1DE07");
        directorsLabel.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel actorsLabel = interfaceMethodes.createLabel("Nr. of actors: ", 315, 150, 500, 50, "#E1DE07");
        actorsLabel.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel seasonsLabel = interfaceMethodes.createLabel("Seasons: ", 530, 150, 500, 50, "#E1DE07");
        seasonsLabel.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel genresLabel = interfaceMethodes.createLabel("Genres: ", 70, 360, 500, 50, "#E1DE07");
        genresLabel.setFont(new Font("Impact Regular", Font.BOLD, 15));

        // Directors label input
        Integer[] numbers = new Integer[]{1, 2, 3};
        Integer[] episodes = new Integer[]{1, 2, 3, 4, 5, 6};
        JComboBox<Integer> directorsList = new JComboBox<>(numbers);
        directorsList.setBounds(180, 160, 50, 30);
        directorsList.setFont(new Font("Impact Regular", Font.BOLD, 15));
        directorsList.addActionListener(e -> {
            Component[] components = frame.getContentPane().getComponents();
            for (Component component : components) {
                if (component instanceof JLabel && ((JLabel) component).getText().startsWith("Director ")) {
                    frame.remove(component);
                } else if (component instanceof JTextField && component.getName() != null && component.getName().startsWith("DirectorField")) {
                    frame.remove(component);
                }
            }

            int number = (int) directorsList.getSelectedItem();
            for (int i = 0; i < number; i++) {
                JLabel director = interfaceMethodes.createLabel("Director " + (i + 1) + ": ", 60, 200 + (i * 50), 500, 50, "#E1DE07");
                director.setFont(new Font("Impact Regular", Font.BOLD, 15));
                JTextField directorField = interfaceMethodes.createTextField(150, 210 + (i * 50), 125, 30);
                directorField.setFont(new Font("Impact Regular", Font.BOLD, 15));
                directorField.setName("DirectorField" + i);
                frame.add(director);
                frame.add(directorField);
            }
            frame.revalidate();
            frame.repaint();
        });

        // Actors label input
        JComboBox<Integer> actorsList = new JComboBox<>(numbers);
        actorsList.setBounds(425, 160, 50, 30);
        actorsList.setFont(new Font("Impact Regular", Font.BOLD, 15));
        actorsList.addActionListener(e -> {
            Component[] components = frame.getContentPane().getComponents();
            for (Component component : components) {
                if (component instanceof JLabel && ((JLabel) component).getText().startsWith("Actor ")) {
                    frame.remove(component);
                } else if (component instanceof JTextField && component.getName() != null && component.getName().startsWith("ActorField")) {
                    frame.remove(component);
                }
            }

            int number = (int) actorsList.getSelectedItem();
            for (int i = 0; i < number; i++) {
                JLabel actor = interfaceMethodes.createLabel("Actor " + (i + 1) + ": ", 315, 200 + (i * 50), 500, 50, "#E1DE07");
                actor.setFont(new Font("Impact Regular", Font.BOLD, 15));
                JTextField actorField = interfaceMethodes.createTextField(395, 210 + (i * 50), 125, 30);
                actorField.setFont(new Font("Impact Regular", Font.BOLD, 15));
                actorField.setName("ActorField" + i);
                frame.add(actor);
                frame.add(actorField);
            }
            frame.revalidate();
            frame.repaint();
        });

        // Seasons label input
        JComboBox<Integer> seasonsList = new JComboBox<>(numbers);
        seasonsList.setBounds(610, 160, 50, 30);
        seasonsList.setFont(new Font("Impact Regular", Font.BOLD, 15));
        seasonsList.addActionListener(e -> {
            Component[] components = frame.getContentPane().getComponents();
            for (Component component : components) {
                if (component instanceof JLabel && ((JLabel) component).getText().startsWith("Season ")) {
                    frame.remove(component);
                } else if (component instanceof JComboBox<?> && component.getName() != null && component.getName().startsWith("SeasonField")) {
                    frame.remove(component);
                } else if (component instanceof JButton && component.getName() != null && component.getName().startsWith("AddEpisode")) {
                    frame.remove(component);
                }
            }

            int number = (int) seasonsList.getSelectedItem();
            for (int i = 0; i < number; i++) {
                JLabel season = interfaceMethodes.createLabel("Season " + (i + 1) + "eps: ", 530, 200 + (i * 50), 500, 50, "#E1DE07");
                season.setFont(new Font("Impact Regular", Font.BOLD, 15));
                JComboBox<Integer> episodesList = new JComboBox<>(episodes);
                episodesList.setBounds(640, 210 + (i * 50), 50, 30);
                episodesList.setFont(new Font("Impact Regular", Font.BOLD, 15));
                episodesList.setName("SeasonField" + i);

                JButton addEpisode = interfaceMethodes.createButton("Add", 700, 210 + (i * 50), 50, 30, "#2f2f2f", "#ffffff", null);
                addEpisode.setBorder(BorderFactory.createLineBorder(Color.decode("#48E5FB"), 1));
                addEpisode.setFont(new Font("Impact Regular", Font.BOLD, 15));
                addEpisode.setName("AddEpisode" + i);
                int finalI = i;
                addEpisode.addActionListener(e1 -> {
                    JFrame frame1 = interfaceMethodes.createFrame(400, 500, "Add Episodes");
                    frame1.getContentPane().setBackground(Color.decode("#000011"));
                    List<Episode> episodes1 = new ArrayList<>();

                    JLabel title = interfaceMethodes.createLabel("Season " + (finalI + 1) + " Episodes name: ", 70, 5, 500, 50, "#E1DE07");
                    title.setFont(new Font("Impact Regular", Font.BOLD, 20));
                    frame1.add(title);

                    for (int j = 0; j < (int) episodesList.getSelectedItem(); j++) {
                        JLabel episode = interfaceMethodes.createLabel("Episode " + (j + 1) + ": ", 70, 50 + (j * 50), 500, 50, "#E1DE07");
                        episode.setFont(new Font("Impact Regular", Font.BOLD, 15));
                        JTextField episodeField = interfaceMethodes.createTextField(150, 60 + (j * 50), 150, 30);
                        episodeField.setFont(new Font("Impact Regular", Font.BOLD, 15));
                        episodeField.setName("EpisodeField" + j);
                        frame1.add(episode);
                        frame1.add(episodeField);
                    }
                    JButton add = interfaceMethodes.createButton("Add", 150, 425, 100, 30, "#2f2f2f", "#ffffff", null);
                    add.setBorder(BorderFactory.createLineBorder(Color.decode("#48E5FB"), 1));
                    add.setFont(new Font("Impact Regular", Font.BOLD, 20));
                    add.addActionListener(e2 -> {
                        Component[] components1 = frame1.getContentPane().getComponents();
                        for (Component component : components1) {
                            if (component instanceof JTextField && component.getName() != null && component.getName().startsWith("EpisodeField")) {
                                Episode episode = new Episode();
                                episode.setEpisodeName(((JTextField) component).getText());
                                episodes1.add(episode);
                            }
                        }
                        frame1.dispose();
                    });
                    series.getSeasons().put("Season " + (finalI + 1), episodes1);
                    frame1.add(add);
                    frame1.setVisible(true);
                });

                frame.add(season);
                frame.add(addEpisode);
                frame.add(episodesList);
            }
            frame.revalidate();
            frame.repaint();
        });

        // Genres label checkboxes
        int j = 0, y1 = 0;
        for (int i = 0; i < Genre.values().length; i++) {
            JCheckBox checkBox = new JCheckBox(Genre.values()[i].toString());
            checkBox.setBounds(190 + (j * 100), 370 + y1, 100, 30);
            j++;
            checkBox.setFont(new Font("Impact Regular", Font.BOLD, 15));
            checkBox.setBackground(Color.decode("#000011"));
            checkBox.setForeground(Color.decode("#ffffff"));
            frame.add(checkBox);
            if (i % 5 == 0 && i != 0) {
                j = 0;
                y1 += 20;
            }
        }

        JButton add = interfaceMethodes.createButton("Add", 300, 500, 100, 30, "#2f2f2f", "#ffffff", null);
        add.setBorder(BorderFactory.createLineBorder(Color.decode("#48E5FB"), 1));
        add.setFont(new Font("Impact Regular", Font.BOLD, 20));
        add.addActionListener(e -> {
            if (plotField.getText().isEmpty() || releaseYearField.getText().isEmpty() || numSeasonsField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String plot1 = plotField.getText();
                try {
                    series.setReleaseYear(Integer.parseInt(releaseYearField.getText()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Release year must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    series.setNumSeasons(Integer.parseInt(numSeasonsField.getText()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Number of seasons must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                List<String> directors = new ArrayList<>();
                List<String> actors = new ArrayList<>();
                List<Genre> genres = new ArrayList<>();
                Component[] components = frame.getContentPane().getComponents();
                for (Component component : components) {
                    if (component instanceof JTextField && component.getName() != null && component.getName().startsWith("DirectorField")) {
                        directors.add(((JTextField) component).getText());
                    } else if (component instanceof JTextField && component.getName() != null && component.getName().startsWith("ActorField")) {
                        actors.add(((JTextField) component).getText());
                    } else if (component instanceof JCheckBox && ((JCheckBox) component).isSelected()) {
                        genres.add(series.stringToGenre(((JCheckBox) component).getText()));
                    }
                }
                if (directors.isEmpty() || actors.isEmpty() || genres.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    series.setTitle(name);
                    series.setPlot(plot1);
                    series.setType("Series");
                    series.setDirectors(directors);
                    series.setActors(actors);
                    series.setGenres(genres);
                    this.addProductionSystem(series, imdb);
                    this.addContribution(series);
                    JOptionPane.showMessageDialog(null, "Series has been added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                }
            }
        });

        frame.add(plot);
        frame.add(plotField);
        frame.add(releaseYear);
        frame.add(directorsLabel);
        frame.add(actorsLabel);
        frame.add(releaseYearField);
        frame.add(numSeasons);
        frame.add(numSeasonsField);
        frame.add(directorsList);
        frame.add(actorsList);
        frame.add(genresLabel);
        frame.add(seasonsLabel);
        frame.add(seasonsList);
        frame.add(add);
        frame.setVisible(true);
    }

    public void createActorUI(String name, IMDB imdb){
        Actor actor = new Actor();
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(700, 500, "Update actor");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel nameLabel = interfaceMethodes.createLabel("Name:", 100, 30, 100, 50, "#E1DE07");
        nameLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JLabel biographyLabel = interfaceMethodes.createLabel("Biography:", 100, 250, 200, 50, "#E1DE07");
        biographyLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JLabel performancesLabel = interfaceMethodes.createLabel("Performances:", 400, 30, 200, 50, "#E1DE07");
        performancesLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JTextField name1 = new JTextField(name);
        name1.setBounds(100, 80, 200, 30);
        name1.setFont(new Font("Impact Regular", Font.BOLD, 15));
        name1.setBackground(Color.DARK_GRAY);
        name1.setForeground(Color.WHITE);
        name1.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

        JTextArea biography = new JTextArea();
        biography.setBounds(100, 300, 475, 100);
        biography.setLineWrap(true);
        biography.setWrapStyleWord(true);
        biography.setBackground(Color.DARK_GRAY);
        biography.setForeground(Color.WHITE);
        biography.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        biography.setFont(new Font("Impact Regular", Font.BOLD, 13));

        DefaultListModel<String> performanceList = new DefaultListModel<>();
        JList<String> performanceJList = new JList<>(performanceList);
        performanceJList.setBounds(375, 80, 200, 200);
        performanceJList.setFont(new Font("Impact Regular", Font.BOLD, 14));
        performanceJList.setBackground(Color.DARK_GRAY);
        performanceJList.setForeground(Color.WHITE);
        performanceJList.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

        JScrollPane scrollPane = new JScrollPane(performanceJList);
        scrollPane.setBounds(375, 80, 200, 200);
        scrollPane.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(Color.DARK_GRAY);
        scrollPane.setForeground(Color.WHITE);

        performanceJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && performanceJList.getSelectedIndex() != -1) {
                JFrame frame1 = interfaceMethodes.createFrame(300, 400, "Update performance");
                frame1.getContentPane().setBackground(Color.decode("#000011"));
                Performance performance = new Performance();

                JLabel titleLabel = interfaceMethodes.createLabel("Title:", 20, 30, 100, 50, "#E1DE07");
                titleLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

                JLabel typeLabel = interfaceMethodes.createLabel("Type:", 20, 130, 100, 50, "#E1DE07");
                typeLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

                JTextField title = new JTextField(performanceJList.getSelectedValue().split(" - ")[0]);
                title.setBounds(20, 80, 250, 30);
                title.setFont(new Font("Impact Regular", Font.BOLD, 15));
                title.setBackground(Color.DARK_GRAY);
                title.setForeground(Color.WHITE);
                title.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

                JTextField type = new JTextField(performanceJList.getSelectedValue().split(" - ")[1]);
                type.setBounds(20, 180, 250, 30);
                type.setFont(new Font("Impact Regular", Font.BOLD, 15));
                type.setBackground(Color.DARK_GRAY);
                type.setForeground(Color.WHITE);
                type.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

                JButton update = interfaceMethodes.createButton("Update", 20, 230, 100, 30, "#2F4F4F", "#E1DE07", null);
                update.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
                update.addActionListener(e1 -> {
                    if (title.getText().isEmpty() || type.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Please fill in all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        performance.setTitle(title.getText());
                        performance.setType(type.getText());
                        performanceList.setElementAt(performance.getTitle() + " - " + performance.getType(), performanceJList.getSelectedIndex());
                        frame.repaint();
                        frame1.dispose();
                    }
                });
                JButton delete = interfaceMethodes.createButton("Delete", 150, 230, 100, 30, "#2F4F4F", "#E1DE07", null);
                delete.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
                delete.addActionListener(e1 -> {
                    performanceList.remove(performanceJList.getSelectedIndex());
                    frame.repaint();
                    frame1.dispose();
                });

                frame1.add(titleLabel);
                frame1.add(typeLabel);
                frame1.add(title);
                frame1.add(type);
                frame1.add(update);
                frame1.add(delete);
                frame1.setVisible(true);
            }
        });

        // Add performance
        JButton addPerformance = interfaceMethodes.createButton("+", 580, 80, 30, 30, "#2F4F4F", "#E1DE07", null);
        addPerformance.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));
        addPerformance.addActionListener(e -> {
            JFrame frame1 = interfaceMethodes.createFrame(300, 400, "Update performance");
            frame1.getContentPane().setBackground(Color.decode("#000011"));
            Performance performance = new Performance();

            JLabel titleLabel = interfaceMethodes.createLabel("Title:", 20, 30, 100, 50, "#E1DE07");
            titleLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

            JLabel typeLabel = interfaceMethodes.createLabel("Type:", 20, 130, 100, 50, "#E1DE07");
            typeLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

            JTextField title = new JTextField();
            title.setBounds(20, 80, 250, 30);
            title.setFont(new Font("Impact Regular", Font.BOLD, 15));
            title.setBackground(Color.DARK_GRAY);
            title.setForeground(Color.WHITE);
            title.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

            JTextField type = new JTextField();
            type.setBounds(20, 180, 250, 30);
            type.setFont(new Font("Impact Regular", Font.BOLD, 15));
            type.setBackground(Color.DARK_GRAY);
            type.setForeground(Color.WHITE);
            type.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

            JButton update = interfaceMethodes.createButton("Add", 100, 230, 100, 30, "#2F4F4F", "#E1DE07", null);
            update.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
            update.addActionListener(e1 -> {
                if (title.getText().isEmpty() || type.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    performance.setTitle(title.getText());
                    performance.setType(type.getText());
                    performanceList.addElement(performance.getTitle() + " - " + performance.getType());
                    frame.repaint();
                    frame1.dispose();
                }
            });
            frame1.add(titleLabel);
            frame1.add(typeLabel);
            frame1.add(title);
            frame1.add(type);
            frame1.add(update);
            frame1.setVisible(true);

        });

        JButton add = interfaceMethodes.createButton("Add", 100, 420, 100, 30, "#2F4F4F", "#E1DE07", null);
        add.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        add.addActionListener(e -> {
            if (name1.getText().isEmpty() || biography.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill in all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                actor.setName(name1.getText());
                actor.setBiography(biography.getText());
                if (!performanceList.isEmpty()){
                    List<Performance> performances = new ArrayList<>();
                    for (int i = 0; i < performanceList.size(); i++){
                        String[] performanceInfo = performanceList.get(i).split(" - ");
                        Performance performance = new Performance();
                        performance.setTitle(performanceInfo[0]);
                        performance.setType(performanceInfo[1]);
                        performances.add(performance);
                    }
                    actor.setPerformances(performances);
                } else {
                    actor.setPerformances(new ArrayList<>());
                }
                this.addActorSystem(actor, imdb);
                frame.dispose();
            }
        });

        frame.add(nameLabel);
        frame.add(addPerformance);
        frame.add(performancesLabel);
        frame.add(scrollPane);
        frame.add(name1);
        frame.add(addPerformance);
        frame.add(biographyLabel);
        frame.add(biography);
        frame.add(add);
        frame.setVisible(true);
    }


    public void addProductionUI(IMDB imdb){
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(550, 300, "Add Production or Actor");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel title = interfaceMethodes.createLabel("Choose what do you want to add: ", 90, 5, 500, 50, "#E1DE07");
        title.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JLabel nameLabel = interfaceMethodes.createLabel("Name: ", 70, 50, 500, 50, "#E1DE07");
        nameLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JTextField nameField = interfaceMethodes.createTextField(150, 60, 300, 30);
        nameField.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel type = interfaceMethodes.createLabel("Type: ", 70, 100, 500, 50, "#E1DE07");
        type.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JComboBox<String> productionsList = new JComboBox<>(new String[]{"Movie", "Series", "Actor"});
        productionsList.setBounds(150, 110, 300, 30);
        productionsList.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JButton add = interfaceMethodes.createButton("Add", 220, 150, 100, 30, "#2f2f2f", "#ffffff", null);
        add.setBorder(BorderFactory.createLineBorder(Color.decode("#48E5FB"), 1));
        add.setFont(new Font("Impact Regular", Font.BOLD, 20));
        add.addActionListener(e -> {
            if (nameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a name", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String name = nameField.getText();
                for (Production production : imdb.getProductions()) {
                    if (production.getTitle().equals(name)) {
                        JOptionPane.showMessageDialog(null, "Production already exists", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                for (Actor actor : imdb.getActors()) {
                    if (actor.getName().equals(name)) {
                        JOptionPane.showMessageDialog(null, "Actor already exists", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                if (productionsList.getSelectedItem().toString().equals("Movie")) {
                    this.createMovieUI(name, imdb);
                    frame.dispose();
                } else if (productionsList.getSelectedItem().toString().equals("Series")) {
                    this.createSeriesUI(name, imdb);
                    frame.dispose();
                } else if (productionsList.getSelectedItem().toString().equals("Actor")) {
                    this.createActorUI(name, imdb);
                    frame.dispose();
                }
            }
        });

        frame.add(title);
        frame.add(nameLabel);
        frame.add(productionsList);
        frame.add(nameField);
        frame.add(type);
        frame.add(add);
        frame.setVisible(true);
    }

    public void solveRequestUI(IMDB imdb){
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(500, 600, "Solve Request");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel yourRequests = interfaceMethodes.createLabel("Your requests: ", 190, 5, 500, 50, "#E1DE07");
        yourRequests.setFont(new Font("Impact Regular", Font.BOLD, 20));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Request request : this.getRequests()) {
            listModel.addElement(request.getType() + " by " + request.getUsername());
        }
        if (listModel.isEmpty()) {
            listModel.addElement("You don't have any requests");
        }
        JList<String> requestsList = new JList<>(listModel);
        requestsList.setBounds(70, 50, 200, 150);
        requestsList.setFont(new Font("Impact Regular", Font.BOLD, 15));
        requestsList.addListSelectionListener(e -> {
            if (requestsList.getSelectedValue() != null && !e.getValueIsAdjusting()) {
                String request = requestsList.getSelectedValue();
                String[] request1 = request.split(" ");
                String type = request1[0];
                String username = request1[2];
                for (Request request2 : this.getRequests()) {
                    if (request2.getType().toString().equals(type) && request2.getUsername().equals(username)) {
                        request2.printRequestUI(imdb, listModel, request, true);
                        this.getRequests().remove(request2);
                        frame.repaint();
                        break;
                    }
                }
            }
        });

            JScrollPane scrollPane = new JScrollPane(requestsList);
            scrollPane.setBounds(70, 50, 350, 150);
            scrollPane.setBackground(Color.decode("#000011"));
            scrollPane.setForeground(Color.decode("#ffffff"));

        if (this.getUserType().equals(AccountType.Admin)) {
            JLabel adminRequests = interfaceMethodes.createLabel("Admin requests: ", 190, 210, 500, 50, "#E1DE07");
            adminRequests.setFont(new Font("Impact Regular", Font.BOLD, 20));

            DefaultListModel<String> listModel1 = new DefaultListModel<>();
            for (Request request : RequestHolder.getRequests()) {
                listModel1.addElement(request.getType() + " by " + request.getUsername());
            }
            if (listModel1.isEmpty()) {
                listModel1.addElement("There are no requests");
            }
            JList<String> requestsList1 = new JList<>(listModel1);
            requestsList1.setBounds(70, 250, 200, 150);
            requestsList1.setFont(new Font("Impact Regular", Font.BOLD, 15));
            requestsList1.addListSelectionListener(e -> {
                if (requestsList1.getSelectedValue() != null && !e.getValueIsAdjusting()) {
                    String request = requestsList1.getSelectedValue();
                    String[] request1 = request.split(" ");
                    String type = request1[0];
                    String username = request1[2];
                    for (Request request2 : RequestHolder.getRequests()) {
                        if (request2.getType().toString().equals(type) && request2.getUsername().equals(username)) {
                            request2.printRequestUI(imdb, listModel1, request, true);
                            frame.repaint();
                            break;
                        }
                    }
                }
            });

            JScrollPane scrollPane1 = new JScrollPane(requestsList1);
            scrollPane1.setBounds(70, 250, 350, 150);
            scrollPane1.setBackground(Color.decode("#000011"));
            scrollPane1.setForeground(Color.decode("#ffffff"));

            frame.add(adminRequests);
            frame.add(scrollPane1);
        }

        frame.add(yourRequests);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    public void updateProductionUI(IMDB imdb) {
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(550, 300, "Update Production/Actor");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel title = interfaceMethodes.createLabel("Choose what do you want to update: ", 90, 5, 500, 50, "#E1DE07");
        title.setFont(new Font("Impact Regular", Font.BOLD, 20));

        String[] productions = new String[getContributions().size()];
        for (int i = 0; i < getContributions().size(); i++) {
            if (getContributions().toArray()[i] instanceof Actor) {
                String actor = "Actor: " + ((Actor) getContributions().toArray()[i]).getName();
                productions[i] = actor;
            } else if (getContributions().toArray()[i] instanceof Movie) {
                String prod = "Movie: " + ((Production) getContributions().toArray()[i]).getTitle();
                productions[i] = prod;
            } else if (getContributions().toArray()[i] instanceof Series) {
                String prod = "Series: " + ((Production) getContributions().toArray()[i]).getTitle();
                productions[i] = prod;
            }
        }

        JComboBox<String> productionsList = new JComboBox<>(productions);
        productionsList.setBounds(100, 60, 300, 30);
        productionsList.setFont(new Font("Impact Regular", Font.BOLD, 15));
        productionsList.setFocusable(false);
        productionsList.setBackground(Color.decode("#000011"));
        productionsList.setForeground(Color.decode("#E1DE07"));

        JButton update = interfaceMethodes.createButton("Update", 200, 150, 100, 30, "#2f2f2f", "#ffffff", null);
        update.setBorder(BorderFactory.createLineBorder(Color.decode("#48E5FB"), 1));
        update.setFont(new Font("Impact Regular", Font.BOLD, 20));
        update.addActionListener(e -> {
            if (productionsList.getSelectedItem() != null ){
                String production = productionsList.getSelectedItem().toString();
                String[] production1 = production.split(" ");
                String type = production1[0];
                production1 = Arrays.copyOfRange(production1, 1, production1.length);
                String name = String.join(" ", production1);
                if (type.equals("Actor:")){
                    imdb.findActor(name).updateActorUI();
                } else if (type.equals("Movie:")){
                    ((Movie) imdb.findProduction(name)).updateMovieUI();
                } else if (type.equals("Series:")){
                    ((Series) imdb.findProduction(name)).updateSeriesUI();
                }
            }
        });

        frame.add(productionsList);
        frame.add(update);
        frame.add(title);
        frame.setVisible(true);
    }

}