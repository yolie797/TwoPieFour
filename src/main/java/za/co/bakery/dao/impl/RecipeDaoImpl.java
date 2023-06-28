
package za.co.bakery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakery.dao.RecipeDao;
import za.co.bakery.model.Recipe;
import za.co.bakery.model.RecipeIngredient;


public class RecipeDaoImpl implements RecipeDao{
    private static RecipeDao recipeDao = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private List<Recipe> lstRecipe;
    private List<RecipeIngredient> lstRecipeIngredient;
    
    private RecipeDaoImpl(Connection con) {
        this.con = con;
    }

    public static RecipeDao getInstance(Connection dbCon) {
        if (recipeDao == null) {
            recipeDao = new RecipeDaoImpl(dbCon);
        }
        return recipeDao;
    }
    
    @Override
    public List<Recipe> getAllRecipe() {
        lstRecipe = new ArrayList<>();
        if(con != null){
            try{
                ps = con.prepareStatement("Select recipeId, description, recipeName, active FROM recipe WHERE active=1");
                rs = ps.executeQuery();
                while(rs.next()){
                    lstRecipe.add(new Recipe(rs.getInt("recipeId"), rs.getString("description"), rs.getString("recipeName"), rs.getBoolean("active")));
                }
            }
            catch(SQLException exception){
                System.out.println("Get allRecipe ERROR: " + exception.getMessage());
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
        return !lstRecipe.isEmpty() ? lstRecipe : null;
    }

    @Override
    public Recipe getRecipeByName(String recipeName) {
        Recipe recipe = null;
        if(con != null){
            try{
                ps = con.prepareStatement("Select recipeId, description, recipeName, active FROM recipe WHERE recipeName=?");
                ps.setString(1, recipeName);
                rs = ps.executeQuery();
                while(rs.next()){
                    recipe = new Recipe(rs.getInt("recipeId"), rs.getString("description"), rs.getString("recipeName"), rs.getBoolean("active"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get recipeByName ERROR: " + exception.getMessage());
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
        return recipe;
    }
    
    @Override
    public Recipe getRecipeById(int recipeId) {
        Recipe recipe = null;
        if(con != null){
            try{
                ps = con.prepareStatement("Select recipeId, description, recipeName, active FROM recipe WHERE recipeId=?");
                ps.setInt(1, recipeId);
                rs = ps.executeQuery();
                while(rs.next()){
                    recipe = new Recipe(rs.getInt("recipeId"), rs.getString("description"), rs.getString("recipeName"), rs.getBoolean("active"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get recipeById ERROR: " + exception.getMessage());
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
        return recipe;
    }

    @Override
    public List<RecipeIngredient> getRecipeIngredients(int recipeId) {
        lstRecipeIngredient = new ArrayList<>();
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT i.ingredientId, u.unitId, ri.recipeId, ri.quantity FROM ingredient i, recipe_ingredient ri, unit u"
                        + " WHERE ri.recipeId=? AND ri.ingredientId = i.ingredientId AND ri.unitId = u.unitId");
                ps.setInt(1, recipeId);
                rs = ps.executeQuery();
                while(rs.next()){
                    lstRecipeIngredient.add(new RecipeIngredient(rs.getInt("recipeId"),rs.getInt("ingredientId"), rs.getInt("unitId"), rs.getDouble("quantity")));
                }
            }
            catch(SQLException exception){
                System.out.println("Get productRecipe ERROR: " + exception.getMessage());
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
        return !lstRecipeIngredient.isEmpty() ? lstRecipeIngredient : null;
    }

    @Override
    public boolean addRecipe(Recipe recipe, List<RecipeIngredient> lstRecipeIngredient) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("INSERT INTO recipe (description, recipeName) values(?,?)");
                ps.setString(1, recipe.getDescription());
                ps.setString(2, recipe.getRecipeName());
                ps.execute();
                recipe = getRecipeByName(recipe.getRecipeName());
                ps = con.prepareStatement("INSERT INTO recipe_ingredient (recipeId, ingredientId, quantity) values(?,?,?)");
                for(RecipeIngredient ing : lstRecipeIngredient) {
                    ps.setInt(1, recipe.getRecipeId());
                    ps.setInt(2, ing.getIngredientId());
                    ps.setDouble(3, ing.getQuantity());
                    ps.execute();
                }
                retVal = true;
            } catch (SQLException exception) {
                System.out.println("Add recipe Error!!: " + exception.getMessage());
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
    public boolean editRecipe(Recipe recipe, List<RecipeIngredient> lstRecipeIngredient) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("DELETE FROM recipe_ingredient WHERE recipeId=?");
                ps.setInt(1, recipe.getRecipeId());
                ps.execute();
                ps = con.prepareStatement("UPDATE recipe SET description=?, recipeName=? WHERE recipeId=?");
                ps.setString(1, recipe.getDescription());
                ps.setString(2, recipe.getRecipeName());
                ps.setInt(3, recipe.getRecipeId());
                ps.execute();
                ps = con.prepareStatement("INSERT INTO recipe_ingredient (recipeId, ingredientId, quantity) values(?,?,?)");
                for(RecipeIngredient ing : lstRecipeIngredient) {
                    ps.setInt(1, recipe.getRecipeId());
                    ps.setInt(2, ing.getIngredientId());
                    ps.setDouble(3, ing.getQuantity());
                    ps.execute();
                }
                retVal = true;
            } catch (SQLException exception) {
                System.out.println("Edit recipe Error!!: " + exception.getMessage());
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
    public boolean deleteRecipe(Recipe recipe) {
         boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE recipe SET active=? WHERE recipeId=?");
                ps.setBoolean(1, recipe.isActive());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Delete recipe Error!!: " + exception.getMessage());
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
