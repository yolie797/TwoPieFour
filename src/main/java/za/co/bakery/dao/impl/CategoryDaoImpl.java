package za.co.bakery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakery.dao.CategoryDao;
import za.co.bakery.model.Category;

public class CategoryDaoImpl implements CategoryDao{
    private static CategoryDao categoryDao = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private List<Category> lstCategory;
    
    private CategoryDaoImpl(Connection con) {
        this.con = con;
    }

    public static CategoryDao getInstance(Connection dbCon) {
        if (categoryDao == null) {
            categoryDao = new CategoryDaoImpl(dbCon);
        }
        return categoryDao;
    }
    
    @Override
    public List<Category> getAllCategory() {
        lstCategory = new ArrayList<>();
        if(con != null){
            try{
                ps = con.prepareStatement("Select categoryId, categoryName, active FROM category WHERE active=1");
                rs = ps.executeQuery();
                while(rs.next()){
                    lstCategory.add(new Category(rs.getInt("categoryId"), rs.getString("categoryName"), rs.getBoolean("active")));
                }
            }
            catch(SQLException exception){
                System.out.println("Get allCategory ERROR: " + exception.getMessage());
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
        return lstCategory;
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        Category category = null;
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT categoryId, categoryName, active FROM category WHERE categoryName=?");
                ps.setString(1, categoryName);
                rs = ps.executeQuery();
                while(rs.next()){
                    category = new Category(rs.getInt("categoryId"), rs.getString("categoryName"), rs.getBoolean("active"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get categoryByName ERROR: " + exception.getMessage());
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
        return category;
    }

    @Override
    public Category getCategoryById(int categoryId) {
        Category category = null;
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT categoryId, categoryName, active FROM category WHERE categoryId=?");
                ps.setInt(1, categoryId);
                rs = ps.executeQuery();
                while(rs.next()){
                    category = new Category(rs.getInt("categoryId"), rs.getString("categoryName"), rs.getBoolean("active"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get categoryById ERROR: " + exception.getMessage());
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
        return category;
    }

    @Override
    public boolean addCategory(Category category) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("INSERT INTO category (categoryName) values(?)");
                ps.setString(1, category.getCategoryName());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Add category Error!!: " + exception.getMessage());
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
    public boolean editCategory(Category category) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE category SET categoryName=? WHERE categoryId=?");
                ps.setString(1, category.getCategoryName());
                ps.setInt(1, category.getCategoryId());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Edit category Error!!: " + exception.getMessage());
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
    public boolean deleteCategory(Category category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
