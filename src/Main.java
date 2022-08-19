import controller.AdminProduct;
import lib.crud.CreateTable;
import lib.crud.Read;
import view.AdminMenu;
import view.Menu;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Menu menu = new Menu();
        AdminMenu adminMenu = new AdminMenu();


        menu.view();
//        adminMenu.view();
    }
}
