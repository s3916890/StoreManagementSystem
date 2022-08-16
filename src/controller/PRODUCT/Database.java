public class DatabaseClass {

    private static Map<Long, Product> products=new HashMap<>();

    public static Map<Long, Product> getProduct()
    {
        return products;
    }

}
