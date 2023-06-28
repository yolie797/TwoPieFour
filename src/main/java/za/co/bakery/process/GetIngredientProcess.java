package za.co.bakery.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Ingredient;
import za.co.bakery.service.IngredientService;
import za.co.bakery.serviceImpl.IngredientServiceImpl;

public class GetIngredientProcess implements ProcessRequest {

    private IngredientService ingredientService;
    private List<Ingredient> lstIngredient = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    private String view;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        switch (process.toLowerCase()) {
            case "gi"://get all ingredients
                if (dBManager != null) {
                    ingredientService = new IngredientServiceImpl(dBManager);
                    lstIngredient = ingredientService.getAllIngredient();
                    request.setAttribute("ingredients", lstIngredient);
                    view = "admin/ingredient.jsp";
                } else {
                    view = error.processError("0x00001", response);
                }
                break;
            case "giis"://get ingredients in stock
                if (dBManager != null) {
                    ingredientService = new IngredientServiceImpl(dBManager);
                    lstIngredient = ingredientService.getAllIngredient();
                    lstIngredient = lstIngredient.stream().filter(i -> (i.getMinStockQuantity() < i.getQuantityInStock())).collect(Collectors.toList());
                    Collections.sort(lstIngredient, new Ingredient());
                    request.setAttribute("ingredients", lstIngredient);
                    view = "";
                }
                break;
            case "gios"://get ingredients out of stock
                if (dBManager != null) {
                    ingredientService = new IngredientServiceImpl(dBManager);
                    lstIngredient = ingredientService.getAllIngredient();
                    lstIngredient = lstIngredient.stream().filter(i -> (i.getMinStockQuantity() > i.getQuantityInStock())).collect(Collectors.toList());
                    Collections.sort(lstIngredient, new Ingredient());
                    request.setAttribute("ingredients", lstIngredient);
                    view = "";
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
            System.out.println("Error Dispatching " + view + ": " + ex.getMessage());
        }
    }

}
