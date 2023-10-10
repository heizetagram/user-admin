package useradmin;

import ui.UI;

public class Menu {
    public static void menu() {
        UI.println("1. View user list");
        UI.println("2. Create new user");
        UI.println("3. Delete user");
        UI.println("4. Save file");
        UI.println("5. Read file");
        UI.println("0. Quit");
        UI.print("\nPlease choose option: ");
    }
}
