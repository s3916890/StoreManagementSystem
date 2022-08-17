import controller.AdminProduct;
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
        menu.view();

//        AdminMenu menu = new AdminMenu();
//        AdminProduct adProduct = new AdminProduct();

//        menu.adSystem();
//        menu.adView();

//        ArrayList<Integer> test = new ArrayList<>();

//        ArrayList<String[]> database = Read.readAllLine("products.txt");
//        Scanner sc = new Scanner(System.in);
//        String position = sc.nextLine();
//        String[] checkObj = Read.getSpecificLine(position, 0, "products.txt", ",");
//
//        for(String[] obj : database){
//            if(obj.equals(checkObj)){
//                adProduct.updatePrice("products.txt", checkObj, "400000");
//            }
//        }

//        adProduct.updatePrice(1, "products.txt", "800000");

//        System.out.println(test.toString());
    }
}
