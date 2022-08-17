package controller;

import lib.DateAndTime;
import lib.crud.Read;
import lib.crud.Write;
import view.Menu;

import java.io.*;
import java.util.*;
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

        if(!file.exists()){
            file.createNewFile();
        }
        BufferedReader reader = new BufferedReader(new FileReader(file.getName()));
        String attributes = "orderID,userName,order,color,price,membership,totalPayment,orderTime";
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

        br.close();

        String obj = detail(this.id, user, product).toString();
        String[] castObj = obj.split(",");
        orderInfo.add(obj);
        Write.write("orders.txt", attributes, obj);

        System.out.println("\n===================================================================== Order Payment Invoice !!! ===========================================================================================");
        System.out. printf("\n%12s %14s %22s %27s %24s %24s %24s %20s", "OrderID", "Username", "Item", "Color", "Price(VND)", "TotalPayment", "Membership", "OrderTime");
        System.out.println();
        System.out.println("\n============================================================================================================================================================================================");

        System.out.printf("\n%8s %18s %30s %20s %20s VND %21s VND %23s %24s", castObj[0], castObj[1], castObj[2], castObj[3], castObj[4], castObj[6], castObj[5], castObj[7]);
        System.out.println();
        System.out.println("\n===========================================================================================================================================================================================");

    }

    public void searchOrder() throws IOException, InterruptedException {
        String orderID = this.orderIDInput();
        File file = new File("orders.txt");

        if(!file.exists()){
            file.createNewFile();
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
        System.out.println("\s\s\s\s\s\s\s\s\s\s\s\sYour order history");
        System.out.println("Order ID: " + matchingResult[0]);
        System.out.println("Username: " + matchingResult[1]);
        System.out.println("Order name: " + matchingResult[2]);
        System.out.println("Color: " + matchingResult[3]);
        System.out.println("Price: " + matchingResult[4] + " VND");
        System.out.println("Total payment: " + matchingResult[5] + " VND");
        System.out.println("Order time: " + matchingResult[7]);
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