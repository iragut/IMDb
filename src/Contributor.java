package org.example;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.entities.Actor;
import org.example.entities.Production;
import org.example.enums.AccountType;
import org.example.enums.RequestTypes;
import org.example.input.account.CredentialsInput;
import org.example.input.account.Information;
import org.example.interfaces.InterfaceMethodes;
import org.example.interfaces.SwingInterface;
import org.example.request.Request;
import org.example.request.RequestHolder;
import org.example.request.RequestsManager;
import org.example.strategy.ExperienceStrategy;
import org.example.user.Staff;
import org.example.user.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
public class Contributor<T extends Comparable<T>> extends Staff<T> implements RequestsManager, ActionListener {
    private ExperienceStrategy experienceStrategy;
    private IMDB imdb;
    private JFrame frame;
    private JPanel panel;
    private JTextField search;
    private JLabel welcome, movies, series;
    private JButton logoutButton ,settingsButton, searchButton, menu, viewAllProduction, viewAllActors, createRequest;
    private JButton addProduction, removeProduction, updateProductionActor, solveRequest, deleteRequest;

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

        viewAllProduction = interfaceMethodes.createButton("View all productions", 910, 40, 180, 40,
                "#1f1f1f", "#ffffff", this);
        viewAllProduction.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));
        viewAllProduction.setName("Menu");
        viewAllActors = interfaceMethodes.createButton("View all actors", 910, 80, 180, 40,
                "#1f1f1f", "#ffffff", this);
        viewAllActors.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));
        viewAllActors.setName("Menu");

        addProduction = interfaceMethodes.createButton("Add Prod/Actor", 910, 120, 180, 40,
                "#1f1f1f", "#ffffff", this);
        addProduction.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));

        updateProductionActor = interfaceMethodes.createButton("Update Prod/Actor", 910, 160, 180, 40,
                "#1f1f1f", "#ffffff", this);
        updateProductionActor.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));

        removeProduction = interfaceMethodes.createButton("Remove Prod/Actor", 910, 200, 180, 40,
                "#1f1f1f", "#ffffff", this);
        removeProduction.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));

        solveRequest = interfaceMethodes.createButton("Solve request", 910, 240, 180, 40,
                "#1f1f1f", "#ffffff", this);
        solveRequest.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));

        createRequest = interfaceMethodes.createButton("Create request", 910, 280, 180, 40,
                "#1f1f1f", "#ffffff", this);
        createRequest.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));

        deleteRequest = interfaceMethodes.createButton("Delete your request", 910, 320, 180, 40,
                "#1f1f1f", "#ffffff", this);
        deleteRequest.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));

        frame.add(viewAllProduction);
        frame.add(viewAllActors);
        frame.add(addProduction);
        frame.add(updateProductionActor);
        frame.add(removeProduction);
        frame.add(solveRequest);
        frame.add(createRequest);
        frame.add(deleteRequest);
        viewAllProduction.setVisible(false);
        viewAllActors.setVisible(false);
        addProduction.setVisible(false);
        updateProductionActor.setVisible(false);
        removeProduction.setVisible(false);
        solveRequest.setVisible(false);
        createRequest.setVisible(false);
        deleteRequest.setVisible(false);

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
                addProduction.setVisible(true);
                updateProductionActor.setVisible(true);
                removeProduction.setVisible(true);
                solveRequest.setVisible(true);
                createRequest.setVisible(true);
                deleteRequest.setVisible(true);
                menuOpen[0] = false;
            } else {
                viewAllProduction.setVisible(false);
                viewAllActors.setVisible(false);
                addProduction.setVisible(false);
                updateProductionActor.setVisible(false);
                removeProduction.setVisible(false);
                solveRequest.setVisible(false);
                createRequest.setVisible(false);
                deleteRequest.setVisible(false);
                menuOpen[0] = true;
            }
            frame.repaint();
        });

        panel.add(welcome);
        frame.add(search);
        frame.add(movies);
        frame.add(series);
        frame.add(scrollPane);
        frame.add(settingsButton);
        frame.add(searchButton);
        frame.add(logoutButton);
        frame.add(panel);
        frame.add(menu);
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
        } else if (e.getSource() == removeProduction) {
            this.removeProductionUI(imdb);
        } else if (e.getSource() == addProduction) {
            this.addProductionUI(imdb);
        } else if (e.getSource() == solveRequest) {
            this.solveRequestUI(imdb);
        } else if (e.getSource() == createRequest) {
            this.createRequestUI(imdb);
        } else if (e.getSource() == deleteRequest) {
            this.removeRequestUI(imdb);
        } else if (e.getSource() == updateProductionActor) {
            this.updateProductionUI(imdb);
        }
    }

    public void removeRequestUI(IMDB imdb){
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(500, 500, "Delete request");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel label = interfaceMethodes.createLabel("Your request:", 175, 5, 500, 50, "#E1DE07");
        label.setFont(new Font("Impact Regular", Font.BOLD, 20));

        List<Request> requests = new ArrayList<>();
        for (Request request : RequestHolder.getRequests()){
            if (request.getUsername().equals(this.getUsername())){
                requests.add(request);
            }
        }
        for (User<?> user : imdb.getUsers()){
            if (!user.getUserType().equals(AccountType.Regular)){
                for (Request request : ((Staff<?>) user).getRequests()){
                    if (request.getUsername().equals(this.getUsername())){
                        requests.add(request);
                    }
                }
            }
        }

        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Request value : requests) {
            listModel.addElement("ID: " + value.getId() + " | " + value.getType());
        }
        if (listModel.isEmpty()){
            listModel.addElement("You don't have any requests!");
        }
        JList<String> list = new JList<>(listModel);
        list.setBackground(Color.decode("#1f1f1f"));
        list.setForeground(Color.decode("#ffffff"));
        list.setFont(new Font("Impact Regular", Font.BOLD, 15));
        list.setFocusable(false);
        list.addListSelectionListener(e -> {
            if (list.getSelectedValue() != null && !e.getValueIsAdjusting()){
            String request = list.getSelectedValue();
            int id = Integer.parseInt(request.substring(4, request.indexOf(" |")));
            for ( Request request1 : RequestHolder.getRequests()){
                if (request1.getId() == id){
                    request1.printRequestUI(imdb, listModel, request, false);
                    break;
                }
            }
            for (User<?> user : imdb.getUsers()){
                if (!user.getUserType().equals(AccountType.Regular)){
                    for (Request request1 : ((Staff<?>) user).getRequests()){
                        if (request1.getId() == id){
                            request1.printRequestUI(imdb, listModel, request, false);
                            break;
                        }
                    }
                }
            }}
        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(20, 80, 450, 300);
        scrollPane.setBorder(new LineBorder(Color.decode("#48E5FB"), 2));
        scrollPane.setBackground(Color.decode("#1f1f1f"));

        frame.add(scrollPane);
        frame.add(label);
        frame.setVisible(true);
    }

    public void createRequestUI(IMDB imdb) {
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(500, 500, "Create request");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel label = interfaceMethodes.createLabel("What type the request you want?",
                100, 5, 500, 50, "#E1DE07");
        label.setFont(new Font("Impact Regular", Font.BOLD, 20));

        int productionSize = 0;
        int actorSize = 0;

        for (Production production : imdb.getProductions()){
            if (this.getContributions().contains(production))
                productionSize++;
        }

        for (Actor actor : imdb.getActors()){
            if (this.getContributions().contains(actor))
                actorSize++;
        }

        String[] options = {"Delete account", "Movie issue", "Actor issue", "Other"};
        String[] movieOptions = new String[imdb.getProductions().size() - productionSize];
        String[] actorOptions = new String[imdb.getActors().size() - actorSize];
        int j = 0;
        for (int i = 0; i < imdb.getProductions().size(); i++) {
            if (!this.getContributions().contains(imdb.getProductions().get(i))) {
                movieOptions[j] = imdb.getProductions().get(i).getTitle();
                j++;
            }
        }
        j = 0;
        for (int i = 0; i < imdb.getActors().size(); i++) {
            if (!this.getContributions().contains(imdb.getActors().get(i))){
                actorOptions[j] = imdb.getActors().get(i).getName();
                j++;
            }
        }
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setBounds(150, 60, 200, 30);
        comboBox.setFont(new Font("Impact Regular", Font.BOLD, 15));
        comboBox.setFocusable(false);
        comboBox.addActionListener(e -> {
            Component[] components = frame.getContentPane().getComponents();
            for (Component component : components) {
                if (component.getName() != null) {
                    if (component.getName().equals("movie") || component.getName().equals("actor")
                            || component.getName().equals("other") || component.getName().equals("delete")) {
                        frame.remove(component);
                        frame.repaint();
                    }
                }
            }
            if (comboBox.getSelectedItem().equals("Delete account")) {
                JLabel description = interfaceMethodes.createLabel("Describe your request: ",
                        150, 100, 500, 50, "#E1DE07");
                description.setFont(new Font("Impact Regular", Font.BOLD, 20));
                description.setName("delete");

                JTextField descriptionField = interfaceMethodes.createTextField(100, 160, 300, 30);
                descriptionField.setFont(new Font("Impact Regular", Font.BOLD, 15));
                descriptionField.setName("delete");

                frame.add(description);
                frame.add(descriptionField);
                frame.repaint();
            } else if (comboBox.getSelectedItem().equals("Movie issue")) {
                JLabel movieLabel = interfaceMethodes.createLabel("Select a movie/series: ",
                        150, 100, 500, 50, "#E1DE07");
                movieLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));
                movieLabel.setName("movie");

                JComboBox<String> movieBox = new JComboBox<>(movieOptions);
                movieBox.setBounds(100, 160, 300, 30);
                movieBox.setFont(new Font("Impact Regular", Font.BOLD, 15));
                movieBox.setName("movie");
                movieBox.setFocusable(false);

                JLabel description = interfaceMethodes.createLabel("Describe your request: ",
                        150, 200, 500, 50, "#E1DE07");
                description.setFont(new Font("Impact Regular", Font.BOLD, 20));
                description.setName("movie");

                JTextField descriptionField = interfaceMethodes.createTextField(100, 260, 300, 30);
                descriptionField.setFont(new Font("Impact Regular", Font.BOLD, 15));
                descriptionField.setName("movie");

                frame.add(movieLabel);
                frame.add(description);
                frame.add(descriptionField);
                frame.add(movieBox);
                frame.repaint();
            } else if (comboBox.getSelectedItem().equals("Actor issue")) {
                JLabel actorLabel = interfaceMethodes.createLabel("Select an actor: ",
                        170, 100, 500, 50, "#E1DE07");
                actorLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));
                actorLabel.setName("actor");

                JComboBox<String> actorBox = new JComboBox<>(actorOptions);
                actorBox.setBounds(100, 160, 300, 30);
                actorBox.setFont(new Font("Impact Regular", Font.BOLD, 15));
                actorBox.setFocusable(false);
                actorBox.setName("actor");

                JLabel description = interfaceMethodes.createLabel("Describe your request: ",
                        150, 200, 500, 50, "#E1DE07");
                description.setFont(new Font("Impact Regular", Font.BOLD, 20));
                description.setName("actor");

                JTextField descriptionField = interfaceMethodes.createTextField(100, 260, 300, 30);
                descriptionField.setFont(new Font("Impact Regular", Font.BOLD, 15));
                descriptionField.setName("actor");

                frame.add(actorLabel);
                frame.add(actorBox);
                frame.add(description);
                frame.add(descriptionField);
                frame.repaint();
            } else if (comboBox.getSelectedItem().equals("Other")) {
                JLabel description = interfaceMethodes.createLabel("Describe your request: ",
                        150, 100, 500, 50, "#E1DE07");
                description.setFont(new Font("Impact Regular", Font.BOLD, 20));
                description.setName("other");

                JTextField descriptionField = interfaceMethodes.createTextField(100, 160, 300, 30);
                descriptionField.setFont(new Font("Impact Regular", Font.BOLD, 15));
                descriptionField.setName("other");

                frame.add(description);
                frame.add(descriptionField);
                frame.repaint();
            }
        });

        JButton create = interfaceMethodes.createButton("Create", 200, 400, 100, 30,
                "#1f1f1f", "#ffffff", null);
        create.addActionListener(e -> {
            for (Component component : frame.getContentPane().getComponents()) {
                if (component instanceof JTextField ) {
                    if (((JTextField) component).getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please complete all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
            if (comboBox.getSelectedItem().equals("Delete account")) {
                for (Component component : frame.getContentPane().getComponents()) {
                    if (component instanceof JTextField && component.getName() != null && component.getName().equals("delete")) {
                        Request request = new Request();
                        Date date = new Date();

                        request.setType(RequestTypes.DELETE_ACCOUNT);
                        request.setUsername(this.getUsername());
                        request.setCreatedDate(date);
                        request.setDescription(((JTextField) component).getText());
                        request.setTo("ADMIN");
                        request.setId(imdb.getRequestID());
                        imdb.setRequestID(imdb.getRequestID() + 1);

                        RequestHolder.addRequest(request);
                        JOptionPane.showMessageDialog(null, "Request created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    }
                }
            } else if (comboBox.getSelectedItem().equals("Movie issue")) {
                for (Component component : frame.getContentPane().getComponents()) {
                    if (component instanceof JComboBox && component.getName() != null && component.getName().equals("movie")) {
                        String movieTitle = (String) ((JComboBox<?>) component).getSelectedItem();
                        String description = ((JTextField) frame.getContentPane().getComponent(5)).getText();
                        Request request = new Request();
                        Date date = new Date();

                        request.setUsername(this.getUsername());
                        request.setCreatedDate(date);
                        request.setMovieTitle(movieTitle);
                        request.setTo(imdb.findContributor(imdb.findProduction(movieTitle)));
                        imdb.getNotificationSubject().notifyObservers(imdb.findUser(request.getTo()), "You have a new request!");
                        request.setDescription(description);
                        request.setType(RequestTypes.MOVIE_ISSUE);
                        request.setId(imdb.getRequestID());
                        imdb.setRequestID(imdb.getRequestID() + 1);

                        imdb.addRequestInSystem(request);
                        JOptionPane.showMessageDialog(null, "Request created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    }
                }
            } else if (comboBox.getSelectedItem().equals("Actor issue")) {
                for (Component component : frame.getContentPane().getComponents()) {
                    if (component instanceof JComboBox && component.getName() != null && component.getName().equals("actor")) {
                        String actorName = (String) ((JComboBox<?>) component).getSelectedItem();
                        String description = ((JTextField) frame.getContentPane().getComponent(6)).getText();
                        Request request = new Request();
                        Date date = new Date();

                        request.setUsername(this.getUsername());
                        request.setCreatedDate(date);
                        request.setActorName(actorName);
                        request.setTo(imdb.findContributor(imdb.findActor(actorName)));
                        imdb.getNotificationSubject().notifyObservers(imdb.findUser(request.getTo()), "You have a new request!");
                        request.setDescription(description);
                        request.setType(RequestTypes.ACTOR_ISSUE);
                        request.setId(imdb.getRequestID());
                        imdb.setRequestID(imdb.getRequestID() + 1);

                        imdb.addRequestInSystem(request);
                        JOptionPane.showMessageDialog(null, "Request created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    }
                }
            } else if (comboBox.getSelectedItem().equals("Other")) {
                for (Component component : frame.getContentPane().getComponents()) {
                    if (component instanceof JTextField && component.getName() != null && component.getName().equals("other")) {
                        Request request = new Request();
                        Date date = new Date();

                        request.setType(RequestTypes.OTHERS);
                        request.setUsername(this.getUsername());
                        request.setCreatedDate(date);
                        request.setDescription(((JTextField) component).getText());
                        request.setTo("ADMIN");
                        request.setId(imdb.getRequestID());
                        imdb.setRequestID(imdb.getRequestID() + 1);

                        RequestHolder.addRequest(request);
                        JOptionPane.showMessageDialog(null, "Request created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    }
                }
            }
        });
        frame.add(create);
        frame.add(comboBox);
        frame.add(label);
        frame.setVisible(true);
    }

    public Contributor(CredentialsInput credentialsInput, String name) {
        this.setInformation(new Information.InformationBuilder(credentialsInput, name).build());
        this.setUserType(AccountType.Contributor);
        this.setExperience(0);
        this.setContributions(new TreeSet<>());
        this.setNotifications(new ArrayList<>());
        this.setRequests(new ArrayList<>());
        this.setFavorites(new TreeSet<>());

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
        System.out.println("11) Update a production");
        System.out.println("12) Update an actor");
        System.out.println("13) Update your account");
        System.out.println("14) Create a request");
        System.out.println("15) Remove a request");
        System.out.println("16) Solve a request");
        System.out.println("17) Log out");
    }
    @Override
    public void UserTerminal(IMDB imdb) {
        this.imdb = imdb;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome back, " + this.getInformation().getName() + "!");
        System.out.println("Username: " + this.getUsername());
        System.out.println("Experience: " + this.getExperience());
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
                    this.updateProduction(imdb);
                } else if (number == 12) {
                    this.updateActor(imdb);
                } else if (number == 13) {
                    this.updateUser();
                } else if (number == 14) {
                    this.createRequest(imdb);
                } else if (number == 15) {
                    this.removeRequest(imdb);
                } else if (number == 16) {
                    if (this.getRequests().isEmpty())
                        System.out.println("You don't have any requests for you");
                    else
                        this.manageRequest(this.getRequests(), imdb);
                } else if (number == 17) {
                        break;
                } else {
                        System.out.println("Please select a valid command");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please select a valid command");
            }
        }
    }

    @Override
    public void createRequest(IMDB imdb) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What type the request you want?");
        while (true) {
            System.out.println("1) Delete account");
            System.out.println("2) Movie issue");
            System.out.println("3) Actor issue");
            System.out.println("4) Other");
            System.out.println("5) Back");
            try {
                int number = scanner.nextInt();
                Request request = new Request();
                Date date = new Date();
                scanner.nextLine();
                if (number == 1) {
                    request.setType(RequestTypes.DELETE_ACCOUNT);
                    request.setUsername(this.getUsername());
                    request.setCreatedDate(date);
                    request.setTo("ADMIN");
                    request.setDescription("I want to delete my account");
                    RequestHolder.addRequest(request);
                    break;
                } else if (number == 2) {
                    Production production = imdb.findProduction();
                    if (production == null)
                        return;
                    if (this.getContributions().contains(production)) {
                        System.out.println("You are not allowed to send a request for the production what you added!");
                        return;
                    }
                    request.setUsername(this.getUsername());
                    request.setCreatedDate(date);
                    request.setMovieTitle(production.getTitle());
                    request.setTo(imdb.findContributor(production));
                    System.out.println("Enter your request: ");
                    request.setDescription(scanner.nextLine());
                    request.setType(RequestTypes.MOVIE_ISSUE);
                    imdb.addRequestInSystem(request);
                    break;
                } else if (number == 3) {
                    Actor actor = imdb.findActor();
                    if (actor == null)
                        return;
                    if (this.getContributions().contains(actor)) {
                        System.out.println("You are not allowed to send a request for the actor what you added!");
                        return;
                    }
                    request.setUsername(this.getUsername());
                    request.setCreatedDate(date);
                    request.setActorName(actor.getName());
                    request.setTo(imdb.findContributor(actor));
                    System.out.println("Enter your request: ");
                    request.setDescription(scanner.nextLine());
                    request.setType(RequestTypes.ACTOR_ISSUE);
                    imdb.addRequestInSystem(request);
                    break;
                } else if (number == 4) {
                    request.setType(RequestTypes.OTHERS);
                    request.setUsername(this.getUsername());
                    request.setCreatedDate(date);
                    System.out.println("Enter your request: ");
                    request.setDescription(scanner.nextLine());
                    request.setTo("ADMIN");
                    RequestHolder.addRequest(request);
                    break;
                } else if (number == 5) {
                    return;
                } else {
                    System.out.println("Please select a valid command");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please select a valid command");
                scanner.nextLine();
            }
        }
    }

    @Override
    public void removeRequest(IMDB imdb) {
        List<Request> requests = new ArrayList<>();
        System.out.println("Your request");
        for (Request request : imdb.getRequests()){
            if (request.getUsername().equals(this.getUsername())){
                requests.add(request);
                request.printRequest();
            }
        }
        Scanner scanner = new Scanner(System.in);
        if (requests.isEmpty()){
            System.out.println("You don't have any request");
            return;
        }
        System.out.println("Enter the request you want to delete");
        while (true){
            try {
                int number = scanner.nextInt();
                scanner.nextLine();
                if (number < 1 || number > requests.size()) {
                    System.out.println("Please enter a valid command");
                } else {
                    imdb.getRequests().remove(requests.get(number - 1));
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid command");
                scanner.nextLine();

            }
        }
    }
}
