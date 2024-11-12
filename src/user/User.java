package org.example.user;

import lombok.Getter;

import lombok.Setter;
import org.example.IMDB;
import org.example.entities.Actor;
import org.example.entities.Production;
import org.example.enums.AccountType;
import org.example.input.account.AccountInput;
import org.example.input.account.Information;
import org.example.observer.Observer;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;


@Setter
@Getter
public abstract class User<T extends Comparable<T>> implements Observer {
    private AccountType userType;
    private String username;
    private int experience;
    private Information information;
    private List<String> notifications;
    private SortedSet<T> favorites;

    public abstract void UserTerminal(IMDB imdb);
    public abstract void UserInterface(IMDB imdb);

    private void printUser(){
        System.out.println("Username: " + username);
        System.out.println("Account type: " + userType);
        System.out.println("Experience: " + experience);
        System.out.println("Name: " + information.getName());
        System.out.println("Country: " + information.getCountry());
        System.out.println("Date of birth: " + information.getBirthDate());
        System.out.println("Age: " + information.getAge());
        System.out.println("Gender: " + information.getGender());
        System.out.println("Email: " + information.getCredentials().getEmail());
        System.out.println("Password: " + information.getCredentials().getPassword());
    }

    public void printFavorites() {
        if (favorites.isEmpty()) {
            System.out.println("You have no favorites!");
        } else {
            System.out.println("Your favorites are: ");
            for (int i = 0; i < favorites.size(); i++) {
                Object object = favorites.toArray()[i];
                if (object instanceof Production production) {
                    production.displayInfo();
                } else if (object instanceof Actor actor) {
                    actor.displayInfo();
                }
            }
        }
    }

    public void addFavorable(List<Actor> actors, List<Production> productions) {
        this.printFavorites();
        Scanner scanner = new Scanner(System.in);
        AccountInput accountInput = new AccountInput();
        System.out.println("1) Add a production");
        System.out.println("2) Add an actor");
        System.out.println("3) Go back");
        int number = scanner.nextInt();
        scanner.nextLine();
        try {
            if (number == 1) {
                System.out.println("Enter the title of the production: ");
                String title = scanner.nextLine();
                for (Production production : productions) {
                    if (production.getTitle().equals(title)) {
                        favorites = accountInput.addFavorite(favorites, production);
                        System.out.println("Added to favorites!");
                        return;
                    }
                }
                System.out.println("Production does not exist!");
            } else if (number == 2) {
                System.out.println("Enter the name of the actor: ");
                String name = scanner.nextLine();
                for (Actor actor : actors) {
                    if (actor.getName().equals(name)) {
                        favorites = accountInput.addFavorite(favorites, actor);
                        System.out.println("Added to favorites!");
                        return;
                    }
                }
                System.out.println("Actor does not exist!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please select a valid command");
        }
    }

    public void removeFavorable(List<Actor> actors, List<Production> productions) {
        this.printFavorites();
        Scanner scanner = new Scanner(System.in);
        System.out.println("1) Remove a production");
        System.out.println("2) Remove an actor");
        System.out.println("3) Go back");
        int number = scanner.nextInt();
        scanner.nextLine();
        try {
            if (number == 1) {
                System.out.println("Enter the title of the production: ");
                String title = scanner.nextLine();
                for (Production production : productions) {
                    if (production.getTitle().equals(title)) {
                        if (favorites.contains(production)) {
                            favorites.remove(production);
                            System.out.println("Removed from favorites!");
                        } else {
                            System.out.println("Production is not in favorites!");
                        }
                        return;
                    }
                }
                System.out.println("Production does not exist!");
            } else if (number == 2) {
                System.out.println("Enter the name of the actor: ");
                String name = scanner.nextLine();
                for (Actor actor : actors) {
                    if (actor.getName().equals(name)) {
                        if (favorites.contains(actor)) {
                            favorites.remove(actor);
                            System.out.println("Removed from favorites!");
                        } else {
                            System.out.println("Actor is not in favorites!");
                        }
                        return;
                    }
                }
                System.out.println("Actor does not exist!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please select a valid command");
        }
    }

    public void viewAllNotification() {
        Scanner scanner = new Scanner(System.in);
        if (this.getNotifications().isEmpty()) {
            System.out.println("You have no notifications!");
        } else {
            for (String notification : this.getNotifications()) {
                System.out.println(notification);
            }
        }
        System.out.println("To go back, press b");
        while (true) {
            String command = scanner.nextLine();
            if (command.equals("b")) {
                break;
            }
        }
    }

    public void updateUser(){
        this.printUser();
        Scanner scanner = new Scanner(System.in);
        System.out.println("1) Change username");
        System.out.println("2) Change password");
        System.out.println("3) Change name");
        System.out.println("4) Change country");
        System.out.println("5) Change age");
        System.out.println("6) Change genre");
        System.out.println("7) Go back");
        int number = scanner.nextInt();
        scanner.nextLine();
        try {
            if (number == 1) {
                System.out.println("Enter the new username: ");
                this.setUsername(scanner.nextLine());
            } else if (number == 2) {
                System.out.println("Enter the new password: ");
                this.getInformation().getCredentials().setPassword(scanner.nextLine());
            } else if (number == 3) {
                System.out.println("Enter the new name: ");
                this.getInformation().setName(scanner.nextLine());
            } else if (number == 4) {
                System.out.println("Enter the new country: ");
                this.getInformation().setCountry(scanner.nextLine());
            } else if (number == 5) {
                System.out.println("Enter the new age: ");
                this.getInformation().setAge(scanner.nextInt());
                scanner.nextLine();
            } else if (number == 6) {
                System.out.println("Enter the new genre: ");
                this.getInformation().setGender(scanner.nextLine());
            }
        } catch (InputMismatchException e) {
            System.out.println("Please select a valid command");
        }
    }
    @Override
    public void update(String notification) {
        this.notifications.add(notification);
    }

}