package lib.order;

import lib.crud.Read;
import java.io.IOException;
import java.util.ArrayList;

public class Product {
    private String id;
    private String name;
    private Long price;
    private String category;

    public Product(){

    }
    public Product(String id, String name, Long price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
