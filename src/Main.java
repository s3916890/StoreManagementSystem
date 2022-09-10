import view.AdminMenu;
import view.Menu;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Menu menu = new Menu();
        AdminMenu adminMenu = new AdminMenu();

//        menu.view();
        adminMenu.view();
    }
}
