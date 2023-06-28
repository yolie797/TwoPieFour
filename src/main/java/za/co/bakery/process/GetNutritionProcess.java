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

public class GetNutritionProcess implements ProcessRequest{
    private NutritionService nutritionService;
    private RecipeService recipeService;
    private List<Nutrition> lstNutrition = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    private String view;
            
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        switch (process.toLowerCase()) {
            case "gan"://get all nutrition
                if(dBManager != null) {
                    nutritionService = new NutritionServiceImpl(dBManager);
                    lstNutrition = nutritionService.getAllNutrition();
                    request.setAttribute("nutritions", lstNutrition);
                    view = "";
                }
                else {
                    view = error.processError("0x00001", response);
                }
                break;
            case "gnn"://get nutrition by name
                String nutritionName = request.getParameter("nutritionname");
                if(nutritionName != null) {
                    nutritionName = nutritionName.toLowerCase().trim();
                    if(!nutritionName.isEmpty()) {
                        if(dBManager != null) {
                            nutritionService = new NutritionServiceImpl(dBManager);
                            Nutrition nutrition = nutritionService.getNutritionByName(nutritionName);
                            request.setAttribute("nutrition", nutrition);
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
            case "grn"://get recipe nutrition
                String recipeName = request.getParameter("recipename");
                if(recipeName != null) {
                    recipeName = recipeName.toLowerCase().trim();
                    if(!recipeName.isEmpty()) {
                        if(dBManager != null) {
                            nutritionService = new NutritionServiceImpl(dBManager);
                            recipeService = new RecipeServiceImpl(dBManager);
                            Recipe recipe = recipeService.getRecipeByName(recipeName);
                            if(recipe != null) {
                                lstNutrition = nutritionService.getRecipeNutrition(recipe.getRecipeId());
                                request.setAttribute("nutritions", lstNutrition);
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
                    view = error.processError("0x00004", response);
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
            System.out.println("Error Dispatching " + view + ": " + ex.getMessage());
        }
    }
    
}
