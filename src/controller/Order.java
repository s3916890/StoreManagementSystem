package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Order {
    private int id;
    private Users user;
    private Product product;
    private Date date;
    private Map<Integer, String> orderInfo;
    // private ArrayList<> detail;
    final String outputFilePath = "StoreManagementSystem/src/orders";

    public Order() {
    }

    public Order(int id, Users user, Product goods) {
        this.id = id;
        this.user = user;
        this.product = goods;

        orderInfo = new HashMap<>();
        date = new Date();
    }

    public String detail(Users user, Product product) {
        return user.getName() + " " + product.getName();
    }

    public int generateID() {
        return (int) (Math.random() * 100 + 1);
    }
    public void printOrder(){
        for (Integer name : orderInfo.keySet()) {
            String key = name.toString();
            String value = orderInfo.get(name);
            System.out.println(key + " " + value);
        }
    }

    public void createNewOrder(Users user, Product product) {
        int id = generateID();
        String orderDetail = detail(user, product);
        for (Map.Entry<Integer, String> list : orderInfo.entrySet()) {
            int key = list.getKey();
            if (key == id) {
                id = generateID();
            }
        }orderInfo.put(id, orderDetail);
        System.out.println("Your order id: " + id);
    }

    public void searchOrder(){
        Scanner s = new Scanner(System.in);
        int value = 0;
        while (value != 1){
            System.out.print("Id search: ");
            int id = s.nextInt();
            for (Map.Entry<Integer, String> list : orderInfo.entrySet()) {
                if (list.getKey() == id){
                    System.out.println("Your order is: "+ id + " " + list.getValue());
                    value = 1;
                }
            }
        }
    }

    public void writeFile(){
        File file = new File(outputFilePath);
        try (BufferedWriter bf = new BufferedWriter(new FileWriter("orders.txt"))) {
            for (Map.Entry<Integer, String> entry :
                    orderInfo.entrySet()) {

                // put key and value separated by a colon
                bf.write(entry.getKey() + ":"
                        + entry.getValue());

                // new line
                bf.newLine();
            }

            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // always close the writer
    }



    public int getId() {
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

    public Map<Integer, String> getOrderInfo() {
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