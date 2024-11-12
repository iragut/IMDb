package org.example;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.entities.Actor;
import org.example.entities.Production;
import org.example.enums.AccountType;
import org.example.input.account.CredentialsInput;
import org.example.input.account.Information;
import org.example.input.production.Rating;
import org.example.interfaces.InterfaceMethodes;
import org.example.interfaces.SwingInterface;
import org.example.request.Request;
import org.example.request.RequestHolder;
import org.example.user.Staff;
import org.example.user.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
public class Admin<T extends Comparable<T>> extends Staff<T> implements ActionListener {
    private IMDB imdb;
    private JFrame frame;
    private JPanel panel;
    private JTextField search;
    private JLabel welcome, movies, series;
    private JButton logoutButton ,settingsButton, searchButton, viewAllProduction, viewAllActors, addUser, removeUser;
    private JButton addProductionActor, removeProductionActor, updateProductionActor, solveRequest, menu;

    public Admin(CredentialsInput credentialsInput, String name) {
        this.setInformation(new Information.InformationBuilder(credentialsInput, name).build());
        this.setUserType(AccountType.Admin);
        this.setExperience(0);
        this.setNotifications(new ArrayList<>());
        this.setFavorites(new TreeSet<>());
        this.setRequests(new ArrayList<>());
        this.setContributions(new TreeSet<>());
    }

    @Override
    public void UserInterface(IMDB imdb) {
        this.imdb = imdb;
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        ImageIcon settingIcon = new ImageIcon("src/main/java/org/example/img/setting.png");
        ImageIcon searchIcon = new ImageIcon("src/main/java/org/example/img/search.png");
        frame = interfaceMethodes.createFrame(1100, 650, "IMDB");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        panel = interfaceMethodes.createPanel(0, 0, 300, 650, "#1f1f1f");

        menu = interfaceMethodes.createButton("Menu", 910, 0, 180, 40, "#1f1f1f", "#ffffff", null);
        menu.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));
        final boolean[] menuOpen = {true};

        welcome = interfaceMethodes.createLabel("Welcome back, " + this.getInformation().getName() + "!",
                0, 5, 300, 50, "#C9EB48");
        welcome.setFont(new Font("Impact Regular", Font.BOLD, 15));

        frame = interfaceMethodes.addInformationUser(frame, this);

        logoutButton = interfaceMethodes.createButton("Log out", 0, 583, 80, 30, "#1f1f1f", "#ffffff", this);
        logoutButton.setBorder(new LineBorder(Color.decode("#C9EB48"), 2));

        settingsButton = new JButton(settingIcon);
        settingsButton.setBounds(80, 583, 30, 30);
        settingsButton.setBackground(Color.decode("#1f1f1f"));
        settingsButton.addActionListener(this);
        settingsButton.setBorder(new LineBorder(Color.decode("#C9EB48"), 2));


        search = interfaceMethodes.createTextField(300, 0, 550, 35);
        search.setFont(new Font("Impact Regular", Font.BOLD, 15));
        searchButton = new JButton(searchIcon);
        searchButton.setBounds(850, 0, 35, 35);
        searchButton.setFocusable(false);
        searchButton.setBackground(Color.decode("#ffffff"));
        searchButton.addActionListener(this);

        viewAllProduction = interfaceMethodes.createButton("View all productions", 910, 40, 180, 40,
                "#1f1f1f", "#ffffff", this);
        viewAllProduction.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));
        viewAllActors = interfaceMethodes.createButton("View all actors", 910, 80, 180, 40,
                "#1f1f1f", "#ffffff", this);
        viewAllActors.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));

        addUser = interfaceMethodes.createButton("Add user", 910, 120, 180, 40,
                "#1f1f1f", "#ffffff", this);
        addUser.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));
        removeUser = interfaceMethodes.createButton("Remove user", 910, 160, 180, 40,
                "#1f1f1f", "#ffffff", this);
        removeUser.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));

        String[] notifications;
        if (!this.getNotifications().isEmpty()) {
            notifications = new String[this.getNotifications().size()];
            for (int i = 0; i < this.getNotifications().size(); i++) {
                notifications[i] = this.getNotifications().get(i);
            }
        } else {
            notifications = new String[]{"You have zero notification!"};
        }
        JList<String> list = new JList<>(notifications);
        list.setBackground(Color.decode("#1f1f1f"));
        list.setForeground(Color.decode("#ffffff"));
        list.setFont(new Font("Impact Regular", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(20, 80, 250, 300);
        scrollPane.setBorder(new LineBorder(Color.decode("#48E5FB"), 2));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));


        addProductionActor = interfaceMethodes.createButton("Add Prod/Actor", 910, 200, 180, 40,
                "#1f1f1f", "#ffffff", this);
        addProductionActor.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));
        updateProductionActor = interfaceMethodes.createButton("Update Prod/Actor", 910, 240, 180, 40,
                "#1f1f1f", "#ffffff", this);
        updateProductionActor.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));

        removeProductionActor = interfaceMethodes.createButton("Remove Prod/Actor", 910, 280, 180, 40,
                "#1f1f1f", "#ffffff", this);
        removeProductionActor.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));

        solveRequest = interfaceMethodes.createButton("Solve request", 910, 320, 180, 40,
                "#1f1f1f", "#ffffff", this);
        solveRequest.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));


        frame.add(viewAllProduction);
        frame.add(viewAllActors);
        frame.add(addProductionActor);
        frame.add(updateProductionActor);
        frame.add(removeProductionActor);
        frame.add(solveRequest);
        frame.add(addUser);
        frame.add(removeUser);
        viewAllProduction.setVisible(false);
        viewAllActors.setVisible(false);
        addProductionActor.setVisible(false);
        updateProductionActor.setVisible(false);
        removeProductionActor.setVisible(false);
        solveRequest.setVisible(false);
        addUser.setVisible(false);
        removeUser.setVisible(false);

        movies = interfaceMethodes.createLabel("Movies", 320, 45, 300, 50, "#E1DE07");
        movies.setFont(new Font("Impact Regular", Font.BOLD, 15));
        frame = interfaceMethodes.selectMovie(frame, imdb, this);

        series = interfaceMethodes.createLabel("Series", 320, 300, 300, 50, "#E1DE07");
        series.setFont(new Font("Impact Regular", Font.BOLD, 15));
        frame = interfaceMethodes.selectSeries(frame, imdb, this);

        menu.addActionListener(e -> {
            if (menuOpen[0]) {
                viewAllProduction.setVisible(true);
                viewAllActors.setVisible(true);
                addProductionActor.setVisible(true);
                updateProductionActor.setVisible(true);
                removeProductionActor.setVisible(true);
                solveRequest.setVisible(true);
                addUser.setVisible(true);
                removeUser.setVisible(true);
                menuOpen[0] = false;
            } else {
                viewAllProduction.setVisible(false);
                viewAllActors.setVisible(false);
                addProductionActor.setVisible(false);
                updateProductionActor.setVisible(false);
                removeProductionActor.setVisible(false);
                solveRequest.setVisible(false);
                addUser.setVisible(false);
                removeUser.setVisible(false);
                menuOpen[0] = true;
            }
        });

        panel.add(welcome);
        frame.add(search);
        frame.add(menu);
        frame.add(movies);
        frame.add(series);
        frame.add(scrollPane);
        frame.add(settingsButton);
        frame.add(searchButton);
        frame.add(logoutButton);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton){
            frame.dispose();
            SwingInterface swingInterface = new SwingInterface(imdb);
        } else if (e.getSource() == settingsButton) {
            InterfaceMethodes interfaceMethodes= new InterfaceMethodes();
            interfaceMethodes.settings(this);
        } else if (e.getSource() == searchButton) {
            String text = search.getText();
            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a text!");
            } else {
                imdb.searchInSystemUI(text, this);
            }
        } else if (e.getSource() == viewAllProduction) {
            imdb.viewAllProductionUI(this);
        } else if (e.getSource() == viewAllActors){
            imdb.viewAllActorsUI(this);
        } else if (e.getSource() == removeUser) {
            this.removeUserUI();
        } else if (e.getSource() == addUser) {
            this.addUserUI();
        } else if (e.getSource() == removeProductionActor) {
            this.removeProductionUI(imdb);
        } else if (e.getSource() == addProductionActor) {
            this.addProductionUI(imdb);
        } else if (e.getSource() == solveRequest) {
            this.solveRequestUI(imdb);
        } else if (e.getSource() == updateProductionActor) {
            this.updateProductionUI(imdb);
        }
    }
    private void terminalCommands(){
        System.out.println("1) View all productions");
        System.out.println("2) View all actors");
        System.out.println("3) View all notification");
        System.out.println("4) Search for a movie/series/actor");
        System.out.println("5) Add a new movie/series in system");
        System.out.println("6) Add a new actor in system");
        System.out.println("7) Add a new movie/series/actor in favorite list");
        System.out.println("8) Remove a movie/series from system");
        System.out.println("9) Remove a actor from system");
        System.out.println("10) Remove a movie/series/actor from favorite list");
        System.out.println("11) Add an account");
        System.out.println("12) Remove an account");
        System.out.println("13) Update a production");
        System.out.println("14) Update an actor");
        System.out.println("15) Update your account");
        System.out.println("16) Solve a request");
        System.out.println("17) Log out");
    }


    @Override
    public void UserTerminal(IMDB imdb) {
        this.imdb = imdb;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome back, " + this.getInformation().getName() + "!");
        System.out.println("Username: " + this.getUsername());
        System.out.println("Experience: - " );
        System.out.println("Chose one command:");
        while (true) {
            try {
                this.terminalCommands();
                int number = scanner.nextInt();
                if (number == 1) {
                    imdb.showAllProductions();
                } else if (number == 2) {
                    imdb.showAllActors();
                } else if (number == 3) {
                    this.viewAllNotification();
                } else if (number == 4) {
                    imdb.searchInSystem();
                } else if (number == 5) {
                    Production production = this.createProduction();
                    if (production != null) {
                        this.addProductionSystem(production, imdb);
                        this.addContribution(production);
                    }
                } else if (number == 6) {
                    Actor actor = this.createActor();
                    if (actor != null) {
                        this.addActorSystem(actor, imdb);
                        this.addContribution(actor);
                    }
                } else if (number == 7) {
                    this.addFavorable(imdb.getActors(), imdb.getProductions());
                } else if (number == 8) {
                    Production production = imdb.findProduction();
                    if (production != null) {
                        if (this.getContributions().contains(production)) {
                            this.removeProductionSystem(production, imdb);
                            this.removeContributionsSystem(production, imdb);
                            this.removeFavoritesSystem(production, imdb);
                        } else {
                            System.out.println("You are not allowed to remove this production!");
                        }
                    }
                } else if (number == 9) {
                    Actor actor = imdb.findActor();
                    if (actor != null) {
                        if (this.getContributions().contains(actor)) {
                            this.removeActorSystem(actor, imdb);
                            this.removeContributionsSystem(actor, imdb);
                            this.removeFavoritesSystem(actor, imdb);
                        } else {
                            System.out.println("You are not allowed to remove this actor!");
                        }
                    }
                } else if (number == 10) {
                    this.removeFavorable(imdb.getActors(), imdb.getProductions());
                } else if (number == 11) {
                    this.addAccount();
                } else if (number == 12) {
                    this.removeAccount();
                } else if (number == 13) {
                    this.updateProduction(imdb);
                } else if (number == 14) {
                    this.updateActor(imdb);
                } else if (number == 15) {
                    this.updateUser();
                } else if (number == 16) {
                    this.solveRequest();
                } else if (number == 17) {
                    break;
                } else {
                    System.out.println("Please select a valid command");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please select a valid command");
                scanner.nextLine();
            }
        }
    }

    private String makeUsername(String name, List<User<?>> users) {
        Random random = new Random();
        int number =  1 + random.nextInt(9998);
        name = name.toLowerCase();
        name = name.replace(" ", "_");
        name = name + "_" + number;
        for (User<?> user : users) {
            if (user.getUsername().equals(name)) {
                return this.makeUsername(name, users);
            }
        }
        return name;
    }
    private String makePassword(String name) {
        Random random = new Random();
        int number =  1 + random.nextInt(98);
        String characters = "!@#$%&*";
        String str = String.valueOf(number);
        str = str + characters.charAt(number % 8);
        name = name.replace(" ", str);
        name = name.replace("o", "0");
        name = name.replace("i", "!");
        name = name.replace("e", "3");
        name = name.replace("a", "@");
        name = name.replace("u", "v");

        return name;
    }

    private void solveRequest(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            try {
                System.out.println("1) View all requests for admins");
                System.out.println("2) View all requests for you");
                System.out.println("3) Go back");
                int number = scanner.nextInt();
                if (number == 1) {
                    if (RequestHolder.getRequests().isEmpty())
                        System.out.println("There is no request for admins");
                    else
                        this.manageRequest(RequestHolder.getRequests(), imdb);
                } else if (number == 2) {
                    if (this.getRequests().isEmpty())
                        System.out.println("You don't have any requests for you");
                    else
                        this.manageRequest(this.getRequests(), imdb);
                } else if (number == 3) {
                    break;
                } else {
                    System.out.println("Please select a valid command");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please select a valid command");
                scanner.nextLine();
            }
        }
    }

    private void addAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of user: ");
        String name = scanner.nextLine();
        if (name.contains("\\d+")) {
            System.out.println("Invalid name!");
            return;
        }
        System.out.println("Enter the email : ");
        String email = scanner.nextLine();
        if (!email.contains("@")) {
            System.out.println("Invalid email!");
            return;
        }
        String username = this.makeUsername(name, imdb.getUsers());
        String password = this.makePassword(name);
        CredentialsInput credentialsInput = new CredentialsInput(email, password);
        while (true) {
            try {
                System.out.println("Enter account type: ");
                System.out.println("1) Regular");
                System.out.println("2) Contributor");
                System.out.println("3) Admin");
                int number = scanner.nextInt();
                scanner.nextLine();
                if (number == 1) {
                    User<?> user = UserFactory.factory(AccountType.Regular, name, credentialsInput);
                    imdb.getUsers().add(user);
                    imdb.getNotificationSubject().addObserver(user);
                    break;
                } else if (number == 2) {
                    User<?> user = UserFactory.factory(AccountType.Contributor, name, credentialsInput);
                    imdb.getUsers().add(user);
                    imdb.getNotificationSubject().addObserver(user);
                    break;
                } else if (number == 3) {
                    User<?> user = UserFactory.factory(AccountType.Admin, name, credentialsInput);
                    imdb.getUsers().add(user);
                    imdb.getNotificationSubject().addObserver(user);
                    break;
                } else {
                    System.out.println("Please select a valid command");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid account type!");
                scanner.nextLine();
            }
        }
        System.out.println("Account created successfully!");
        System.out.println("Username is: " + username);
        System.out.println("Password is: " + password);
    }

    @SuppressWarnings("unchecked")
    private void removeAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the username of the account you want to remove: ");
        String username = scanner.nextLine();
        for (User<?> user : imdb.getUsers()) {
            if (user.getUsername().equals(username)) {
                if (user.getUserType() == AccountType.Admin) {
                    System.out.println("You are not allowed to remove admin account!");
                    return;
                } else if (user.getUserType().equals(AccountType.Contributor)) {
                    for (Object object : ((Staff<?>) user).getContributions()) {
                        for (User<?> user1 : imdb.getUsers()) {
                            if (user1.getUserType().equals(AccountType.Admin)) {
                                ((Staff<T>) user1).addContribution(object);
                            }
                        }
                    }
                }
                for (Production production : imdb.getProductions()) {
                    for (Rating rating : production.getRatings()) {
                        if (rating.getUsername().equals(username)) {
                            production.getRatings().remove(rating);
                            break;
                        }
                    }
                }
                imdb.getUsers().remove(user);
                System.out.println("Account removed successfully!");
                return;
            }
        }
        System.out.println("Account does not exist!");
    }

    public void addUserUI(){
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(600, 400, "Add user");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel label = interfaceMethodes.createLabel("Enter the name of user:", 70, 30, 250, 50, "#E1DE07");
        label.setFont(new Font("Impact Regular", Font.BOLD, 18));

        JLabel label1 = interfaceMethodes.createLabel("Enter the email of user:", 70, 80, 250, 50, "#E1DE07");
        label1.setFont(new Font("Impact Regular", Font.BOLD, 18));

        JLabel label2 = interfaceMethodes.createLabel("Select account type:", 70, 130, 250, 50, "#E1DE07");
        label2.setFont(new Font("Impact Regular", Font.BOLD, 18));

        JTextField textField = interfaceMethodes.createTextField(290, 40, 250, 30);
        textField.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JTextField textField1 = interfaceMethodes.createTextField(290, 90, 250, 30);
        textField1.setFont(new Font("Impact Regular", Font.BOLD, 15));

        String[] accountTypes = {"Regular", "Contributor", "Admin"};

        JComboBox<String> accountType = new JComboBox<>(accountTypes);
        accountType.setBounds(290, 140, 250, 30);
        accountType.setFont(new Font("Impact Regular", Font.BOLD, 15));

        JButton addButton = interfaceMethodes.createButton("Add", 250, 200, 100, 30, "#2F4F4F", "#F53F20", null);
        addButton.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        addButton.addActionListener(e -> {
            String name = textField.getText();
            String email = textField1.getText();
            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a name and an email!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String username = this.makeUsername(name, imdb.getUsers());
                String password = this.makePassword(name);
                CredentialsInput credentialsInput = new CredentialsInput(email, password);
                if (accountType.getSelectedItem().equals("Regular")) {
                    User<?> user = UserFactory.factory(AccountType.Regular, name, credentialsInput);
                    user.setUsername(username);
                    imdb.getUsers().add(user);
                } else if (accountType.getSelectedItem().equals("Contributor")) {
                    User<?> user = UserFactory.factory(AccountType.Contributor, name, credentialsInput);
                    user.setUsername(username);
                    imdb.getUsers().add(user);
                } else if (accountType.getSelectedItem().equals("Admin")) {
                    User<?> user = UserFactory.factory(AccountType.Admin, name, credentialsInput);
                    user.setUsername(username);
                    imdb.getUsers().add(user);
                }
                JOptionPane.showMessageDialog(null, "Account created successfully!\nUsername is: " + username + "\nPassword is: " + password, "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });

        frame.add(label);
        frame.add(textField);
        frame.add(label1);
        frame.add(label2);
        frame.add(textField1);
        frame.add(accountType);
        frame.add(addButton);
        frame.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    public void removeUserUI(){
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(400, 300, "Remove user");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel label = interfaceMethodes.createLabel("Enter the name of the user:", 70, 5, 500, 50, "#E1DE07");
        label.setFont(new Font("Impact Regular", Font.BOLD, 18));

        List<String> users = new ArrayList<>();
        for (User<?> user : imdb.getUsers()) {
            if (!user.getUserType().equals(AccountType.Admin)) {
                users.add(user.getUsername());
            }
        }

        String[] users1 = users.toArray(new String[0]);
        JComboBox<String> comboBox = new JComboBox<>(users1);
        comboBox.setBounds(70, 60, 250, 30);
        comboBox.setFont(new Font("Impact Regular", Font.BOLD, 15));
        comboBox.setBackground(Color.decode("#000011"));
        comboBox.setFocusable(false);
        comboBox.setForeground(Color.decode("#E1DE07"));

        JButton removeButton = interfaceMethodes.createButton("Remove", 150, 120, 100, 30, "#2F4F4F", "#F53F20", null);
        removeButton.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

        removeButton.addActionListener(e -> {
            String username = (String) comboBox.getSelectedItem();
            for (User<?> user : imdb.getUsers()) {
                if (user.getUsername().equals(username)) {
                    if (user.getUserType().equals(AccountType.Contributor)) {
                        for (Object object : ((Staff<?>) user).getContributions()) {
                            for (User<?> user1 : imdb.getUsers()) {
                                if (user1.getUserType().equals(AccountType.Admin)) {
                                    ((Staff<T>) user1).addContribution(object);
                                }
                            }
                        }
                    }
                    for (Production production : imdb.getProductions()) {
                        for (Rating rating : production.getRatings()) {
                            if (rating.getUsername().equals(username)) {
                                production.getRatings().remove(rating);
                                break;
                            }
                        }
                    }
                    for (Request request : RequestHolder.getRequests()) {
                        if (request.getUsername().equals(username)) {
                            RequestHolder.getRequests().remove(request);
                            break;
                        }
                    }
                    if (!user.getUserType().equals(AccountType.Regular))
                        ((Staff<?>) user).getRequests().removeIf(request -> request.getUsername().equals(username));
                    imdb.getUsers().remove(user);
                    JOptionPane.showMessageDialog(null, "Account removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    return;
                }
            }
        });

        frame.add(label);
        frame.add(comboBox);
        frame.add(removeButton);
        frame.setVisible(true);
    }
}