package product;

import com.sun.tools.javac.util.StringUtils;
import controller.Product;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProductService {

    private static final List<Product> PRODUCT_DATA = new ArrayList<>();

    private static ProductService productService;

    static {
        // Load products from file
        File fileData = new File("products.txt");
        try {
            Scanner scanner = new Scanner(fileData);
            scanner.nextLine(); // Skip header
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line != null && line.length() > 0) {
                    String[] data = line.split(",");
                    if (data.length >= 5) {
                        Product product = new Product();
                        product.setId(Integer.parseInt(data[0]));
                        product.setCategory(data[1]);
                        product.setName(data[2]);
                        product.setColor(data[3]);
                        product.setPrice(Long.parseLong(data[4]));
                        PRODUCT_DATA.add(product);
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static public ProductService getInstance() {
        if (productService == null)  {
            return new ProductService();
        }

        return productService;
    }

    public List<Product> listAll() {
        return PRODUCT_DATA;
    }

    public List<Product> listByCategory(String category) {
        if (category == null || category.length() == 0) {
            return Collections.emptyList();
        }

        return PRODUCT_DATA.stream()
                .filter(product -> product.getCategory() != null
                        && category.equalsIgnoreCase(product.getCategory()))
                .collect(Collectors.toList());
    }

    public List<Product> sortByPrice() {
        return PRODUCT_DATA.stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .collect(Collectors.toList());
    }

}
