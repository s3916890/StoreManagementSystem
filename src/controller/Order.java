package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class Order {
    private String id;
    private Users user;
    private Product product;
    private Date date;
    private String orderDetails;
    // private Map<Integer, String> orderInfo;
    private ArrayList<String> orderInfo;
    final String outputFilePath = "StoreManagementSystem/src/orders";

    public Order() {
    }

    public Order(Users user, Product goods) {
        this.user = user;
        this.product = goods;
        orderInfo = new ArrayList<>();
        date = new Date();
    }

    public String detail(Users user, Product product) {
        return " Name: " + user.getName() + " Product: " + product.getName();
    }

    public String generateID() {
        return UUID.randomUUID().toString();
    }
    public void printOrder(){
        for (String name : orderInfo) {
            System.out.println(name);
        }
    }

    public String createNewOrder(Users user, Product product) {
        this.id = generateID();
        String details = detail(user, product);
        String orderDetail = "Id" + this.id + details;
        orderInfo.add(orderDetail);
        System.out.println("Your order id: " + this.id);
        System.out.println("Your order detail: " + details);
        return this.orderDetails = orderDetail;
    }

    public void searchOrder() throws FileNotFoundException {
        File file = new File("orders.txt");
        Scanner s = new Scanner(System.in);
        Scanner s2 = new Scanner(file);
        int value = 0;
        while (value != 1) {
            System.out.print("Your order id: ");
            String id = s.nextLine();
            while (s2.hasNextLine()){
                String lineFromFile = s2.nextLine();
                if (lineFromFile.contains(id)){
                    System.out.println(this.orderDetails);
                }value = 1;
            }
        }
    }

    public void writeFile() throws IOException {
        FileWriter file = new FileWriter("orders.txt");
        for (String list : orderInfo) {
            file.write(list + System.lineSeparator());
        }
        file.close();

        // always close the writer
    }



    public String getId() {
        return id;
    }

    public Users getUser() {
        return user;
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
                ", user=" + user +
                ", product=" + product +
                ", date=" + date +
                ", orderInfo=" + orderInfo +
                '}';
    }
}