import controller.AdminProduct;
import controller.Customer;
import controller.Order;
import controller.Product;
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


//        menu.view();
//        adminMenu.view();

//        ArrayList<String[]> productOptions = Read.readAllLine( "products.txt");
//
//        for(String[] obj: productOptions){
//            System.out.println(Arrays.toString(obj));
//        }
//        System.out.println(productOptions.get(12)[0]);
//        System.out.println(productOptions.get(12)[1]);
//        System.out.println(productOptions.get(12)[2]);
//        System.out.println(productOptions.get(12)[3]);
//        System.out.println(productOptions.get(12)[4]);

//        Customer customer = new Customer(3, "40.karma", "0909150472");
//        Product product = new Product(1, "laptop", "Macbook Pro 13 inch Retina 2015", "silver", Long.parseLong("25000000"));
//
//        Order order = new Order();
//
//        order.createNewOrder(customer, product);
    }
}
