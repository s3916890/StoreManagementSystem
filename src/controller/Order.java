package controller;

import lib.DateAndTime;
import lib.crud.CreateTable;
import lib.crud.Read;
import lib.crud.Write;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Order {
    private int id = 1;
    private long totalSpendingResult = 0;

    public enum MembershipCategories{
        MEMBER,
        SILVER,
        GOLD,
        PLATINUM
    }

    public Order() {
    }

    public StringBuilder detail(int id, Customer customer, Product product) throws IOException {
        // Access the userID by index and convert to integer
        customer.setId(Integer.parseInt(Read.getSpecificUserInfo(customer.getUserName(), 1, "users.txt", ",")[0]));
        return new StringBuilder()
                .append(this.id)
                .append(",")
                .append(customer.getId())
                .append(",")
                .append(product.getId())
                .append(",")
                .append(this.getTypeOfMemberShip(this.totalSpendingResult))
                .append(",")
                .append(this.totalSpendingResult)
                .append(",")
                .append(DateAndTime.getDateAndTime())
                .append(",")
                .append("SUCCESSFUL")
                .append(",")
                .append("DELIVERING");
    }

    public void createNewOrder(Customer user, Product product) throws IOException {
        File orderSession = new File("orderSession.txt");
        File orderHistory = new File("ordersHistory.txt");

        if(!orderSession.exists()){
            orderSession.createNewFile();
        }

        if(!orderHistory.exists()){
            orderHistory.createNewFile();
        }

        String attributes = "ORDER_ID,CUSTOMER_ID, PRODUCT_ID,MEMBERSHIP,TOTAL_PAYMENT,TIMESTAMP,ORDER_STATUS,DELIVERY_STATUS";
        int lines = 0;

        ArrayList<String[]> orderFetch = Read.readAllLine(orderSession.getName());
        long paymentPrice = product.getPrice();

        if (orderFetch.equals(new ArrayList<String[]>())){
            this.totalSpendingResult += paymentPrice;
            this.setTotalSpendingResult(this.totalSpendingResult);
        }
        else {
            System.out.println("Status order: " + orderFetch.equals(new ArrayList<String[]>()));
            this.setTotalSpendingResult(Long.parseLong(orderFetch.get(orderFetch.size() - 1)[4]));
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

        BufferedReader br = new BufferedReader(new FileReader(orderSession.getName()));

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

        Write.write(orderSession.getName(), attributes, obj);

        String[] castObj = obj.split(",");

        orderFetch.add(castObj);

        System.out.println("\n\s\s\s\s\s\s\s\s\s\s\sOrder Database for username " + user.getUserName());

        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("ORDER_ID", "USER_ID", "PRODUCT_ID", "TOTAL_PAYMENT", "MEMBERSHIP", "TIMESTAMP", "ORDER_STATUS", "DELIVERY_STATUS");

        for(String[] order: orderFetch){
            CreateTable.addRow(
                    order[0],
                    order[1],
                    order[2],
                    order[4],
                    order[3],
                    order[5],
                    order[6],
                    order[7]);
        }
        CreateTable.render();

        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());

        this.id = 1;
        lines = 0;

        BufferedReader readOrdHistory = new BufferedReader(new FileReader(orderHistory.getName()));

        while (readOrdHistory.readLine() != null){
            if(lines == 1){
                lines++;
                this.id = lines;
            }
            else{
                this.id++;
            }
            lines++;
        }
        String objOrderHistory = detail(this.id, user, product).toString();

        Write.write(orderHistory.getName(), attributes, objOrderHistory);

        br.close();

    }

    public void searchOrder() throws IOException, InterruptedException {
        String orderID = this.orderIDInput();
        File file = new File("ordersHistory.txt");

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

        System.out.println("\n\s\s\s\s\s\s\s\s\s\s\sOrder Searching");

        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("ORDER_IO", "CUSTOMER_ID", "PRODUCT_ID", "TOTAL_PAYMENT", "MEMBERSHIP", "TIMESTAMP", "ORDER_STATUS", "DELIVERY_STATUS");
        CreateTable.addRow(matchingResult[0],matchingResult[1], matchingResult[2], matchingResult[4], matchingResult[3], matchingResult[5], matchingResult[6], matchingResult[7]);
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

    public long getTotalSpendingResult() {
        return this.totalSpendingResult;
    }

    public void setTotalSpendingResult(long totalSpendingResult) {
        this.totalSpendingResult = totalSpendingResult;
    }

    public ArrayList<String[]> getAllOrders() throws IOException {
        return Read.readAllLine("ordersHistory.txt");
    }
}
