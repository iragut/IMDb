package org.example;

import org.example.interfaces.SwingInterface;
import org.example.terminal.Terminal;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int number;
        Scanner scanner = new Scanner(System.in);
        IMDB imdb = IMDB.getInstance();
        System.out.println("Select one:");
        while (true) {
            try {
                System.out.println("1. Terminal");
                System.out.println("2. Interface");
                number = scanner.nextInt();
                if (number < 0 || number > 2) {
                    System.out.println("Please select a valid command");
                    scanner.nextLine();
                } else if (number == 1) {
                    Terminal terminal = new Terminal();
                    terminal.Menu(imdb);
                    break;
                } else {
                    SwingInterface swingInterface = new SwingInterface(imdb);
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please select a valid command");
                scanner.nextLine();
            }
        }
    }
}
