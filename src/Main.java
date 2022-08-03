import controller.Account;
import view.Menu;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        Menu menu = new Menu();
//        menu.view();
        Account account = new Account();
        account.register(account.userNameInput(), account.passwordInput(), account.fullNameInput(), account.phoneNumberInput());
    }
}
