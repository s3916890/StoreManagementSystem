package controller;

import controller.Account;
import lib.crud.Read;
import lib.crud.Write;
import view.AdminMenu;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Admin extends Account {
    public Admin() {
        super();
    }

    public String adNameLoginInput() throws IOException, InterruptedException {
        AdminMenu menu = new AdminMenu();
        Scanner sc = new Scanner(System.in);
        System.out.print("Developer Name: ");
        String adName = sc.nextLine();
        if (!adName.equals("admin")) {
            System.out.println("Developer Name is not available, please try again !!!");
            menu.adView();
        }
        return adName;
    }

    public boolean verifyAdmin(String admin, String password) {
        String hPassword = this.hashing(password);
        return admin.equals("admin") && hPassword.equals("21232f297a57a5a743894a0e4a801fc3");
    }

    public void updateOrderStatus(int position, String filePath, String newStatus) throws IOException {
        ArrayList<String[]> database = Read.readAllLine("ordersHistory.txt");
        database.get(position - 1)[7] = String.valueOf(newStatus);

        File file = new File(filePath);
        PrintWriter pw = new PrintWriter(file);

        pw.write("");
        pw.close();

        ArrayList<String[]> newDatabase = database;

        for(String[] obj : newDatabase){
            Write.write(filePath, "ORDER_ID,CUSTOMER_ID, PRODUCT_ID,MEMBERSHIP,TOTAL_PAYMENT,TIMESTAMP,ORDER_STATUS,DELIVERY_STATUS", String.join(",", obj));
        }
    }

    private static int id  = 1;


    public String createObj(String category, String name, String color, Long price) {
        return new StringBuilder()
                .append("\n")
                .append(this.id)
                .append(",")
                .append(category)
                .append(",")
                .append(name)
                .append(",")
                .append(color)
                .append(",")
                .append(price).toString();
    }

    public void addProduct(String category, String name, String color, Long price)throws IOException {
        String filePath = "products.txt";
        int lines = 0;
        BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
        while (reader.readLine() != null){
            if(lines == 0){
                lines++;
                id = lines;
            }
            else{
                ++id;
            }
            lines++;
        }

        String obj = this.createObj(category, name, color, price);

        File file = new File(filePath);
        FileWriter csvFile = new FileWriter(file, true);
        csvFile.write(obj);
        csvFile.flush();
        csvFile.close();
    }

    public void updateProductPrice(int position, String filePath, long newPrice) throws IOException {
        ArrayList<String[]> database = Read.readAllLine("products.txt");

        database.get(position - 1)[4] = String.valueOf(newPrice);

        File file = new File(filePath);
        PrintWriter pw = new PrintWriter(file);

        pw.write("");
        pw.close();

        ArrayList<String[]> newDatabase = database;

        for(String[] obj : newDatabase){
            Write.write(filePath, "ID,CATEGORY,NAME,COLOR,PRICE", String.join(",", obj));
        }
    }


    public ArrayList<String> getAllProductName() throws IOException {
        File file = new File("products.txt");
        if(!file.exists()){
            file.createNewFile();
            this.appendAttributesToFile();
        }
        ArrayList<String> checkProductName = new ArrayList<>();
        String[] readUserName = Read.readSpecificColumn(2, file.getName(), ",");

        if(file.length() == 0) {
            return new ArrayList<String>();
        }

        Collections.addAll(checkProductName, readUserName);

        return checkProductName;
    }

    public String productNameInput() throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Product name: ");
        String productName = sc.nextLine();

        ArrayList<String> productNameDB = this.getAllProductName();
        while(this.validateName(productName) || productNameDB.contains(productName)){
            System.out.println("This product is already exist or invalid, try again !!!!");
            System.out.print("Product Name: ");
            productName = sc.nextLine();
        }

        return productName;
    }

    public String categoryInput() throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Category: ");
        String category = sc.nextLine();


        while(this.validateName(category)){
            System.out.println("This product is invalid, try again !!!!");
            System.out.print("Category: ");
            category = sc.nextLine();
        }

        return category;
    }

    public String colorInput() throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Color: ");
        String color = sc.nextLine();


        while(!this.validateColor(color)){
            System.out.println("This color is invalid, try again !!!!");
            System.out.print("Color: ");
            color = sc.nextLine();
        }

        return color;
    }

    public Long priceInput() throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Price: ");
        Long price = sc.nextLong();


        while(!this.validatePrice(price)){
            System.out.println("The price is invalid, try again !!!!");
            System.out.print("Price: ");
            price = sc.nextLong();
        }

        return price;

    }

    public void appendAttributesToFile() throws IOException {
        File file = new File("products.txt");
        String attributes = "ID,CATEGORY,NAME,COLOR,PRICE";
        FileWriter csvFile = new FileWriter(file.getName(), true);
        BufferedReader reader = new BufferedReader(new FileReader(file.getName()));

        if(reader.readLine() == null){
            csvFile.append(attributes);
            csvFile.append("\n");
        }
        reader.close();
        csvFile.close();
    }

    public boolean validateName(final String name) {
        String CONFIG_RULE =
                "[a-zA-Z0-9]+( +[a-zA-Z0-9]+)*";
        Pattern pattern = Pattern.compile(CONFIG_RULE);
        Matcher matcher = pattern.matcher(name);
        return !matcher.matches();
    }

    public boolean validateColor(final String color) {
        String CONFIG_RULE =
                "[a-zA-Z]*";
        Pattern pattern = Pattern.compile(CONFIG_RULE);
        Matcher matcher = pattern.matcher(color);
        return matcher.matches();
    }

    public boolean validatePrice(final Long price) {
        String CONFIG_RULE =
                "[0-9]+";
        Pattern pattern = Pattern.compile(CONFIG_RULE);
        Matcher matcher = pattern.matcher(price.toString());
        return matcher.matches();
    }
}
