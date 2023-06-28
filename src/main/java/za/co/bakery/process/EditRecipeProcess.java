package za.co.bakery.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Recipe;
import za.co.bakery.model.RecipeIngredient;
import za.co.bakery.service.RecipeService;
import za.co.bakery.serviceImpl.RecipeServiceImpl;

public class EditRecipeProcess implements ProcessRequest{
    private RecipeService recipeService;
    private List<RecipeIngredient> lstRecipeIngredients;
    private Recipe recipe = null;
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session=request.getSession();
        String recipeName;
        if(session.getAttribute("recipeingredients") != null){
            lstRecipeIngredients = (List<RecipeIngredient>) session.getAttribute("recipeingredients");
        }
        else{
            lstRecipeIngredients = new ArrayList<>();
            session.setAttribute("recipeingredients", lstRecipeIngredients);
        }
        switch (process.toLowerCase()){
            case "er":
                recipeName = request.getParameter("recipename");
                if(recipeName != null && lstRecipeIngredients != null) {
                    recipeName = recipeName.toLowerCase().trim();
                    if(!recipeName.isEmpty()){
                        if (dBManager != null) {
                            recipeService = new RecipeServiceImpl(dBManager);
                            recipe = recipeService.getRecipeByName(recipeName);
                            if(recipe != null)  {
                                lstRecipeIngredients = recipeService.getRecipeIngredients(recipe.getRecipeId());
                                if(lstRecipeIngredients != null) {
                                    session.setAttribute("recipe", recipe);
                                    session.setAttribute("recipeingredients", lstRecipeIngredients);
                                    view = "";
                                }
                            }
                        }
                    }
                }
                //else print error message
                break;
            case "ers":
                recipeName = request.getParameter("recipename");
                String description = request.getParameter("description");
                recipe = (Recipe)session.getAttribute("recipe");
                if (recipeName != null && description != null && lstRecipeIngredients != null) {
                    description = description.toLowerCase().toLowerCase();
                    if(!description.isEmpty()){
                        if (dBManager != null){
                            if(recipe != null) {
                                recipe.setDescription(description);
                                recipe.setRecipeName(recipeName);
                                recipeService = new RecipeServiceImpl(dBManager);
                                recipeService.editRecipe(recipe, lstRecipeIngredients);
                                view = "";
                            }
                        }
                    }
                }
                //else print error message
                break;
        }
    }

    @Override
    public void processResponse(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(view);
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException ex) {
            System.out.println("Error Dispatching " + view + ex.getMessage());
        }
    }
    
}
