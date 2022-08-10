package lib.order;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Order {
    private int id;
    private Users user;
    private Product product;
    private Date date;
    // private Map<Integer, String> orderInfo;
    private ArrayList<String> orderInfo;
    final String outputFilePath = "StoreManagementSystem/src/orders";

    public Order() {
    }

    public Order(int id, Users user, Product goods) {
        this.id = id;
        this.user = user;
        this.product = goods;

        orderInfo = new ArrayList<>();
        date = new Date();
    }

    public String detail(Users user, Product product) {
        return user.getName() + " " + product.getName();
    }

    public int generateID() {
        return (int) (Math.random() * 100 + 1);
    }
    public void printOrder(){
        for (String name : orderInfo) {
            System.out.println(name);
        }
    }

    public void createNewOrder(Users user, Product product) {
        this.id = 0;
        String orderDetail = detail(user, product);
        orderInfo.add(orderDetail);
        for (int i = 0; i < orderInfo.size(); i++) {
            if (orderInfo.get(i).contains(orderDetail))
                this.id = i;
            System.out.println("Your order id: " + this.id);
        }
    }

    public void searchOrder(){
        Scanner s = new Scanner(System.in);
        int value = 0;
        while (value != 1){
            System.out.print("Id search: ");
            int id = s.nextInt();
            if(id >= orderInfo.size() || id < 0){
                System.out.println("Invalid id");
            }else{
                System.out.println("Your order is: "+ id + " " + orderInfo.get(id));
                value = 1;
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