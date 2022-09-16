package view;

import lib.OptionInput;

import java.io.IOException;
import java.util.Scanner;

public class Authentication {
    public void view() throws IOException, InterruptedException {
        Menu menu = new Menu();
        AdminMenu adminMenu = new AdminMenu();
        System.out.println("\n====================================================== Administrator or Customer option? ======================================================");
        System.out.println("1. Customer");
        System.out.println("2. Admin");
        String option = OptionInput.input();

        switch (option) {
            case "1" -> menu.view();
            case "2" -> adminMenu.view();
            default -> {
                System.out.println("Sorry, there is no match option. Please enter again");
                this.view();
            }

        }
    }
}
