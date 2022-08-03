package view;

import controller.Account;
import lib.OptionInput;
import lib.crud.read.ReadSpecificLine;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    public void view() throws IOException {
        System.out.println("\n===================================================================== WELCOME TO APPLE STORE MANAGEMENT SYSTEM !!! =====================================================================");
        System.out.println("""
                    1. Register
                    2. Login
                    3. Exit""");

        Account user = new Account();
        Menu menu = new Menu();
        String option = OptionInput.input();

        switch (option) {
            case "1" -> {
                user.register(user.userNameInput(), user.passwordInput(), user.fullNameInput(), user.phoneNumberInput());
                new VisitOrNot().view();
            }
            case "2" -> {
                boolean isLogged = false;
                System.out.println("Login Status: "+ isLogged);
                if(user.verifyLogin(user.userNameInput(), user.passwordInput(), "users.txt", ",")){
                    String[] data = ReadSpecificLine.getSpecificLine(user.userNameInput(), 1, "users.txt", ",");
                    System.out.println("\nUsername: " + data[1] + "\nFull name: " + data[3] + "\nPhone-number: " + data[4]);
                    isLogged = true;
                }
                else{
                    System.out.println("Wrong password, try again bro !!!!");
                    user.login(user.userNameInput(), user.passwordInput());
                }
                System.out.println("Login Status: " + isLogged);
            }
            case "3" -> {
                System.out.println("Thank you so much for using our system. See you soon !!!!");
                System.exit(1);
            }
            default -> {
                System.out.println("There is no match option, please enter again");
                menu.view();
            }
        }
    }

}
