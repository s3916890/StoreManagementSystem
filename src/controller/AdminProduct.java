package controller;

import lib.DateAndTime;
import lib.crud.Read;
import lib.crud.Write;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminProduct {
    private String category;
    private String name;
    private String color;
    private Long price;

    public AdminProduct(String category, String name, String color, Long price) {
        this.category = category;
        this.name = name;
        this.color = color;
        this.price = price;
    }

    private static int id  = 1;

    public AdminProduct(){

    }

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

        Write.write(filePath, "\n", obj);
    }

    public void updatePrice(int position, String filePath, String newPrice) throws IOException {
        ArrayList<String[]> database = Read.readAllLine("products.txt");

        database.get(position - 1)[4] = newPrice;

        File file = new File(filePath);
        PrintWriter pw = new PrintWriter(file);

        pw.write("");
        pw.close();

        ArrayList<String[]> newDatabase = database;

        for(String[] obj : newDatabase){
            Write.write(filePath, "ID,Category,Name,Color,Price", String.join(",", obj));
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
        File file = new File("products.txt.txt");
        String attributes = "ID,Category,Name,Color,Price";
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