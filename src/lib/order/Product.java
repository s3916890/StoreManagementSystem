package lib.order;

import lib.crud.Read;
import java.io.IOException;
import java.util.ArrayList;

public class Product {
    // ID,Category,Name,Color,Price
    private int id;
    private String category;
    private String name;
    private String color;
    private Long price;


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

    public void setColor(String color) {
        this.color = color;
    }

    public void add(){

    }

    public void remove(){

    }

    public void view() throws IOException {
        Read line = new Read();

        ArrayList<String[]> products = line.readAllLine("products.txt");

        for(int i = 0; i < products.size() - 1; i++){
            System.out.println("\nID: " + products.get(i)[0] + ", Category:" + products.get(i)[1] + ", Name:" +
                    products.get(i)[2] + ", Price:" + products.get(i)[3] + " VND");
        }
    }
}
