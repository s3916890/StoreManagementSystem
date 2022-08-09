import lib.order.Order;
import lib.order.Product;
import lib.order.Users;
import view.Menu;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
//        menu.view();
        Users user = new Users(1, "40.karma", "0909150472");
        Product product = new Product(1, "laptop", "Macbook Pro 13 inch Retina 2015 MLL42 Core i5 6360U", "silver",
                Long.parseLong("17900000"));
    }

}
