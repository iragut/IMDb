package org.example.terminal;

import org.example.IMDB;
import org.example.user.User;

import java.util.Scanner;

public class Terminal {
    public void Menu (IMDB imdb){
        Scanner scanner = new Scanner(System.in);
        String email, password;
        boolean accountExists = false;
        System.out.println("Welcome back! Enter your credentials!");
        System.out.println();
        while (true) {
            accountExists = false;
            System.out.print("email: ");
            email = scanner.nextLine();
            for (int i = 0; i < imdb.getUsers().size(); i++) {
                User<?> user = imdb.getUsers().get(i);
                if (email.equals("Exit")) {
                    System.out.println("Bye!");
                    System.exit(0);
                }
                if (user.getInformation().getCredentials().getEmail().equals(email)) {
                    accountExists = true;
                    System.out.print("password: ");
                    password = scanner.nextLine();
                    if (user.getInformation().getCredentials().getPassword().equals(password)) {
                        user.UserTerminal(imdb);
                        break;
                    } else {
                        System.out.println("Wrong password!");
                    }
                }
            }
            if (!accountExists) {
                System.out.println("Account does not exist!");
            }
        }
    }
}
