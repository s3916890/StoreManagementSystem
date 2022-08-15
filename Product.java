package controller;

public class Product {
    // ID,Category,Name,Color,Price
    private int id;
    private String category;
    private String name;
    private String color;
    private Long price;


    public Product() {

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

    public void add() {

    }

    @Override
    public String toString() {
        return String.format("Product(id:%d, category:%s, name:%s, color:%s, price: %d) \n",
                this.id, this.category, this.name, this.color, this.price);
    }
}
