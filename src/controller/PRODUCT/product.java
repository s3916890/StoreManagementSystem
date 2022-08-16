import lib.OptionInput;
import lib.algorithm.search.BoyerMoore;
import lib.crud.Read;
import view.Menu;
import java.util.Scanner;;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
public class Product {
    private int id;
    private String name;
    private Long price;
    private String category;

    private String color;

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

    public ArrayList<Long> getAllPrice(){
        String[] readPrice = Read.readSpecificColumn(4, "products.txt", ",");


        ArrayList<Long> priceList = new ArrayList<>(readPrice.length);

        for(int i = 1; i < readPrice.length; i++){
            priceList.add(Long.parseLong(readPrice[i]));
        }

        return priceList;
    }

    public void add(){

    }

    public void remove(){

    }

    //** Child class**
// creates the comparator for comparing price value
    private static class PriceComparator implements Comparator<Product> {

        // Function to compare
        public int compare(Product p1, Product p2)
        {
            if (p1.price == p2.price)
                return 0;
            else if (p1.price > p2.price)
                return 1;
            else
                return -1;
        }
    }
    //LIST PRODUCT N VIEW PRODUCT DETAILS
    public void view() throws IOException {
        ReadAllLine line = new ReadAllLine();

        ArrayList<String[]> products = line.read("products.txt");

        for(int i = 0; i < products.size() - 1; i++){
            System.out.println("\nID: " + products.get(i)[0] + ", Category:" + products.get(i)[1] + ", Name:" +
                    products.get(i)[2] + ", Color:" + products.get(i)[3] + ", Price:" + products.get(i)[4] + " VND");
        }
    }
    //SORT ALL OF THE PRODUCTS BY PRICE (ASCENDING OR DESCENDING)
//Main driver method
    public void view() throws IOException {
        ReadAllLine line = new ReadAllLine();
        Scanner s = new Scanner(System.in);
        ArrayList<String[]> products = line.read("products.txt");
        //****** USER INPUT VALIDATOR*******
        System.out.println("Press 1 if you want to sort by price in ascending order or press 2 if you want to sort by price in descending order")
        //Sorting Arraylist in Java on ASCENDING order defined by Comparator
        while input == 1
        Collections.sort(Products,new PriceComparator());
        System.out.println("Products sorted by price in ascending order: "Products);
        //Sorting Arraylist in Java on DESCENDING order defined by Comparator
        if input == 2
        Collections.sort(Products, Collections.reverseOrder());
        System.out.println("Products sorted by price in descending order: " + unsortedList);

    }
}
