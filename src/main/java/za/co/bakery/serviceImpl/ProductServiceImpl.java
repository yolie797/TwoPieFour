package za.co.bakery.serviceImpl;

import java.util.List;
import za.co.bakery.dao.ProductDao;
import za.co.bakery.dao.impl.ProductDaoImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Product;
import za.co.bakery.service.ProductService;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;

    public ProductServiceImpl(DBManager dbManager) {
        this.productDao = ProductDaoImpl.getInstance(dbManager.getConnection());
    }

    @Override
    public List<Product> getProductByCategory(int categoryId) {
        return categoryId > 0 ? productDao.getProductByCategory(categoryId):null;
    }
    
    @Override
    public Product getProductByTitle(String title){
        title = title.toLowerCase();
        return title != null ? productDao.getProductByTitle(title):null;
    }
    
    @Override
    public Product getProductById(int productId) {
        return productId > 0 ? productDao.getProductById(productId):null;
    }
    
    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public boolean addProduct(Product product) {
        return product != null ? productDao.addProduct(product):false;
    }

    @Override
    public boolean editProduct(Product product) {
        return product != null ? productDao.editProduct(product):false;
    }

    @Override
    public boolean deleteProduct(Product product) {
        return product != null ? productDao.deleteProduct(product):false;
    }

}
