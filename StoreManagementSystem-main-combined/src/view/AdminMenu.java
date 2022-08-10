package view;

import controller.Account;
import lib.OptionInput;
import java.io.IOException;

public class AdminMenu {
    public void adView() throws IOException{
        System.out.println("\n===================================================================== WELCOME TO APPLE STORE DEVELOPING SYSTEM !!! =====================================================================");
        System.out.println("""
                    1. Login (Developing account only)
                    2. Back to main menu""");
        Account admin = new Account();
        Menu menu = new Menu();
        AdminMenu adMenu = new AdminMenu();
        String option = OptionInput.input();

        switch (option){
            case "1" -> {
                boolean isLogged = false;
                System.out.println("Login Status: "+ false);
                if(admin.verifyAdmin(admin.adNameLoginInput(), admin.passwordLoginInput())){
                    System.out.println("Welcome back sir !!!");
                    isLogged = true;
                }
                else {
                    System.out.println("The developer name is not available, please try again !!!");
                    admin.login(admin.adNameLoginInput(), admin.passwordLoginInput());
                }
                System.out.println("Login Status: " + isLogged);
            }
            case "2" -> {
                menu.view();
            }
            default -> {
                System.out.println("There is no match option, please enter again");
                adMenu.adView();
            }
        }
    }
}
