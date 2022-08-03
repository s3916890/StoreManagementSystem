import controller.Account;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Account account = new Account();

        account.register(account.userNameInput(), account.passwordInput(), account.fullNameInput(), account.phoneNumberInput());

    }
}
