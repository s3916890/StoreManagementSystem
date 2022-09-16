import lib.OptionInput;
import view.AdminMenu;
import view.AdministratorSystem;
import view.Menu;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        AdministratorSystem administratorSystem = new AdministratorSystem();
        administratorSystem.view();
    }
}

