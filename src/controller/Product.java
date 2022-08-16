package controller;

import lib.OptionInput;
import lib.algorithm.search.BoyerMoore;
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

    public ArrayList<Long> getAllPrice(){
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
        System.out.printf("\n%30s %65s", "Item", "Price(VND)");
        System.out.printf("\n%s%n", "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for(int i = 0; i < products.size() - 1; i++) {
            System.out.printf("\n%40s %51s VND",products.get(i)[2], products.get(i)[4]);
        }

        this.viewDetailConfirmation();
    }

    public void viewDetailed() throws IOException {
        ArrayList<String[]> products = Read.readAllLine("products.txt");
        System.out.println("\n===================================================================== Show Detail Available Products !!! =====================================================================");
        System.out.printf("\n%16s %34s %46s %44s", "ProductID", "Item", "Color", "Price(VND)");
        System.out.printf("\n%s%n", "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < products.size(); i++) {
            System.out.printf("\n%12s %50s %34s %40s VND", products.get(i)[0], products.get(i)[2], products.get(i)[3], products.get(i)[4]);
        }
    }

    public void viewDetailConfirmation() throws IOException, InterruptedException {
        System.out.println("\n===================================================================== Wish to show detailed ? !!! =====================================================================");
        System.out.println("""
                    1. Yes
                    2. No""");
        String option = OptionInput.input();
        switch (option){
            case "1" -> {
                this.viewDetailed();
            }
            case "2" -> {
                Menu menu = new Menu();
                menu.view();
            }
        }
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
                System.exit(1);
            }
        }
        if(matchResult.size() == 0){
            System.out.println("Sorry, there is no found result");
        }

        if(matchResult.size() > 0){
            System.out.println("\n=============================================================== Available Searching Products !!! =====================================================================");
            System.out.printf("\n%27s %40s %40s", "Item", "Color", "Price($)");
            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            for(int i = 0; i < matchResult.size(); i++){
                System.out.printf("\n%30s %40s %36s VND\n",matchResult.get(i)[2], matchResult.get(i)[3], matchResult.get(i)[4]);
            }
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
            System.out.printf("%30s %30s %40s %25s %40s%n", "Item", "|", "Color", "|", "Price($)");
            System.out.printf("%s%n", "----------------------------------------------------------------------------------------------------------------------------------------------------------");

            for(int i = 0; i < matchResult.size(); i++){
                System.out.printf("%30s %25s %10s %25s %10s%n",matchResult.get(i)[2], "|", matchResult.get(i)[3], "|",matchResult.get(i)[4]);
            }
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
