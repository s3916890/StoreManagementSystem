package view;

import controller.*;
import controller.Account;
import controller.Admin;
import lib.OptionInput;
import lib.crud.CreateTable;
import lib.crud.Read;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminMenu {
    public void view() throws IOException, InterruptedException {
        System.out.println("\n===================================================================== WELCOME TO APPLE STORE MANAGEMENT SYSTEM !!! =====================================================================");
        System.out.println("""
                1. Developer only
                2. Move back to the authentication system.
                3. Exit""");

        Account user = new Account();
        String option = OptionInput.input();
        Authentication authenticationComponent = new Authentication();

        switch (option) {
            case "1" -> {
                this.adView();
            }
            case "2" -> authenticationComponent.view();
            case "3" -> {
                System.out.println("Thank you so much for using our system. See you soon !!!!");
                System.out.println("""
                        COSC2081 GROUP ASSIGNMENT\s
                        STORE ORDER MANAGEMENT SYSTEM\s
                        Instructor: Mr. Minh Vu\s
                        Group: Group Name\s                           
                        """);
                CreateTable.setShowVerticalLines(true);
                CreateTable.setHeaders("STUDENT_ID", "NAME");
                CreateTable.addRow("s3916890", "Nguyen Phuc Loi");
                CreateTable.addRow("s3938101", "Duong Tran My Linh");
                CreateTable.addRow("s3836606", "Dang Hoang Anh Khoa");
                CreateTable.addRow("s3927120", "Vu Quoc Gia Quan");

                CreateTable.render();

                CreateTable.setHeaders(new String[0]);
                CreateTable.setRows(new ArrayList<String[]>());

                System.exit(1);
            }
            default -> {
                System.out.println("There is no match option, please enter again");
                this.view();
            }
        }
    }

    //  Using for admin to log in for controlling the system
    public void adView() throws IOException, InterruptedException {
        System.out.println("\n===================================================================== WELCOME TO APPLE STORE DEVELOPING SYSTEM !!! =====================================================================");
        System.out.println("""
                1. Login (Developing account only)
                2. Back to main menu""");
        Admin admin = new Admin();

        String option = OptionInput.input();

        switch (option) {
            case "1" -> {
                boolean isLogged = false;
                System.out.println("Login Status: " + false);
                String userName = admin.adNameLoginInput();
                String password = admin.passwordLoginInput();
                while (!admin.verifyAdmin(userName, password)) {
                    System.out.println("The developer name is not available, please try again !!!");
                    userName = admin.adNameLoginInput();
                    password = admin.passwordLoginInput();
                }
                System.out.println("Welcome back sir !!! Accessing the system...");
                isLogged = true;
                System.out.println("Login Status: " + isLogged);
                this.adSystem();
            }
            case "2" -> {
                this.view();
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
                1. Query information of all users
                2. Query information of all products
                3. Query information of all orders
                4. Update price
                5. Add new product
                6. Change Order Status
                7. Sign out""");
        Order allOrders = new Order();
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
                    CreateTable.setHeaders("ID","USERNAME","FULL_NAME","INITIAL_SPENDING","PHONE_NUMBER","REGISTER_TIME","INITIAL_MEMBERSHIP");

                    for (int i = 0; i < user.size(); i++) {
                        CreateTable.addRow(user.get(i)[0], user.get(i)[1],user.get(i)[3],user.get(i)[4],user.get(i)[5],user.get(i)[6],user.get(i)[7]);
                    }

                }
                CreateTable.render();

                CreateTable.setHeaders(new String[0]);
                CreateTable.setRows(new ArrayList<String[]>());


                this.adSystem();
            }
            case "2" -> {
                CreateTable.setShowVerticalLines(true);
                CreateTable.setHeaders("PRODUCT_ID","CATEGORY","ITEM","COLOR","PRICE(VND)");
                for (String[] strings : allProducts) {
                    CreateTable.addRow(strings[0],strings[1],strings[2],strings[3],strings[4]);
                }
                CreateTable.render();

                CreateTable.setHeaders(new String[0]);
                CreateTable.setRows(new ArrayList<String[]>());
                this.adSystem();
            }
            case "3" -> {
                ArrayList<String[]> ordersHistory = allOrders.getAllOrders();
                CreateTable.setShowVerticalLines(true);

                CreateTable.setHeaders("ORDER_ID","CUSTOMER_ID", "PRODUCT_ID","MEMBERSHIP","TOTAL_PAYMENT","TIMESTAMP","ORDER_STATUS","DELIVERY_STATUS");

                for (String[] strings : ordersHistory) {
                    CreateTable.addRow(strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6],strings[7]);
                }
                CreateTable.render();

                CreateTable.setHeaders(new String[0]);
                CreateTable.setRows(new ArrayList<String[]>());
                this.adSystem();
            }
            case "4" -> {
                Scanner sc = new Scanner(System.in);
                System.out.print("\nEnter the position that you want to update/modify: ");
                int position = sc.nextInt();
                System.out.print("\nEnter the new order status that you want to update/modify: ");
                long newPrice = sc.nextLong();

                new Admin().updateProductPrice(position, "products.txt", newPrice);
                this.adSystem();
            }
            case "5" -> {
                new Admin().addProduct(new Admin().categoryInput(), new Admin().productNameInput(), new Admin().colorInput(), new Admin().priceInput());
                this.adSystem();
            }
            case "6" -> {
                Scanner sc = new Scanner(System.in);
                System.out.print("\nEnter the position that you want to update/modify: ");
                int position = sc.nextInt();
                System.out.print("\nEnter the new price that you want to update/modify: ");
                sc.nextLine();
                String newStatus = sc.nextLine();

                new Admin().updateOrderStatus(position, "ordersHistory.txt", newStatus);
                this.adSystem();
            }
            case "7" -> {
                this.adView();
            }
        }
    }
}
