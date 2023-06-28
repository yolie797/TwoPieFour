package za.co.bakery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakery.dao.IngredientDao;
import za.co.bakery.model.Ingredient;


public class IngredientDaoImpl implements IngredientDao{
    private static IngredientDaoImpl ingredientDaoImpl = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    List<Ingredient> lstIngredient = null;

    private IngredientDaoImpl(Connection dbConnection) {
        this.con = dbConnection;
    }

    public static IngredientDaoImpl getInstance(Connection dbConnection) {
        if (ingredientDaoImpl == null) {
            ingredientDaoImpl = new IngredientDaoImpl(dbConnection);
        }
        return ingredientDaoImpl;
    }

    @Override
    public List<Ingredient> getAllIngredient() {
        lstIngredient = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("SELECT ingredientID, unitId, name, quantityInStock, minStockQuantity, active FROM ingredient");
                rs = ps.executeQuery();
                while (rs.next()) {
                    lstIngredient.add(new Ingredient(rs.getInt("ingredientID"), rs.getInt("unitId"), rs.getString("name"), rs.getInt("quantityInStock"), rs.getDouble("minStockQuantity"), rs.getBoolean("active")));
                }
            } catch (SQLException exception) {
                System.out.println("Get all ingredient ERROR: " + exception.getMessage());
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
        return lstIngredient;
    }
    
    @Override
    public Ingredient getIngedientByName(String name) {
        Ingredient ingredient = null;
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT ingredientID, unitId, name, quantityInStock, minStockQuantity, active FROM ingredient WHERE name=?");
                ps.setString(1, name);
                rs = ps.executeQuery();
                while(rs.next()){
                    ingredient = new Ingredient(rs.getInt("ingredientID"), rs.getInt("unitId"), rs.getString("name"), rs.getInt("quantityInStock"), rs.getDouble("minStockQuantity"), rs.getBoolean("active"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get ingredientByName ERROR: " + exception.getMessage());
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
        return ingredient;
    }
    
    @Override
    public Ingredient getIngedientById(int ingredientId) {
         Ingredient ingredient = null;
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT ingredientID, unitId, name, quantityInStock, minStockQuantity, active FROM ingredient WHERE ingredientID=?");
                ps.setInt(1, ingredientId);
                rs = ps.executeQuery();
                while(rs.next()){
                    ingredient = new Ingredient(rs.getInt("ingredientID"), rs.getInt("unitId"), rs.getString("name"), rs.getInt("quantityInStock"), rs.getDouble("minStockQuantity"), rs.getBoolean("active"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get ingredientById ERROR: " + exception.getMessage());
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
        return ingredient;
    }

    @Override
    public boolean addIngredient(Ingredient ingredient) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("INSERT INTO ingredient (unitId, name, quantityInStock, minStockQuantity) values(?,?,?,?)");
                ps.setInt(1, ingredient.getUnitId());
                ps.setString(2, ingredient.getName());
                ps.setDouble(3, ingredient.getQuantityInStock());
                ps.setDouble(4, ingredient.getMinStockQuantity());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Add ingredient Error!!: " + exception.getMessage());
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
    public boolean editIngredient(Ingredient ingredient) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE ingredient SET unitId=?, name=?, quantityInStock=?, minStockQuantity=? WHERE ingredientID=?");
                ps.setInt(1, ingredient.getUnitId());
                ps.setString(2, ingredient.getName());
                ps.setDouble(3, ingredient.getQuantityInStock());
                ps.setDouble(4, ingredient.getMinStockQuantity());
                ps.setInt(5, ingredient.getIngredientId());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Edit ingredient Error!!: " + exception.getMessage());
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
    public boolean deleteIngredient(Ingredient ingredient) {
         boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE ingredient SET active=? WHERE ingredientId=?");
                ps.setBoolean(1, ingredient.isActive());
                ps.setInt(2, ingredient.getIngredientId());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Delete ingredient Error!!: " + exception.getMessage());
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
