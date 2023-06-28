package za.co.bakery.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Ingredient;
import za.co.bakery.model.Recipe;
import za.co.bakery.model.RecipeIngredient;
import za.co.bakery.model.Unit;
import za.co.bakery.service.IngredientService;
import za.co.bakery.service.RecipeIngredientService;
import za.co.bakery.service.RecipeService;
import za.co.bakery.service.UnitService;
import za.co.bakery.serviceImpl.IngredientServiceImpl;
import za.co.bakery.serviceImpl.RecipeIngredientServiceImpl;
import za.co.bakery.serviceImpl.RecipeServiceImpl;
import za.co.bakery.serviceImpl.UnitServiceImpl;

public class AddRecipeProcess implements ProcessRequest{
    private RecipeService recipeService;
    private IngredientService ingredientService;
    private RecipeIngredientService recipeIngredientService;
    private UnitService unitService;
    private List<Ingredient> lstIngredients = new ArrayList<>();
    private List<RecipeIngredient> lstRecipeIngredients;
    private List<Unit> lstUnits = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session = request.getSession();
        if(session.getAttribute("recipeingredients") != null){
            lstRecipeIngredients = (List<RecipeIngredient>) session.getAttribute("recipeingredients");
        }
        else{
            lstRecipeIngredients = new ArrayList<>();
            session.setAttribute("recipeingredients", lstRecipeIngredients);
        }
        switch (process.toLowerCase()){
            case "ar"://add recipe
                if (dBManager != null) {
                    ingredientService = new IngredientServiceImpl(dBManager);
                    unitService = new UnitServiceImpl(dBManager);
                    lstIngredients = ingredientService.getAllIngredient();
                    lstUnits = unitService.getAllUnit();
                    if(lstIngredients != null && lstUnits != null) {
                        request.setAttribute("ingredients", lstIngredients);
                        request.setAttribute("units", lstUnits);
                        view = "";
                    }
                    else {
                        view = error.processError("0x00004", response);
                    }
                }
                else {
                    view = error.processError("0x00001", response);
                }
                break;
            case "arsi"://add recipe save ingredient
                String ingredient = request.getParameter("ingredientname");
                String unit = request.getParameter("unit");
                double quantity = Double.parseDouble(request.getParameter("quantity"));
                if(ingredient != null && unit != null && quantity > 0.0 && lstRecipeIngredients != null){
                    ingredient = ingredient.toLowerCase().trim();
                    unit = unit.toLowerCase().trim();
                    if(!ingredient.isEmpty() && !unit.isEmpty()){
                        if(dBManager != null){
                            ingredientService = new IngredientServiceImpl(dBManager);
                            unitService = new UnitServiceImpl(dBManager);
                            Ingredient ingredientObj = ingredientService.getIngedientByName(ingredient);
                            Unit unitObj = unitService.getUnitByName(unit);
                            if(ingredientObj != null && unitObj != null) {
                                recipeIngredientService = new RecipeIngredientServiceImpl();
                                lstRecipeIngredients.add(recipeIngredientService
                                        .addRecipeIngredient(ingredientObj.getIngredientId(), unitObj.getUnitId(), quantity));
                                session.setAttribute("recipeingredients", lstRecipeIngredients);
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
            case "ars"://add recipe save
                String recipename = request.getParameter("recipename");
                String description = request.getParameter("description");
                if(recipename != null && description != null) {
                    recipename = recipename.trim();
                    description = description.trim();
                    if(!recipename.isEmpty() && !description.isEmpty()) {
                        if(dBManager != null){
                            Recipe recipe = new Recipe(description, recipename);
                            if(recipe != null){
                                recipeService = new RecipeServiceImpl(dBManager);
                                recipeService.addRecipe(recipe, lstRecipeIngredients);
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
                    error.processError("0x00003", response);
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
