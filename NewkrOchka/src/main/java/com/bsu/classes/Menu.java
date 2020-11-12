package com.bsu.classes;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    private void printMenu() {
        System.out.println("Menu");
        System.out.println("1. Log in");
        System.out.println("2. Sign in");
        System.out.println("3. Check all entries");
        System.out.println("4. Portfolio search by client number(only for ADMIN)");
        System.out.println("5. Client statistic");
        System.out.println("6. Top N clients in ... currency");
        System.out.println("7. Exit");
    }

    public void start(HashMap<String, User> users, HashMap<Integer, Client> clients) {
        if (this.scanner != null) {
            int key;
            boolean logged = false;
            String login = "";
            String password = "";
            do {
                printMenu();
                System.out.print("Enter your request number: ");
                key = this.scanner.nextInt();
                switch (key) {
                    case 1:
                        System.out.println("Enter your details: name, login, email, password:");
                        this.scanner.nextLine();
                        String details = this.scanner.nextLine();
                        User createdUser = User.createUser(details);
                        if (users.containsKey(createdUser.getLogin())) {
                            System.out.println("There is user with that login");
                        } else {
                            users.put(createdUser.getLogin(), createdUser);
                            try (FileWriter writer = new FileWriter("src/main/java/users.txt", true)) {
                                writer.write("\n");
                                writer.write(createdUser.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Success!");
                        }
                        break;
                    case 2:
                        System.out.println("Enter login and password:");
                        this.scanner.nextLine();
                        String loginAndPass = this.scanner.nextLine();
                        String[] signIn = loginAndPass.split(" ");
                        login = signIn[0];
                        password = signIn[1];
                        if (users.containsKey(login) && users.get(login).getPassword().equals(password)) {
                            System.out.println("Welcome " + users.get(login).getName());
                            logged = true;
                        } else System.out.println("Incorrect login or password");
                        break;
                    case 3:
                        if (logged) {
                            System.out.println("All entries:");
                            users.values().forEach(System.out::println);
                        } else System.out.println("you are not logged in.");
                        break;
                    case 4:
                        if (logged) {
                            if (users.get(login).getRole().equals("ADMIN")) {
                                System.out.print("Enter the number of id: ");
                                Integer id = this.scanner.nextInt();
                                if (clients.containsKey(id)) {
                                    System.out.println(clients.get(id).getNameOfPortfolio() + " "
                                            + clients.get(id).getCurrencyList() + " "
                                            + clients.get(id).getSumList());
                                } else System.out.println("There is no portfolio with that id.");
                            } else System.out.println("You are not ADMIN.");
                        } else System.out.println("You are not logged in.");
                        break;
                    case 5:
                        if (logged) {
                            for (Client client : clients.values()) {
                                if (client.getSumList().size() >= 2) {
                                    double max = client.getSumList().get(0) * client.getCurrencyList().get(0).course;
                                    for (int i = 1; i < client.getSumList().size(); ++i) {
                                        double x = client.getSumList().get(i) * client.getCurrencyList().get(i).course;
                                        double y = client.getSumList().get(i - 1) * client.getCurrencyList().get(i - 1).course;
                                        if (x > y) {
                                            max = x;
                                        }
                                    }
                                    System.out.println(client.getNameOfPortfolio() + " " +
                                            client.getCurrencyList().size() + " " + max);
                                } else {
                                    System.out.println(client.getNameOfPortfolio() + " " + client.getCurrencyList().size()
                                            + " " + client.getSumList().get(0) * client.getCurrencyList().get(0).course);
                                }
                            }
                        } else System.out.println("You are not logged in.");
                        break;
                    case 6:
                        if(logged) {
                            System.out.print("Enter currency and N:");
                            String consoleCurrency = this.scanner.next();
                            int n = this.scanner.nextInt();
                            HashMap<Integer, Client> wrongClients = new HashMap<>(clients);
                            Client.Currency currency = Client.Currency.valueOf(consoleCurrency);
                            int index = 0;
                            for (int i = 0; i < n; ++i) {
                                double max = -1;
                                for (Client client : wrongClients.values()) {
                                    if (client.getCurrencyList().contains(currency)) {
                                        int ind = client.getCurrencyList().indexOf(currency);
                                        double courseValue = client.getSumList().get(ind);
                                        if (max < courseValue) {
                                            max = courseValue;
                                            index = client.getId();
                                        }
                                    }
                                }
                                System.out.println(wrongClients.get(index));
                                wrongClients.remove(index);
                            }
                        }
                        else System.out.println("You are not logged in.");
                        break;
                    case 7:
                        System.out.println("End of the program.");
                        break;
                    default:
                        throw new IllegalStateException("Incorrect request");
                }
            } while (key != 7 && logged);
        }
    }
}