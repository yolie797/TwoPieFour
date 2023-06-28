package za.co.bakery.service;

import java.util.List;
import za.co.bakery.model.Product;

public interface ProductService {
    List<Product> getProductByCategory(int categoryId);
    Product getProductByTitle(String title);
    Product getProductById(int productId);
    List<Product> getAllProducts();
    boolean addProduct(Product product);
    boolean editProduct(Product product);
     boolean deleteProduct(Product product);
}
