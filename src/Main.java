import controller.Order;
import controller.Product;
import controller.Users;
import view.Menu;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
//        menu.view();

        Users user = new Users(1, "40.karma", "0909150472");
        Product product = new Product(1, "laptop", "Macbook Pro 13 inch 2016 Four Thunderbolt 3 Port MLH12 TouchBar Core i5 6267U", "silver",
                Long.parseLong("17900000"));
        Order order = new Order();

//        order.createNewOrder(user, product);
        order.searchOrder();
    }

}
