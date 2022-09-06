package controller;

import lib.DateAndTime;
import lib.crud.CreateTable;
import lib.crud.Read;
import lib.crud.Write;
import view.Menu;

import java.io.*;
import java.lang.reflect.Member;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Order {
    private int id = 1;
    private Customer customer;
    private Product product;
    private Date date;
    private long totalSpendingResult = 0;
    private ArrayList<String[]> orderInfo = new ArrayList<>();

    public enum MembershipCategories{
        MEMBER,
        SILVER,
        GOLD,
        PLATINUM
    }

    public Order() {
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

        if(!file.exists()){
            file.createNewFile();
        }
        BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));
        String attributes = "orderID,userName,order,color,price,membership,totalPayment,orderTime";
        int lines = 0;

        ArrayList<String[]> orderHistory = Read.readAllLine("orders.txt");
        long paymentPrice = product.getPrice();

        if (orderHistory.equals(new ArrayList<String[]>())){
            System.out.println("Status order: " + orderHistory.equals(new ArrayList<String[]>()));
            System.out.println("Current Total Spending before the first order: " + this.totalSpendingResult);
            this.totalSpendingResult += paymentPrice;
            System.out.println("Current Total Spending in the first order: " + this.totalSpendingResult);
            System.out.println("Total spending result in the first order: " + this.totalSpendingResult);
            this.setTotalSpendingResult(this.totalSpendingResult);
        }
        else {
            System.out.println("Status order: " + orderHistory.equals(new ArrayList<String[]>()));
            this.setTotalSpendingResult(Long.parseLong(orderHistory.get(orderHistory.size() - 1)[6]));
            this.totalSpendingResult = this.getTotalSpendingResult() + paymentPrice;
            this.setTotalSpendingResult(this.totalSpendingResult);
        }

        if(this.getTypeOfMemberShip(this.getTotalSpendingResult()).equals(MembershipCategories.SILVER.name())){
            this.totalSpendingResult = (long)(this.totalSpendingResult * (1 - 0.05));
        }
        if(this.getTypeOfMemberShip(this.getTotalSpendingResult()).equals(MembershipCategories.GOLD.name())){
            this.totalSpendingResult = (long)(this.totalSpendingResult * (1 - 0.1));
        }
        if(this.getTypeOfMemberShip(this.getTotalSpendingResult()).equals(MembershipCategories.PLATINUM.name())){
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

        Write.write("orders.txt", attributes, obj);

        br.close();
        String[] castObj = obj.split(",");

        orderHistory.add(castObj);

        System.out.println("\n\s\s\s\s\s\s\s\s\s\s\sOrder Database for username " + user.getName());

        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("OrderID", "Username", "Item", "Color", "Price(VND)", "TotalPayment", "Membership", "OrderTime");

        for(String[] order: orderHistory){
            CreateTable.addRow(
                    order[0],
                    order[1],
                    order[2],
                    order[3],
                    order[4],
                    order[6],
                    order[5],
                    order[7]);
        }
        CreateTable.render();

        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    public void searchOrder() throws IOException, InterruptedException {
        String orderID = this.orderIDInput();
        File file = new File("orders.txt");

        if(!file.exists()){
            file.createNewFile();
            System.out.println("Sorry, the database was not found");
        }

        String[] orderIDs = Read.readSpecificColumn(0, file.getName(), ",");

        ArrayList<String> listOrderID = new ArrayList<>(Arrays.asList(orderIDs));

        BufferedReader br = new BufferedReader(new FileReader(file));

        while(!listOrderID.contains(orderID) || br.readLine() == null){
            System.out.println("Sorry, order was not found");
            orderID = this.orderIDInput();
        }

        br.close();

        String[] matchingResult = Read.getSpecificLine(orderID, 0, file.getName(), ",");

        System.out.println("\n");

        System.out.println("\n\s\s\s\s\s\s\s\s\s\s\sSearching Order History");

        CreateTable.setShowVerticalLines(true);
        // orderID,userName,order,color,price,membership,totalPayment,orderTime
        CreateTable.setHeaders("OrderID", "Username", "Item", "Color", "Price(VND)", "Membership", "TotalPayment", "OrderTime");
        CreateTable.addRow(matchingResult[0],matchingResult[1], matchingResult[2], matchingResult[3], matchingResult[4], matchingResult[6], matchingResult[5], matchingResult[7]);
        CreateTable.render();

        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
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

        while(true){
            if (totalSpending > Long.parseLong("5000000") && totalSpending <= Long.parseLong("10000000")) {
                typeOfMemberShip = MembershipCategories.SILVER.name();
                break;
            }
            if(totalSpending > Long.parseLong("10000000") && totalSpending <= Long.parseLong("25000000")){
                typeOfMemberShip = MembershipCategories.GOLD.name();
                break;
            }
            if(totalSpending > Long.parseLong("25000000")){
                typeOfMemberShip = MembershipCategories.PLATINUM.name();
            }
            else{
                typeOfMemberShip = MembershipCategories.MEMBER.name();
            }
            break;
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

    public ArrayList<String[]> getOrderInfo() {
        return orderInfo;
    }

    public long getTotalSpendingResult() {
        return this.totalSpendingResult;
    }

    public void setTotalSpendingResult(long totalSpendingResult) {
        this.totalSpendingResult = totalSpendingResult;
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