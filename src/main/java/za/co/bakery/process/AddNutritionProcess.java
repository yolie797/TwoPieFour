package za.co.bakery.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Nutrition;
import za.co.bakery.model.Recipe;
import za.co.bakery.service.NutritionService;
import za.co.bakery.service.RecipeService;
import za.co.bakery.serviceImpl.NutritionServiceImpl;
import za.co.bakery.serviceImpl.RecipeServiceImpl;

public class AddNutritionProcess implements ProcessRequest{
    private NutritionService nutritionService;
    private RecipeService recipeService;
    private List<Recipe> lstRecipe = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        switch (process.toLowerCase()){
            case "an":
                if (dBManager != null) {
                    recipeService = new RecipeServiceImpl(dBManager);
                    lstRecipe = recipeService.getAllRecipe();
                    request.setAttribute("recipes", lstRecipe);
                    view = "";
                }
                else {
                    view = error.processError("0x00001", response);
                }
                break;
            case "ans":
                String nutritionName = request.getParameter("nutritionname");
                double nutritionValue  = Double.parseDouble(request.getParameter("nutritionvalue"));
                String recipeName = request.getParameter("recipename");
                if(nutritionName != null && nutritionValue > 0 && recipeName != null) {
                    nutritionName = nutritionName.toLowerCase().trim();
                    recipeName = recipeName.toLowerCase().trim();
                    if (!nutritionName.isEmpty() && !recipeName.isEmpty()) {
                        if (dBManager != null){
                            recipeService = new RecipeServiceImpl(dBManager);
                            Recipe recipe = recipeService.getRecipeByName(recipeName);
                            if(recipe != null) {
                                Nutrition nutrition = new Nutrition(recipe.getRecipeId(), nutritionName, nutritionValue);
                                nutritionService = new NutritionServiceImpl(dBManager);
                                nutritionService.addRecipeNutrition(nutrition);
                                view = "";
                            }
                            else {
                                view = error.processError("0x00004", response);
                            }
                        }
                        else {
                            view = error.processError("0x00001", response);
                        }
                    }
                    else {
                        view = error.processError("0x00004", response);
                    }
                }
                else {
                    view = error.processError("0x00003", response);
                }
                break;
            default:
        }
    }

    @Override
    public void processResponse(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(view);
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException ex) {
            System.out.println("Error Dispatching view: " + ex.getMessage());
        }
    }
    
}
