package view;

import controller.Account;
import lib.OptionInput;
import lib.crud.Read;

import java.io.IOException;

public class Menu {
    public void view() throws IOException {
        System.out.println("\n===================================================================== WELCOME TO APPLE STORE MANAGEMENT SYSTEM !!! =====================================================================");
        System.out.println("""
                    1. Register
                    2. Login
                    3. Developer only
                    4. Exit""");

        Account user = new Account();
        Menu menu = new Menu();
        AdminMenu adMenu = new AdminMenu();
        String option = OptionInput.input();

        switch (option) {
            case "1" -> {
                user.register(user.userNameRegisterInput(), user.passwordRegisterInput(), user.fullNameInput(), user.phoneNumberInput(), user.totalSpendingInput());
            }
            case "2" -> {
                boolean isLogged = false;
                System.out.println("Login Status: "+ isLogged);
                if(user.verifyLogin(user.userNameLoginInput(), user.passwordLoginInput(), "users.txt", ",")){
                    String[] data = Read.getSpecificLine(user.getUserName(), 1, "users.txt", ",");
                    System.out.println("\nUsername: " + data[1] + "\nFull name: " + data[3] + "\nPhone-number: " + data[4]);
                    isLogged = true;
                }
                else{
                    System.out.println("Wrong password, try again bro !!!!");
                    user.login(user.userNameLoginInput(), user.passwordLoginInput());
                }
                System.out.println("Login Status: " + isLogged);
            }
            case "3" -> {
                adMenu.adView();
            }
            case "4" -> {
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
