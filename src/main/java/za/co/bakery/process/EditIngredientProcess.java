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
import za.co.bakery.model.Ingredient;
import za.co.bakery.model.Unit;
import za.co.bakery.service.IngredientService;
import za.co.bakery.service.UnitService;
import za.co.bakery.serviceImpl.IngredientServiceImpl;
import za.co.bakery.serviceImpl.UnitServiceImpl;

public class EditIngredientProcess implements ProcessRequest {
    private IngredientService ingredientService;
    private UnitService unitService;
    private Ingredient ingredient = null;
    private List<Unit> lstUnit = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    private String view;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session=request.getSession();
        String name;
        switch (process.toLowerCase()){
            case "ei"://edit ingredient
                name = request.getParameter("ingredientname");
                if(name != null) {
                    name = name.toLowerCase().trim();
                    if(!name.isEmpty()) {
                        if (dBManager != null) {
                            ingredientService = new IngredientServiceImpl(dBManager);
                            unitService = new UnitServiceImpl(dBManager);
                            ingredient = ingredientService.getIngedientByName(name);
                            if(ingredient != null) {
                                Unit unit = unitService.getUnitById(ingredient.getUnitId());
                                lstUnit = unitService.getAllUnit();
                                session.setAttribute("ingredient", ingredient);
                                request.setAttribute("unit", unit);
                                request.setAttribute("units", lstUnit);
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
            case "eis"://edit ingredient save
                name = request.getParameter("ingredientname");
                double minQuantity = Integer.parseInt(request.getParameter("minimumquantity"));
                String unitName = request.getParameter("unitname");
                if (name != null && minQuantity > -1 && unitName != null) {
                    name = name.toLowerCase().trim();
                    unitName = unitName.toLowerCase().trim();
                    if(!name.isEmpty() && !unitName.isEmpty()) {
                        if (dBManager != null) {
                            ingredientService = new IngredientServiceImpl(dBManager);
                            unitService = new UnitServiceImpl(dBManager);
                            ingredient = ingredientService.getIngedientByName(name);
                            Unit unit = unitService.getUnitByName(unitName);
                            if(ingredient != null && unit != null) {
                                ingredient.setName(name);
                                ingredient.setMinStockQuantity(minQuantity);
                                ingredientService.editIngredient(ingredient);
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
        } 
        catch (ServletException | IOException ex) {
            System.out.println("Error Dispatching " + view + ": " + ex.getMessage());
        }
    }
}
