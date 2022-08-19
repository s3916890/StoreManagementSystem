package view;

import controller.Account;
import controller.AdminProduct;
import controller.Product;
import lib.OptionInput;
import lib.crud.CreateTable;
import lib.crud.Read;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AdminMenu {
    public void view() throws IOException, InterruptedException {
        System.out.println("\n===================================================================== WELCOME TO APPLE STORE MANAGEMENT SYSTEM !!! =====================================================================");
        System.out.println("""
                1. Register
                2. Login
                3. Developer only
                4. Exit""");

        Account user = new Account();
        Menu menu = new Menu();
        String option = OptionInput.input();

        switch (option) {
            case "1" -> {
                user.register(user.userNameRegisterInput(), user.passwordRegisterInput(), user.fullNameInput(), user.phoneNumberInput());
            }
            case "2" -> {
                boolean isLogged = false;
                System.out.println("Login Status: " + isLogged);
                if (user.verifyLogin(user.userNameLoginInput(), user.passwordLoginInput(), "users.txt", ",")) {
                    String[] data = Read.getSpecificLine(user.getUserName(), 1, "users.txt", ",");
                    System.out.println("\nUsername: " + data[1] + "\nFull name: " + data[3] + "\nPhone-number: " + data[4]);
                    isLogged = true;
                } else {
                    System.out.println("Wrong password, try again bro !!!!");
                    user.login(user.userNameLoginInput(), user.passwordLoginInput());
                }
                System.out.println("Login Status: " + isLogged);
            }
            case "3" -> {
                this.adView();
            }
            case "4" -> {
                System.out.println("Thank you so much for using our system. See you soon !!!!");
                System.exit(1);
            }
            default -> {
                System.out.println("There is no match option, please enter again");
                menu.view();
            }
        }
    }

    //  Using for admin to log in for controlling the system
    public void adView() throws IOException, InterruptedException {
        System.out.println("\n===================================================================== WELCOME TO APPLE STORE DEVELOPING SYSTEM !!! =====================================================================");
        System.out.println("""
                1. Login (Developing account only)
                2. Back to main menu""");
        Account admin = new Account();
        Menu menu = new Menu();

        String option = OptionInput.input();

        switch (option) {
            case "1" -> {
                boolean isLogged = false;
                System.out.println("Login Status: " + false);
                if (admin.verifyAdmin(admin.adNameLoginInput(), admin.passwordLoginInput())) {
                    System.out.println("Welcome back sir !!! Accessing the system...");
                    isLogged = true;
                    this.adSystem();
                } else {
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
                this.adView();
            }
        }
    }

    public void adSystem() throws IOException, InterruptedException {
        System.out.println("\n===================================================================== WELCOME TO APPLE STORE DEVELOPING SYSTEM !!! =====================================================================");
        System.out.println("""
                1. Information of all users
                2. Information of product
                3. Update price
                4. Add new product
                5. Sign out""");
        Menu menu = new Menu();
        AdminProduct product = new AdminProduct();
        String option = OptionInput.input();
        ArrayList<String[]> user = Read.readAllLine("users.txt");
        ArrayList<String[]> allProducts = Read.readAllLine("products.txt");

        switch (option) {
            case "1" -> {
                if(user.toString().equals("[]")){
                    System.out.println("Sorry, the user database does not exist");
                }

                else{
                    CreateTable.setShowVerticalLines(true);
                    CreateTable.setHeaders("ID","Username","FullName","TotalSpending","PhoneNumber","registerTime");

                    for (int i = 0; i < user.size(); i++) {
                        CreateTable.addRow(user.get(i)[0], user.get(i)[1],user.get(i)[3],user.get(i)[4],user.get(i)[5],user.get(i)[6]);
                    }

                }
                CreateTable.render();

                CreateTable.setHeaders(new String[0]);
                CreateTable.setRows(new ArrayList<String[]>());


                this.adSystem();
            }
            case "2" -> {
                CreateTable.setShowVerticalLines(true);
                CreateTable.setHeaders("productID","Category","Item","Color","Price");
                for (String[] strings : allProducts) {
                    CreateTable.addRow(strings[0],strings[1],strings[2],strings[3],strings[4]);
                }
                CreateTable.render();

                CreateTable.setHeaders(new String[0]);
                CreateTable.setRows(new ArrayList<String[]>());
                this.adSystem();
            }
            case "3" -> {
                Scanner sc = new Scanner(System.in);
                System.out.print("\nEnter the position that you want to update/modify: ");
                int position = sc.nextInt();
                System.out.print("\nEnter the new price that you want to update/modify: ");
                long newPrice = sc.nextLong();

                AdminProduct adminProduct = new AdminProduct();

                adminProduct.updatePrice(position, "products.txt", newPrice);
                this.adSystem();
            }
            case "4" -> {
                product.addProduct(product.categoryInput(), product.productNameInput(), product.colorInput(), product.priceInput());
                this.adSystem();
            }
            case "5" -> {
                this.adView();
            }
        }
    }
}
