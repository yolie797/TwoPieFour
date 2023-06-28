package za.co.bakery.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.documents.dao.CreateReportDao;
import za.co.bakery.documents.impl.CreateReportImpl;
import za.co.bakery.model.Ingredient;
import za.co.bakery.model.Unit;
import za.co.bakery.service.IngredientService;
import za.co.bakery.service.UnitService;
import za.co.bakery.serviceImpl.IngredientServiceImpl;
import za.co.bakery.serviceImpl.UnitServiceImpl;

public class IngredientsReportProcess implements ProcessRequest {

    private IngredientService ingredientService;
    private UnitService unitService;
    private List<Ingredient> lstIngredients = new ArrayList<>();
    private List<Unit> lstUnits = new ArrayList<>();
    private CreateReportDao report = new CreateReportImpl();
    private String view;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        switch (process.toLowerCase()) {
            case "iis": //ingredients in stock
                if (dBManager != null) {
                    ingredientService = new IngredientServiceImpl(dBManager);
                    unitService = new UnitServiceImpl(dBManager);
                    lstIngredients = ingredientService.getAllIngredient();
                    lstUnits = unitService.getAllUnit();
                    report.createIngredientsInStockReport(lstIngredients, lstUnits, response);
                    request.setAttribute("ingredients", lstIngredients);
                } 
                break;
            case "ios": //ingredients out stock
                if (dBManager != null) {
                    ingredientService = new IngredientServiceImpl(dBManager);
                    unitService = new UnitServiceImpl(dBManager);
                    lstIngredients = ingredientService.getAllIngredient();
                    lstUnits = unitService.getAllUnit();
                    report.createIngredientsOutOfStockReport(lstIngredients, lstUnits, response);
                    request.setAttribute("ingredients", lstIngredients);
                }
                break;
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
