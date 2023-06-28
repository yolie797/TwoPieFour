package za.co.bakery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import za.co.bakery.dao.ProductDao;
import za.co.bakery.model.Product;

public class ProductDaoImpl implements ProductDao {
    private static ProductDaoImpl productDaoImpl = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private List<Product> lstProduct = null;
    private Product product = null;

    private ProductDaoImpl(Connection dbConnection) {
        this.con = dbConnection;
    }
    
    public static ProductDaoImpl getInstance(Connection dbConnection){
        if(productDaoImpl == null){
            productDaoImpl = new ProductDaoImpl(dbConnection);
        }
        return productDaoImpl;
    } 
    
    @Override
    public List<Product> getProductByCategory(int categoryId) {
        if(lstProduct == null){
            getAllProducts();
        }
        return lstProduct.stream().filter(prod -> categoryId == prod.getCategoryId())
                                  .collect(Collectors.toList());
    }
    
    @Override
    public Product getProductByTitle(String title){
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT productId, recipeId, categoryId, title, description, warnings, price, picture, discount, active FROM product WHERE title=? AND active=1");
                ps.setString(1, title);
                rs = ps.executeQuery();
                while(rs.next()){
                product = new Product(rs.getInt("productId"), rs.getInt("recipeId"), rs.getInt("categoryId"), rs.getString("title"), rs.getString("description"),
                        rs.getString("warnings"), rs.getDouble("price"), rs.getString("picture"), rs.getInt("discount"), rs.getBoolean("active"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get productByTitle ERROR: " + exception.getMessage());
            }
            finally{
                if(ps != null){
                    try{
                        ps.close();
                    }
                    catch(SQLException exception){
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return product;
    }
    
    @Override
    public Product getProductById(int productId) {
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT productId, recipeId, categoryId, title, description, warnings, price, picture, discount, active FROM product WHERE productId=? AND active=1");
                ps.setInt(1, productId);
                rs = ps.executeQuery();
                while(rs.next()){
                product = new Product(rs.getInt("productId"), rs.getInt("recipeId"), rs.getInt("categoryId"), rs.getString("title"), rs.getString("description"),
                        rs.getString("warnings"), rs.getDouble("price"), rs.getString("picture"), rs.getInt("discount"), rs.getBoolean("active"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get productByTitle ERROR: " + exception.getMessage());
            }
            finally{
                if(ps != null){
                    try{
                        ps.close();
                    }
                    catch(SQLException exception){
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        lstProduct = new ArrayList<>();
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT productId, recipeId, categoryId, title, description, warnings, price, picture, discount, active FROM product WHERE active=1");
                rs = ps.executeQuery();
                while(rs.next()){
                    lstProduct.add(new Product(rs.getInt("productId"), rs.getInt("recipeId"), rs.getInt("categoryId"), rs.getString("title"), rs.getString("description"),
                        rs.getString("warnings"), rs.getDouble("price"), rs.getString("picture"), rs.getInt("discount"), rs.getBoolean("active")));
                }
            }
            catch(SQLException exception){
                System.out.println("Get allProduct ERROR: " + exception.getMessage());
            }
            finally{
                if(ps != null){
                    try{
                        ps.close();
                    }
                    catch(SQLException exception){
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return lstProduct;
    }

    @Override
    public boolean addProduct(Product product) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("INSERT INTO product (recipeId, categoryId, title, description, warnings , price, picture) values(?,?,?,?,?,?,?)");
                ps.setInt(1, product.getRecipeId());
                ps.setInt(2, product.getCategoryId());
                ps.setString(3, product.getTitle());
                ps.setString(4, product.getDescription());
                ps.setString(5, product.getWarnings());
                ps.setDouble(6, product.getPrice());
                ps.setString(7, product.getPicture());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Add product Error!!: " + exception.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException exception) {
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return retVal;
    }

    @Override
    public boolean editProduct(Product product) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE product SET recipeId=?, categoryId=?, title=?, description=?, warnings=?, price=?, picture=?, discount=? WHERE productId=?");
                ps.setInt(1, product.getRecipeId());
                ps.setInt(2, product.getCategoryId());
                ps.setString(3, product.getTitle());
                ps.setString(4, product.getDescription());
                ps.setString(5, product.getWarnings());
                ps.setDouble(6, product.getPrice());
                ps.setString(7, product.getPicture());
                ps.setInt(8, product.getDiscount());
                ps.setInt(9, product.getProductId());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Edit product Error!!: " + exception.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException exception) {
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return retVal;
    }

    @Override
    public boolean deleteProduct(Product product) {
           boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE product SET active=? WHERE productId=?");
                ps.setBoolean(1, product.isActive());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Delete product Error!!: " + exception.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException exception) {
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return retVal;
    }
}
