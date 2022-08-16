private Map<Long, Product> products=DatabaseClass.getProduct();

public ProductDaoImpl()
        {
        );
        }

//Get All products
public List<Product> getAllProducts() {

        return new ArrayList<Product>(products.values());
        }

//Get product by product id
public Product getProduct(long pid) {

        return products.get(pid);
        }

//To Add the products
public Product addProduct(Product product) {
        product.setPid(products.size()+1);
        products.put(product.getPid(), product);
        return product;
        }

//Update the product
public Product updateProduct(Product product) {
        if(product.getPid()<=0)
        {
        return null;
        }
        products.put(product.getPid(), product);
        return product;
        }

// Delete the product
public Product deleteProduct(long pid) {

        return products.remove(pid);

        }



//Get the product by category
public List<Product> getProductByCategory(String category) {

        if(products.size()<=0)
        {
        return null;
        }

        else if(category.equals(products.get(Product))
        {