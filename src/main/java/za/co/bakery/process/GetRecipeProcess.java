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
import za.co.bakery.model.Recipe;
import za.co.bakery.service.ProductService;
import za.co.bakery.service.RecipeService;
import za.co.bakery.serviceImpl.RecipeServiceImpl;

public class GetRecipeProcess implements ProcessRequest {

    private RecipeService recipeService;
    private ProductService productService;
    private List<Recipe> lstRecipe = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    String view = "";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        switch (process.toLowerCase()) {
            case "gr"://get all recipe
                if (dBManager != null) {
                    recipeService = new RecipeServiceImpl(dBManager);
                    lstRecipe = recipeService.getAllRecipe();
                    if (lstRecipe != null) {
                        request.setAttribute("recipes", lstRecipe);
                        view = "admin/recipe.jsp";
                    } else {
                        view = error.processError("0x00004", response);
                    }
                } else {
                    view = error.processError("0x00001", response);
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
