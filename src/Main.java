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
        menu.view();
    }

}
