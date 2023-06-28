package za.co.bakery.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Nutrition;
import za.co.bakery.model.Recipe;
import za.co.bakery.service.NutritionService;
import za.co.bakery.service.RecipeService;
import za.co.bakery.serviceImpl.NutritionServiceImpl;
import za.co.bakery.serviceImpl.RecipeServiceImpl;

public class EditNutritionProcess implements ProcessRequest{
    private NutritionService nutritionService;
    private RecipeService recipeService;
    private Nutrition nutrition;
    private List<Recipe> lstRecipe = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session=request.getSession();
        String nutritionName;
        switch (process.toLowerCase()){
            case "en":
                nutritionName = request.getParameter("nutritionname");
                if(nutritionName != null) {
                    nutritionName = nutritionName.toLowerCase().trim();
                    if(!nutritionName.isEmpty()) {
                        if (dBManager != null) {
                            nutritionService = new NutritionServiceImpl(dBManager);
                            recipeService = new RecipeServiceImpl(dBManager);
                            nutrition = nutritionService.getNutritionByName(nutritionName);
                            lstRecipe = recipeService.getAllRecipe();
                            session.setAttribute("nutrition", nutrition);
                            request.setAttribute("recipes", lstRecipe);
                            view = "";
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
            case "ens":
                nutritionName = request.getParameter("nutritionname");
                double nutritionValue = Double.parseDouble(request.getParameter("description"));
                String recipeName = request.getParameter("recipename");
                nutrition = (Nutrition)session.getAttribute("nutrition");
                if (nutritionName != null && nutritionValue > 0 && recipeName != null) {
                    nutritionName = nutritionName.toLowerCase().trim();
                    recipeName = recipeName.toLowerCase().trim();
                    if(!nutritionName.isEmpty() && !recipeName.isEmpty()){
                        if (dBManager != null){
                            Recipe recipe = recipeService.getRecipeByName(recipeName);
                            if(nutrition != null && recipe != null){
                                nutrition.setRecipeId(recipe.getRecipeId());
                                nutrition.setNutritionName(nutritionName);
                                nutrition.setNutritionValue(nutritionValue);
                                nutritionService.editRecipeNutrition(nutrition);
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
            System.out.println("Error Dispatching " + view + ex.getMessage());
        }
    }
    
}
