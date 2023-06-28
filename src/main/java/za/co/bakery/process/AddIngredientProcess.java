package za.co.bakery.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Ingredient;
import za.co.bakery.model.Unit;
import za.co.bakery.service.IngredientService;
import za.co.bakery.service.UnitService;
import za.co.bakery.serviceImpl.IngredientServiceImpl;
import za.co.bakery.serviceImpl.UnitServiceImpl;

public class AddIngredientProcess implements ProcessRequest {
    private IngredientService ingredientService;
    private UnitService unitService;
    private List<Unit> lstUnits = new ArrayList<>();
    private Ingredient ingredientCheck = null;
    private ProcessError error = new ProcessErrorController();
    private String view;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        String name;
        String unitName;
        switch (process.toLowerCase()) {
            case "ai"://add new ingredient
                if (dBManager != null) {
                    unitService = new UnitServiceImpl(dBManager);
                    lstUnits = unitService.getAllUnit();
                    request.setAttribute("units", lstUnits);
                    view = "pages/addIngredient.jsp";
                }
                else {
                    view = error.processError("0x00001", response);
                }
                break;
            case "ais"://add new ingredient save
                name = request.getParameter("ingredientname");
                double minQuantity = Integer.parseInt(request.getParameter("minimumquantity"));
                unitName = request.getParameter("unitname");
                if(name != null && minQuantity > -1 && unitName != null) {
                    name = name.toLowerCase().trim();
                    unitName = unitName.toLowerCase().trim();
                    if(!name.isEmpty() && !unitName.isEmpty()) {
                        if (dBManager != null) {
                            ingredientService = new IngredientServiceImpl(dBManager);
                            unitService = new UnitServiceImpl(dBManager);
                            ingredientCheck = ingredientService.getIngedientByName(name);
                            Unit unit = unitService.getUnitByName(unitName);
                            if (ingredientCheck == null) {
                                if(unit != null) {
                                    Ingredient ingredient = new Ingredient(unit.getUnitId(), name, 0, minQuantity);
                                    ingredientService.addIngredient(ingredient);
                                    view = "tables/ingredient.jsp";
                                }
                                else {
                                    view = error.processError("0x00004", response);
                                }
                            }
                            else {
                                view = error.processError("0x00008", response);
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
            case "asi"://add stock ingredient
                name = request.getParameter("ingredientname");
                if(name != null) {
                    name = name.toLowerCase().trim();
                    if(!name.isEmpty()) {
                        if (dBManager != null) {
                            ingredientService = new IngredientServiceImpl(dBManager);
                            unitService = new UnitServiceImpl(dBManager);
                            Ingredient ingredient = ingredientService.getIngedientByName(name);
                            if(ingredient != null) {
                                Unit unit = unitService.getUnitById(ingredient.getUnitId());
                                request.setAttribute("ingredient", ingredient);
                                request.setAttribute("unit", unit);
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
                case "asis"://add stock ingredient save
                name = request.getParameter("ingredientname");
                double quantity = Integer.parseInt(request.getParameter("quantity"));
                unitName = request.getParameter("unitname");
                if(name != null && quantity > -1 && unitName != null) {
                    name = name.toLowerCase().trim();
                    unitName = unitName.toLowerCase().trim();
                    if(!name.isEmpty() && !unitName.isEmpty()) {
                        if (dBManager != null) {
                            ingredientService = new IngredientServiceImpl(dBManager);
                            unitService = new UnitServiceImpl(dBManager);
                            Ingredient ingredient = ingredientService.getIngedientByName(name);
                            if(ingredient != null) {
                                Unit unit = unitService.getUnitById(ingredient.getUnitId());
                                quantity = convertQuantity(quantity, unitName, unit.getUnitName());
                                ingredient.setQuantityInStock(ingredient.getQuantityInStock() + quantity);
                                if(quantity < 0) {
                                    ingredientService.editIngredient(ingredient);
                                    view = "";
                                }
                                else {
                                    view = error.processError("0x00004", response);
                                }
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
    
    private double convertQuantity(double quantity, String unitName, String ingredientUnitName){
        if(unitName.equals(ingredientUnitName)) {
            return quantity;
        }
        switch(unitName.toLowerCase()){
            case "kilograms":
            case "liters":
            case "units":
                if(ingredientUnitName.equals("kilograms") || ingredientUnitName.equals("liters") || ingredientUnitName.equals("units")) {
                    return quantity;
                }
            case "grams":
            case "mililiters":
                quantity = quantity/1000;
                break;
            case "teaspoon":
                if(ingredientUnitName.equals("kilograms")){
                    quantity = ((quantity*4.2)/1000);
                }
                if(ingredientUnitName.equals("liters")){
                    quantity = ((quantity*5)/1000);
                }
                break;
            case "tablespoon":
                if(ingredientUnitName.equals("kilograms")){
                    quantity = ((quantity*21.25)/1000);
                }
                if(ingredientUnitName.equals("liters")){
                    quantity = ((quantity*15)/1000);
                }
                break;
            case "cup":
                if(ingredientUnitName.equals("kilograms")){
                    quantity = ((quantity*4.2)/1000);
                }
                if(ingredientUnitName.equals("liters")){
                    quantity = ((quantity*240)/1000);
                }
                break;
            default:
                quantity = -1.0;
        }
        return quantity;
    }
}
