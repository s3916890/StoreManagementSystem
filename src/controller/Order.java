package controller;

import lib.DateAndTime;
import lib.crud.Read;
import lib.crud.Write;
import view.Menu;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Order {
    private int id = 1;
    private Customer customer;
    private Product product;
    private Date date;
    private long totalSpendingResult;
    private ArrayList<String> orderInfo = new ArrayList<>();

    public enum MembershipCategories{
        MEMBER,
        SILVER,
        GOLD,
        PLATINUM
    }

    public Order() {
    }

    public Order(int id, Customer customer, Product goods, long totalSpendingResult) {
        this.id = id;
        this.customer = customer;
        this.product = goods;
        this.totalSpendingResult = 0;

        orderInfo = new ArrayList<>();
        date = new Date();
    }

    public StringBuilder detail(int id, Customer customer, Product product) {
        return new StringBuilder()
                .append(this.id)
                .append(",")
                .append(customer.getName())
                .append(",")
                .append(product.getName())
                .append(",")
                .append(product.getColor())
                .append(",")
                .append(product.getPrice())
                .append(",")
                .append(this.getTypeOfMemberShip(this.totalSpendingResult))
                .append(",")
                .append(this.totalSpendingResult)
                .append(",")
                .append(new DateAndTime().getDateAndTime());
    }

    public void createNewOrder(Customer user, Product product) throws IOException {
        File file = new File("orders.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
        String attributes = "orderID,userName,order,color,price,orderTime";
        int lines = 0;

        ArrayList<String[]> orderHistory = Read.readAllLine("orders.txt");
        long paymentPrice = product.getPrice();

        if (orderHistory.equals(new ArrayList<String[]>())){
            String[] userInfo = Read.getSpecificLine(user.getName(), 1, "users.txt", ",");
            long currentTotalSpending = Long.parseLong(userInfo[4]);
            currentTotalSpending += paymentPrice;
            this.totalSpendingResult = currentTotalSpending;
        }
        else{
            String currentLine = "";
            String currentData = "";

            while((currentLine = reader.readLine()) != null){
                currentData = currentLine;
            }
            String[] data = currentData.split(",");
            long totalSpendingHistory = Long.parseLong(data[6]);
            totalSpendingHistory += paymentPrice;
            this.totalSpendingResult = totalSpendingHistory;
        }

        if(this.getTypeOfMemberShip(this.totalSpendingResult).equals(MembershipCategories.SILVER.name())){
            this.totalSpendingResult = (long)(this.totalSpendingResult * (1 - 0.05));
        }
        if(this.getTypeOfMemberShip(this.totalSpendingResult).equals(MembershipCategories.GOLD.name())){
            this.totalSpendingResult = (long)(this.totalSpendingResult * (1 - 0.1));
        }
        if(this.getTypeOfMemberShip(this.totalSpendingResult).equals(MembershipCategories.PLATINUM.name())){
            this.totalSpendingResult = (long)(this.totalSpendingResult * (1 - 0.15));
        }

        BufferedReader br = new BufferedReader(new FileReader(file.getName()));

        while (br.readLine() != null){
            if(lines == 1){
                lines++;
                this.id = lines;
            }
            else{
                this.id++;
            }
            lines++;
        }

        String obj = detail(this.id, user, product).toString();
        orderInfo.add(obj);
        Write.write("orders.txt", attributes, obj);
    }

    public void searchOrder() throws IOException, InterruptedException {
        String orderID = this.orderIDInput();

        String[] matchingResult = Read.getSpecificLine(orderID, 0, "orders.txt", ",");

        if(Arrays.toString(matchingResult).equals("[]")){
            System.out.println("Sorry, order was not found");
            Menu menu  = new Menu();
            String userName = Read.readAllLine("orders.txt").get(0)[1];
            menu.viewHomepage(userName);
        }

        System.out.println("\nYour order history: ");
        System.out.println("\nOrder ID: " + matchingResult[0]);
        System.out.println("\nUsername: " + matchingResult[1]);
        System.out.println("\nOrder name: " + matchingResult[2]);
        System.out.println("\nColor: " + matchingResult[3]);
        System.out.println("\nPrice: " + matchingResult[4]);
        System.out.println("\nOrder time: " + matchingResult[5]);
    }

    public String orderIDInput(){
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter the order ID: ");
        String orderID = sc.nextLine();

        while(!this.validateNumber(orderID)){
            System.out.println("Invalid number, try again !!!!");
            System.out.print("Enter the orderID again: ");
            orderID = sc.nextLine();
        }
        return orderID;
    }

    public String getTypeOfMemberShip(long totalSpending){
        String typeOfMemberShip = "";

        if (totalSpending > 5000000) {
            typeOfMemberShip = MembershipCategories.SILVER.name();
        }
        if(totalSpending > 10000000){
            typeOfMemberShip = MembershipCategories.GOLD.name();
        }
        if(totalSpending > 25000000){
            typeOfMemberShip = MembershipCategories.PLATINUM.name();
        }
        else{
            typeOfMemberShip = "Member";
        }
        return typeOfMemberShip;
    }

    public boolean validateNumber(final String number) {
        String CONFIG_RULE = "[0-9]+";
        Pattern pattern = Pattern.compile(CONFIG_RULE);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }


    public int getId() {
        return id;
    }

    public Customer getUser() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<String> getOrderInfo() {
        return orderInfo;
    }

    @Override
    public String toString() {
        return "Order_2{" +
                "id=" + id +
                ", user=" + customer +
                ", product=" + product +
                ", date=" + date +
                ", orderInfo=" + orderInfo +
                '}';
    }
}