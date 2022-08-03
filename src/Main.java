import controller.Account;
import lib.time.DateAndTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Account account = new Account();

        account.register(account.userNameInput(), account.passwordInput(), account.fullNameInput(), account.phoneNumberInput());
    }
}
