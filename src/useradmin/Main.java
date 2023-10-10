package useradmin;

import ui.ConsoleColors;
import ui.UI;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;


public class Main {
    final String FILENAME = "Package: useradmin, Filename: users.txt";
    ArrayList<User> users;
    boolean keepRunning;
    boolean validInput;
    boolean error;
    File viewUserList;
    Scanner scan;
    int id;

    // Initializes variables
    private void initVar() {
        viewUserList = new File("userlist.txt");
        users = new ArrayList<>();
        keepRunning = true;
        validInput = false;
        error = false;
        scan = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new Main().run();
    }

    // Run method
    private void run() {
        initVar();
        UI.println(FILENAME + "\n");

        while (keepRunning) {
            Menu.menu();
            chooseMenuOption();
        }
    }

    // Choose menu option
    private void chooseMenuOption() {
        int answer = UI.promptInt();
        switch (answer) {
            case 1 -> viewUserList();
            case 2 -> createNewUser();
            case 3 -> deleteUser();
            case 4 -> saveFile();
            case 5 -> readFile();
            case 0 -> keepRunning = false;
        }
    }

    // View users in 'users' ArrayList
    private void viewUserList() {
        if (users.isEmpty())
            System.out.println(ConsoleColors.RED + "List is empty" + ConsoleColors.RESET);
        else {
            for (int i = 0; i < users.size(); i++) {
                UI.println(ConsoleColors.YELLOW + i + ConsoleColors.RESET + ": " + users.get(i).toString());
            }
        }
        UI.println("");
    }

    // Add new user to 'users' ArrayList
    private void createNewUser() {
        while (!validInput) {
            UI.print("Enter ID: ");
            if (scan.hasNextInt()) {
                id = scan.nextInt();
                validInput = true;
            } else {
                UI.println("Invalid ID. Please enter a valid int.");
                scan.next();
            }
        }
        UI.promptString(); // Scanner bug

        UI.print("Enter name: ");
        String name = UI.promptString();

        UI.print("Enter password: ");
        String password = UI.promptString();

        users.add(new User(id, name, password));
        UI.println("");
    }

    // Delete user from 'users' ArrayList
    private void deleteUser() {
        try {
            UI.println("Enter element ID");
            int n = UI.promptInt();
            UI.println(users.get(n).toString() + " removed");
            users.remove(n);
        } catch (IndexOutOfBoundsException e) {
            UI.println(ConsoleColors.RED + "User not found" + ConsoleColors.RESET);
        }
        UI.println("");
    }

    // Save users to 'users.txt' file
    private void saveFile() {
        for (User user : users) {
            try {
                FileWriter createUser = new FileWriter("userlist.txt", true); // True doesn't overwrite any existing code, and if the file doesn't exist, it will create a new.
                createUser.write(user.getId() + "\n" + user.getName() + "\n" + user.getPassword() + "\n\n");
                createUser.close();
            } catch (IOException e) {
                UI.println("An error occurred");
                e.printStackTrace();
                error = true;
            }
        }
        if (!error)
            UI.println(ConsoleColors.GREEN_BRIGHT + "Successfully saved file\n" + ConsoleColors.RESET);
    }

    // Read file from 'users.txt'
    private void readFile() {
        try {
            Scanner readUserList = new Scanner(viewUserList);

            while (readUserList.hasNextLine()) {
                UI.println(readUserList.nextLine());
            }
            readUserList.close();
        } catch (FileNotFoundException e) {
            UI.println("An error occurred");
            e.printStackTrace();
        }
    }
}
