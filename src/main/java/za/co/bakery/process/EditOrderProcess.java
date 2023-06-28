package za.co.bakery.process;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Ingredient;
import za.co.bakery.model.Order;
import za.co.bakery.model.OrderLineItem;
import za.co.bakery.model.Product;
import za.co.bakery.model.RecipeIngredient;
import za.co.bakery.model.Unit;
import za.co.bakery.service.IngredientService;
import za.co.bakery.service.OrderLineItemService;
import za.co.bakery.service.OrderService;
import za.co.bakery.service.ProductService;
import za.co.bakery.service.RecipeService;
import za.co.bakery.service.UnitService;
import za.co.bakery.serviceImpl.IngredientServiceImpl;
import za.co.bakery.serviceImpl.OrderLineItemServiceImpl;
import za.co.bakery.serviceImpl.OrderServiceImpl;
import za.co.bakery.serviceImpl.ProductServiceImpl;
import za.co.bakery.serviceImpl.RecipeServiceImpl;
import za.co.bakery.serviceImpl.UnitServiceImpl;

public class EditOrderProcess implements ProcessRequest{
    private OrderService orderService;
    private OrderLineItemService orderLineItemService;
    private ProductService productService;
    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitService unitService;
    private ProcessError error = new ProcessErrorController();
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        switch(process.toLowerCase()) {
            case "co"://cancel order
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                if (orderId > -1) {
                    if (dBManager != null) {
                        orderService = new OrderServiceImpl(dBManager);
                        orderLineItemService = new OrderLineItemServiceImpl(dBManager);
                        productService = new ProductServiceImpl(dBManager);
                        recipeService = new RecipeServiceImpl(dBManager);
                        ingredientService = new IngredientServiceImpl(dBManager);
                        unitService = new UnitServiceImpl(dBManager);
                        Order order = orderService.getOrderById(orderId);
                        order.setDeliveryStatus("cancelled");
                        orderService.updateOrder(order);
                        List<Ingredient> lstIngredients = ingredientService.getAllIngredient();
                        List<OrderLineItem> lstOrderLineItem = orderLineItemService.getItemsByOrderId(orderId);
                        for(OrderLineItem oli : lstOrderLineItem) {
                            Product product = productService.getProductById(oli.getProductId());
                            List<RecipeIngredient> lstRecipeIngredient = recipeService.getRecipeIngredients(product.getRecipeId());
                            updateStock(lstRecipeIngredient, lstIngredients, response);
                        }
                        view = "bakery?pro=gco";
                    }
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
    
    private boolean updateStock(List<RecipeIngredient> lstRecipeIngredient, List<Ingredient> lstIngredient, HttpServletResponse response) {
        boolean retval = false;
        if(!lstRecipeIngredient.isEmpty() && !lstIngredient.isEmpty()) {
            for(RecipeIngredient ri : lstRecipeIngredient) {
                for(Ingredient i : lstIngredient) {
                    if(i.getIngredientId() == ri.getIngredientId()) {
                        Unit riUnit = unitService.getUnitById(ri.getUnitId());
                        Unit iUnit = unitService.getUnitById(i.getUnitId());
                        if(riUnit != null && iUnit != null) {
                            switch(riUnit.getUnitName().toLowerCase()) {
                                case "grams":
                                case "mililiters":
                                    i.setQuantityInStock(i.getQuantityInStock()+ (ri.getQuantity()/1000));
                                    ingredientService.editIngredient(i);
                                    retval = true;
                                    break;
                                case "kilograms":
                                case "liters":
                                case "units":
                                    i.setQuantityInStock(i.getQuantityInStock() + ri.getQuantity());
                                    ingredientService.editIngredient(i);
                                    retval = true;
                                    break;
                                case "teaspoon":
                                    if(iUnit.getUnitName().equals("kilograms")){
                                        i.setQuantityInStock(i.getQuantityInStock() + ((ri.getQuantity()*4.2)/1000));
                                        ingredientService.editIngredient(i);
                                    }
                                    if(iUnit.getUnitName().equals("liters")){
                                        i.setQuantityInStock(i.getQuantityInStock() + ((ri.getQuantity()*5)/1000));
                                        ingredientService.editIngredient(i);
                                    }
                                    retval = true;
                                    break;
                                case "tablespoon":
                                    if(iUnit.getUnitName().equals("kilograms")){
                                        i.setQuantityInStock(i.getQuantityInStock() + ((ri.getQuantity()*21.25)/1000));
                                        ingredientService.editIngredient(i);
                                    }
                                    if(iUnit.getUnitName().equals("liters")){
                                        i.setQuantityInStock(i.getQuantityInStock() + ((ri.getQuantity()*15)/1000));
                                        ingredientService.editIngredient(i);
                                    }
                                    retval = true;
                                    break;
                                case "cup":
                                    if(iUnit.getUnitName().equals("kilograms")){
                                        i.setQuantityInStock(i.getQuantityInStock() + ((ri.getQuantity()*4.2)/1000));
                                        ingredientService.editIngredient(i);
                                    }
                                    if(iUnit.getUnitName().equals("liters")){
                                        i.setQuantityInStock(i.getQuantityInStock() + ((ri.getQuantity()*240)/1000));
                                        ingredientService.editIngredient(i);
                                    }
                                    retval = true;
                                    break;
                            }
                        }
                        else {
                            view = error.processError("0x00004", response);
                        }
                    }
                }
            }
        }
        else {
            view = error.processError("0x00004", response);
        }
        return retval;
    }
}
