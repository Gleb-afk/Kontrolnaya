package com.bsu.main;

import com.bsu.classes.Client;
import com.bsu.classes.Menu;
import com.bsu.classes.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HashMap<String, User> users = new HashMap<>();
        try (BufferedReader firstFile = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = firstFile.readLine()) != null) {
                User user = User.createUser(line);
                String login = user.getLogin();
                users.put(login, user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<Integer, Client> clients = new HashMap<>();
        try (BufferedReader secondFile = new BufferedReader(new FileReader(args[1]))) {
            String line;
            while ((line = secondFile.readLine()) != null) {
                Client client = Client.createClient(line);
                Integer id = client.getId();
                clients.put(id, client);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        new Menu(new Scanner(System.in)).start(users, clients);
    }
}