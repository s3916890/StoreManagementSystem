import controller.Account;
import lib.crud.Write;
import view.Menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
//        menu.view();

        Account account = new Account();

        account.register(account.userNameRegisterInput(), account.passwordRegisterInput(), account.fullNameInput(), account.phoneNumberInput(), account.totalSpendingInput());

        File file = new File("users.txt");

//        System.out.println(file.length());
//        new Write().write("users.txt", ",", "id", "loi");

//        System.out.println(Account.getAllUserName());
    }

}
