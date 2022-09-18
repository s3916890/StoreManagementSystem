package controller;

import lib.OptionInput;
import lib.algorithm.search.BoyerMoore;
import lib.crud.CreateTable;
import lib.crud.Read;
import view.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Product {
    private int id;
    private String name;
    private Long price;
    private String category;

    private String color;

    public Product(){

    }
    public Product(int id, String category, String name, String color, Long price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.color = color;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public ArrayList<Long> getAllPrice() throws IOException {
        String[] readPrice = Read.readSpecificColumn(4, "products.txt", ",");


        ArrayList<Long> priceList = new ArrayList<>(readPrice.length);

        for(int i = 1; i < readPrice.length; i++){
            priceList.add(Long.parseLong(readPrice[i]));
        }

        return priceList;
    }

    public void view() throws IOException, InterruptedException {
        ArrayList<String[]> products = Read.readAllLine("products.txt");

        System.out.println("\n===================================================================== Available Products !!! =====================================================================");
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("ITEM", "COLOR", "PRICE(VND)");

        for(int i = 0; i < products.size() - 1; i++) {
            CreateTable.addRow(products.get(i)[2], products.get(i)[3], products.get(i)[4]);
        }

        System.out.println();
        CreateTable.render();

        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    public void viewDetailed() throws IOException {
        ArrayList<String[]> products = Read.readAllLine("products.txt");
        System.out.println("\n===================================================================== Show Detail Available Products !!! =====================================================================");
        CreateTable.setHeaders("PRODUCT_ID", "ITEM", "COLOR", "PRICE(VND)");

        for (int i = 0; i < products.size(); i++) {
            CreateTable.addRow(products.get(i)[0], products.get(i)[2], products.get(i)[3], products.get(i)[4]);
        }

        CreateTable.render();

        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    public void viewMemberSearchingResult(String userCookies) throws IOException, InterruptedException {
        String[] category = Read.readSpecificColumn(1, "products.txt", ",");
        Menu homepage = new Menu();
        category =  Arrays.stream(category).distinct().toArray(String[]::new);

        String option= OptionInput.input();

        ArrayList<String[]> matchResult = new ArrayList<>(this.getMatchResult(category[0]).size());


        switch (option){
            case "1" -> {
                matchResult = this.getMatchResult(category[0]);
            }
            case "2" -> {
                matchResult = this.getMatchResult(category[1]);
            }
            case "3" -> {
                matchResult = this.getMatchResult(category[2]);
            }
            case "4" -> {
                matchResult = this.getMatchResult(category[3]);
            }
            case "5" -> {
                matchResult = this.getMatchResult(category[4]);
            }
            case "6" -> {
                matchResult = this.getMatchResult(category[5]);
            }
            case "7" -> {
                homepage.viewHomepage(userCookies);
            }
            case "8" -> {
                System.out.println("Thank you so much for using our system. See you soon !!!!");
                System.out.println("""
                        \nCOSC2081 GROUP ASSIGNMENT\s
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
        }
        if(matchResult.size() == 0){
            System.out.println("Sorry, there is no found result");
        }

        if(matchResult.size() > 0){
            System.out.println("\n=============================================================== Available Searching Products !!! =====================================================================");
            CreateTable.setShowVerticalLines(true);
            CreateTable.setHeaders("PRODUCT_ID","ITEM","COLOR","PRICE(VND)");

            for(int i = 0; i < matchResult.size(); i++){
                CreateTable.addRow(matchResult.get(i)[0],matchResult.get(i)[2],matchResult.get(i)[3],matchResult.get(i)[4]);
            }
            System.out.println();
            CreateTable.render();

            CreateTable.setHeaders(new String[0]);
            CreateTable.setRows(new ArrayList<String[]>());
        }
    }
    public void viewUserSearchingResult() throws IOException, InterruptedException {
        String[] category = Read.readSpecificColumn(1, "products.txt", ",");
        Menu menu = new Menu();
        category =  Arrays.stream(category).distinct().toArray(String[]::new);

        String option= OptionInput.input();

        ArrayList<String[]> matchResult = new ArrayList<>(this.getMatchResult(category[0]).size());


        switch (option){
            case "1" -> {
                matchResult = this.getMatchResult(category[0]);
            }
            case "2" -> {
                matchResult = this.getMatchResult(category[1]);
            }
            case "3" -> {
                matchResult = this.getMatchResult(category[2]);
            }
            case "4" -> {
                matchResult = this.getMatchResult(category[3]);
            }
            case "5" -> {
                matchResult = this.getMatchResult(category[4]);
            }
            case "6" -> {
                matchResult = this.getMatchResult(category[5]);
            }
            case "7" -> {
                menu.view();
            }
        }
        if(matchResult.size() == 0){
            System.out.println("Sorry, there is no found result");
        }

        if(matchResult.size() > 0){
            System.out.println("\n===================================================================== Available Searching Products !!! =====================================================================");
            CreateTable.setShowVerticalLines(true);
            CreateTable.setHeaders("PRODUCT_ID","ITEM","COLOR","PRICE(VND)");

            for(int i = 0; i < matchResult.size(); i++){
                CreateTable.addRow(matchResult.get(i)[0],matchResult.get(i)[2],matchResult.get(i)[3],matchResult.get(i)[4]);
            }
            CreateTable.render();

            CreateTable.setHeaders(new String[0]);
            CreateTable.setRows(new ArrayList<String[]>());
        }
    }
    public ArrayList<String[]> getMatchResult(String data) throws IOException {
        String[] category = Read.readSpecificColumn(1, "products.txt", ",");
        String[] productsName = Read.readSpecificColumn(2, "products.txt", ",");

        ArrayList<String[]> matchResult = new ArrayList<>();

        for (int i = 0; i < productsName.length; i++){
            // Implement Boyer Moore Searching Algorithm
            BoyerMoore text = new BoyerMoore(data);
            boolean isFound = text.boyerMooreSearch(category[i], data);

            if(isFound){
                String[] specificLine = Read.getSpecificLine(productsName[i], 2, "products.txt", ",");
                matchResult.add(specificLine);
            }
        }

        return matchResult;
    }
}
