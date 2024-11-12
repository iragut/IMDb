package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.example.entities.Actor;
import org.example.entities.Production;
import org.example.enums.AccountType;
import org.example.enums.Genre;
import org.example.input.account.AccountInput;
import org.example.input.production.ProductionInput;
import org.example.interfaces.InterfaceMethodes;
import org.example.mapper.AccountInputToUserMapper;
import org.example.mapper.ProductionInputToProductionMapper;
import org.example.observer.NotificationSubject;
import org.example.request.Request;
import org.example.request.RequestHolder;
import org.example.user.Staff;
import org.example.user.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
public class IMDB {
    private static IMDB instance;
    private List<User<?>> users;
    private List<Actor> actors;
    private List<Request> requests;
    private List<Production> productions;
    private NotificationSubject notificationSubject;
    private int requestID = 1;

    private IMDB() throws IOException {
        run();
    }

    public void run() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        users = new ArrayList<>();
        actors = new ArrayList<>();
        requests = new ArrayList<>();
        productions = new ArrayList<>();
        notificationSubject = new NotificationSubject();

        final File fileActors = new File("src/test/resources/testResources/actors.json");
        final File fileRequest = new File("src/test/resources/testResources/requests.json");
        final File fileProduction = new File("src/test/resources/testResources/production.json");
        final File fileAccounts = new File("src/test/resources/testResources/accounts.json");

        actors.addAll(objectMapper.readValue(fileActors, new TypeReference<>() {}));
        requests.addAll(objectMapper.readValue(fileRequest, new TypeReference<>() {}));
        final List<ProductionInput> productionInputs = objectMapper.readValue(fileProduction, new TypeReference<>() {});
        final List<AccountInput> accountInputs = objectMapper.readValue(fileAccounts, new TypeReference<>() {});

        for (final ProductionInput productionInput : productionInputs){
            productions.add(ProductionInputToProductionMapper.mapProductionInputToProduction(productionInput));
        }
        for (final AccountInput accountInput : accountInputs) {
            users.add(AccountInputToUserMapper.mapAccountInputToUser(accountInput, actors, productions));
        }
        for (Request request : requests){
            request.setId(requestID);
            String username = request.getTo();
            for (User<?> user : users){
                if (user.getUsername().equals(username) && !user.getUserType().equals(AccountType.Regular)){
                    ((Staff<?>) user).getRequests().add(request);
                }
            }
            if (username.equals("ADMIN")){
                RequestHolder.addRequest(request);
            }
            requestID++;
        }
        for(User<?> user : users){
            notificationSubject.addObserver(user);
        }
        for (Production production : productions) {
            String title;
            if (production.getTitle().contains(":")) {
                title = production.getTitle().replace(":", "");
            } else {
                title = production.getTitle();
            }
            String imagePath = "src/main/java/org/example/img/" + title + ".jpg";
            Path imageFilePath = Paths.get(imagePath);
            if (Files.exists(imageFilePath)) {
                production.setImage(new ImageIcon(imagePath));
            } else {
                production.setImage(null);
            }
            String videoPath = "src/main/java/org/example/Video/" + title + ".mp4";
            Path videoFilePath = Paths.get(videoPath);
            if (Files.exists(videoFilePath)) {
                production.setTrailer(videoPath);
            } else {
                production.setTrailer(null);
            }
        }
    }

    public static IMDB getInstance() throws IOException {
        if (instance == null) {
            instance = new IMDB();
        }
        return instance;
    }

    public void showAllProductions(){
        Scanner scanner = new Scanner(System.in);
        for (Production production : productions){
            production.getAverageRating();
            production.displayInfo();
        }
        while (true) {
            try {
                System.out.println("1) Sort by the highest number of ratings");
                System.out.println("2) Sort by the lowest number of ratings");
                System.out.println("3) Sort by genre");
                System.out.println("4) Go back");
                int number = scanner.nextInt();
                if (number == 1) {
                    productions.sort(Production::compareToByHighestNumberOfRating);
                    for (Production production : productions){
                        production.displayInfo();
                    }
                } else if (number == 2) {
                    productions.sort(Production::compareToByLowestNumberOfRating);
                    for (Production production : productions){
                        production.displayInfo();
                    }
                } else if (number == 3) {
                    scanner.nextLine();
                    System.out.println("Select a genre:");
                    while (true){
                        try {
                            for (Genre genre : Genre.values()) {
                                System.out.println(genre);
                            }
                            System.out.println();
                            System.out.println("To go back, press b");
                            String genre = scanner.nextLine();
                            if (genre.equals("b")) {
                                for (Production production : productions){
                                    production.displayInfo();
                                }
                                break;
                            } else {
                                for (Production production : productions) {
                                    if (production.getGenres().contains(production.stringToGenre(genre))) {
                                        production.displayInfo();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Please select a valid command");
                            scanner.nextLine();
                        }
                    }
                } else if (number == 4) {
                    break;
                } else {
                    System.out.println("Please select a valid command");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Please select a valid command");
                scanner.nextLine();
            }
        }
    }

    public void showAllActors(){
        Scanner scanner = new Scanner(System.in);
        for (Actor actor : actors){
            actor.displayInfo();
        }
        while(true) {
            try {
                System.out.println("1) Sort by name");
                System.out.println("2) Go back");
                int number = scanner.nextInt();
                if (number == 1) {
                    actors.sort(Actor::compareTo);
                    for (Actor actor : actors){
                        actor.displayInfo();
                    }
                } else if (number == 2) {
                    break;
                } else {
                    System.out.println("Please select a valid command");
                }
            } catch (Exception e) {
                System.out.println("Please select a valid command");
            }
        }
    }
    public void addRequestInSystem(Request request){
        for (User<?> user : users){
            if (user.getUsername().equals(request.getTo())){
                ((Staff<?>) user).getRequests().add(request);
            }
        }
    }

    public void searchInSystemUI(String text, User<?> user) {
        for (Production production : productions) {
            if (production.getTitle().equals(text)) {
                production.displayInfoUI(user);
                return;
            }
        }

        for (Actor actor : actors) {
            if (actor.getName().equals(text)) {
                actor.displayInfoUI(user);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Actor or Production not found!");
    }
    public void searchInSystem(){
        boolean found;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                found = false;
                System.out.println("1) Search a production");
                System.out.println("2) Search an actor");
                System.out.println("3) Go back");
                int number = scanner.nextInt();
                if (number == 1) {
                    scanner.nextLine();
                    System.out.println("Enter the name of the production:");
                    String name = scanner.nextLine();
                    for (Production production : productions) {
                        if (production.getTitle().equals(name)) {
                            found = true;
                            production.getAverageRating();
                            production.displayInfo();
                        }
                    }
                    if (!found)
                        System.out.println("Production not found!");
                } else if (number == 2) {
                    scanner.nextLine();
                    System.out.println("Enter the name of the actor:");
                    String name = scanner.nextLine();
                    for (Actor actor : actors) {
                        if (actor.getName().equals(name)) {
                            found = true;
                            actor.displayInfo();
                        }
                    }
                    if (!found)
                        System.out.println("Actor not found!");
                } else if (number == 3) {
                    break;
                } else {
                    System.out.println("Please select a valid command");
                }
            } catch (Exception e) {
                System.out.println("Please select a valid command");
            }
        }
    }
    public Production findProduction(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the production:");
        String name = scanner.nextLine();
        for (Production production : productions) {
            if (production.getTitle().equals(name)) {
                return production;
            }
        }
        System.out.println("Production not found!");
        return null;
    }

    public Production findProduction(String name){
        for (Production production : productions) {
            if (production.getTitle().equals(name)) {
                return production;
            }
        }
        return null;
    }

    public Actor findActor(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the actor:");
        String name = scanner.nextLine();
        for (Actor actor : actors) {
            if (actor.getName().equals(name)) {
                return actor;
            }
        }
        System.out.println("Actor not found!");
        return null;
    }

    public Actor findActor(String name){
        for (Actor actor : actors) {
            if (actor.getName().equals(name)) {
                return actor;
            }
        }
        return null;
    }


    public User<?> findUser(String username){
        for (User<?> user : users){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public String findContributor(Object object){
        for (User<?> user : users){
            if (user.getUserType().equals(AccountType.Contributor)){
                if (((Staff<?>) user).getContributions().contains(object)){
                    return user.getUsername();
                }
            }
        }
        return null;
    }

    private JScrollPane productionList(User<?> user, Genre genre){
        DefaultListModel<String> stringProduction = new DefaultListModel<>();
        int len;
        for (Production production : productions){
            if (genre != null){
                if (production.getGenres().contains(genre)){
                    len = production.getTitle().length();
                    if (60 - len > 0){
                        stringProduction.addElement(production.getTitle() + " ".repeat(60 - len) + production.getAverageRating()
                                + " ".repeat(10) + production.getGenres());
                    } else {
                        stringProduction.addElement(production.getTitle() + " " + production.getAverageRating()
                                + " ".repeat(10) + production.getGenres());
                    }
                }
            } else {
                len = production.getTitle().length();
                if (60 - len > 0){
                    stringProduction.addElement(production.getTitle() + " ".repeat(60 - len) + production.getAverageRating()
                            + " ".repeat(10) + production.getGenres());
                } else {
                    stringProduction.addElement(production.getTitle() + " " + production.getAverageRating()
                            + " ".repeat(10) + production.getGenres());
                }
            }
        }
        JList<String> listProduction = new JList<>(stringProduction);
        listProduction.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String title = listProduction.getSelectedValue().substring(0, 60).trim();
                for (Production production : productions) {
                    if (production.getTitle().equals(title)) {
                        production.displayInfoUI(user);
                    }
                }
            }
        });
        listProduction.setFont(new Font("Impact Regular", Font.BOLD, 15));
        listProduction.setBorder(new LineBorder(Color.decode("#E1DE07"), 2));
        listProduction.setBackground(Color.decode("#000011"));
        listProduction.setForeground(Color.decode("#ffffff"));
        listProduction.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(listProduction);
        scrollPane.setBounds(20, 20, 650, 400);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.setBorder(new LineBorder(Color.decode("#E1DE07"), 2));
        scrollPane.setBackground(Color.decode("#000011"));
        return scrollPane;
    }

    private JScrollPane actorList(User<?> user){
        DefaultListModel<String> stringActors = new DefaultListModel<>();
        for (Actor actor : actors){
            stringActors.addElement(actor.getName());
        }
        JList<String> list = new JList<>(stringActors);
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String name = list.getSelectedValue();
                for (Actor actor : actors) {
                    if (actor.getName().equals(name)) {
                        actor.displayInfoUI(user);
                    }
                }
            }
        });
        list.setFont(new Font("Impact Regular", Font.BOLD, 15));
        list.setBorder(new LineBorder(Color.decode("#E1DE07"), 2));
        list.setBackground(Color.decode("#000011"));
        list.setForeground(Color.decode("#ffffff"));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(20, 20, 650, 400);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new LineBorder(Color.decode("#E1DE07"), 2));
        scrollPane.setBackground(Color.decode("#000011"));
        scrollPane.setForeground(Color.decode("#ffffff"));
        return scrollPane;
    }

    public void viewAllProductionUI(User<?> user){
        productions.sort(Production::compareTo);
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(700, 550, "All Productions");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        final List<JScrollPane> scrollPaneList = new ArrayList<>();
        scrollPaneList.add(productionList(user, null));

        JLabel sort = interfaceMethodes.createLabel("Sort by:", 20, 410, 100, 50, "#ffffff");
        sort.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JLabel selectGenre1 = interfaceMethodes.createLabel("Select genre:", 340, 410, 100, 50, "#ffffff");
        selectGenre1.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JButton nrRatingHighest = interfaceMethodes.createButton("NrRatingHighest", 20, 460, 150, 30, "#2F4F4F", "#ffffff", null);
        nrRatingHighest.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        nrRatingHighest.addActionListener(e -> {
            productions.sort(Production::compareToByHighestNumberOfRating);
            JScrollPane scrollPane1 = productionList(user, null);

            frame.remove(scrollPaneList.remove(0));
            scrollPaneList.add(scrollPane1);
            frame.add(scrollPane1);
            frame.revalidate();
            frame.repaint();
        });

        JButton nrRatingLowest = interfaceMethodes.createButton("NrRatingLowest", 170, 460, 150, 30, "#2F4F4F", "#ffffff", null);
        nrRatingLowest.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        nrRatingLowest.addActionListener(e -> {
            productions.sort(Production::compareToByLowestNumberOfRating);
            JScrollPane scrollPane1 = productionList(user, null);

            frame.remove(scrollPaneList.remove(0));
            scrollPaneList.add(scrollPane1);
            frame.add(scrollPane1);
            frame.revalidate();
            frame.repaint();
        });

        JTextField selectGenre = interfaceMethodes.createTextField(340, 460, 190, 30);
        selectGenre.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

        JButton selectGenreButton = interfaceMethodes.createButton("Select Genre", 530, 460, 150, 30, "#2F4F4F", "#ffffff", null);
        selectGenreButton.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        selectGenreButton.addActionListener(e -> {
            String genre = selectGenre.getText();
            try {
                Genre.valueOf(genre);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Genre not found!");
                return;
            }
            JScrollPane scrollPane1 = productionList(user, Genre.valueOf(genre));

            frame.remove(scrollPaneList.remove(0));
            scrollPaneList.add(scrollPane1);
            frame.add(scrollPane1);
            frame.revalidate();
            frame.repaint();
        });


        frame.add(sort);
        frame.add(scrollPaneList.get(0));
        frame.add(nrRatingHighest);
        frame.add(nrRatingLowest);
        frame.add(selectGenre);
        frame.add(selectGenre1);
        frame.add(selectGenreButton);
        frame.setVisible(true);
    }

    public void viewAllActorsUI(User<?> user){
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(700, 550, "All Actors");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JScrollPane scrollPane = actorList(user);

        JLabel sort = interfaceMethodes.createLabel("Sort by:", 20, 410, 100, 50, "#ffffff");
        sort.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JButton sortName = interfaceMethodes.createButton("Sort by name", 20, 460, 150, 30, "#2F4F4F", "#ffffff", null);
        sortName.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

        sortName.addActionListener(e -> {
            actors.sort(Actor::compareTo);
            JScrollPane scrollPane1 = actorList(user);

            frame.remove(scrollPane);
            frame.add(scrollPane1);
            frame.revalidate();
            frame.repaint();
        });

        frame.add(scrollPane);
        frame.add(sort);
        frame.add(sortName);
        frame.setVisible(true);
    }
}