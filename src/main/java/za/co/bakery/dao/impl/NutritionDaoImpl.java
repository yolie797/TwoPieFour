
package za.co.bakery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakery.dao.NutritionDao;
import za.co.bakery.model.Nutrition;

public class NutritionDaoImpl implements NutritionDao{
    private static NutritionDao nutritionDao = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private List<Nutrition> lstNutrition;
    
    private NutritionDaoImpl(Connection con) {
        this.con = con;
    }

    public static NutritionDao getInstance(Connection dbCon) {
        if (nutritionDao == null) {
            nutritionDao = new NutritionDaoImpl(dbCon);
        }
        return nutritionDao;
    }

    @Override
    public List<Nutrition> getAllNutrition(){
        lstNutrition = new ArrayList<>();
        if(con != null){
            try{
                ps = con.prepareStatement("Select nutritionId, recipeId, nutrtitionName, nutrtitionValue FROM nutritional_information");
                rs = ps.executeQuery();
                while(rs.next()){
                    lstNutrition.add(new Nutrition(rs.getInt("nutritionId"), rs.getInt("recipeId"), rs.getString("nutrtitionName"), rs.getDouble("nutrtitionValue")));
                }
            }
            catch(SQLException exception){
                System.out.println("Get productNutrition ERROR: " + exception.getMessage());
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
        return lstNutrition;
    }
    
    @Override
    public List<Nutrition> getRecipeNutrition(int recipeID) {
       lstNutrition = new ArrayList<>();
        if(con != null){
            try{
                ps = con.prepareStatement("Select nutritionId, recipeId, nutrtitionName, nutrtitionValue FROM nutritional_information WHERE recipeId=?");
                ps.setInt(1, recipeID);
                rs = ps.executeQuery();
                while(rs.next()){
                    lstNutrition.add(new Nutrition(rs.getInt("nutritionId"), rs.getInt("recipeId"), rs.getString("nutrtitionName"), rs.getDouble("nutrtitionValue")));
                }
            }
            catch(SQLException exception){
                System.out.println("Get productNutrition ERROR: " + exception.getMessage());
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
        return lstNutrition;
    }
    
    
    @Override
    public Nutrition getNutritionByName(String nutritionName) {
        Nutrition nutrition = null;
        if(con != null){
            try{
                ps = con.prepareStatement("Select nutritionId, recipeId, nutrtitionName, nutrtitionValue FROM nutritional_information WHERE nutriotionName=?");
                ps.setString(1, nutritionName);
                rs = ps.executeQuery();
                while(rs.next()){
                    nutrition = new Nutrition(rs.getInt("nutritionId"), rs.getInt("recipeId"), rs.getString("nutrtitionName"), rs.getDouble("nutrtitionValue"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get nutritionByName ERROR: " + exception.getMessage());
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
        return nutrition;
    }

    @Override
    public boolean addRecipeNutrition(Nutrition nutrition) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("INSERT INTO nutritional_information (recipeId, nutritionName, nutritionValue) values(?,?,?)");
                ps.setInt(1, nutrition.getRecipeId());
                ps.setString(2, nutrition.getNutritionName());
                ps.setDouble(3, nutrition.getNutritionValue());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Add productNutrition Error!!: " + exception.getMessage());
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
    public boolean editRecipeNutrition(Nutrition nutrition) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE recipe SET nutritionName=?, nutritionValue=? WHERE recipeId=?");
                ps.setString(1, nutrition.getNutritionName());
                ps.setDouble(2, nutrition.getNutritionValue());
                ps.setInt(3, nutrition.getRecipeId());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Edit recipeNutrition Error!!: " + exception.getMessage());
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
    public boolean deleteNutrition(Nutrition nutrition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
