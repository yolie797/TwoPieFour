package za.co.bakery.process;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.serviceImpl.CartServiceImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.CartItem;
import za.co.bakery.model.Ingredient;
import za.co.bakery.model.Product;
import za.co.bakery.model.RecipeIngredient;
import za.co.bakery.model.Unit;
import za.co.bakery.service.ProductService;
import za.co.bakery.serviceImpl.ProductServiceImpl;
import za.co.bakery.service.CartService;
import za.co.bakery.service.IngredientService;
import za.co.bakery.service.RecipeService;
import za.co.bakery.service.UnitService;
import za.co.bakery.serviceImpl.IngredientServiceImpl;
import za.co.bakery.serviceImpl.RecipeServiceImpl;
import za.co.bakery.serviceImpl.UnitServiceImpl;

public class AddToCartProcess implements ProcessRequest{
    private Set<CartItem> setCart;
    private ProductService productService;
    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitService unitService;
    private CartService cartService;
    private Product product = null;
    private double total = 0.0;
    private double subTotal = 0.0;
    private ProcessError error = new ProcessErrorController();
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session=request.getSession();
        if(session.getAttribute("cart") != null){
            setCart = (Set<CartItem>) session.getAttribute("cart");
        }
        else{
            setCart = new HashSet<>();
            session.setAttribute("cart", setCart);
        }
        switch (process.toLowerCase()){
            case "sc":
                view = "customer/cart.jsp";
                break;
            case "atc":
                String productname = request.getParameter("productname");
                int quantity = 1; // Integer.parseInt(request.getParameter("quantity"));
                if(productname != null && quantity > 0 && setCart != null){
                    productname = productname.toLowerCase().trim();
                    if(!productname.isEmpty()) {
                        if(dBManager != null){
                            productService = new ProductServiceImpl(dBManager);
                            recipeService = new RecipeServiceImpl(dBManager);
                            ingredientService = new IngredientServiceImpl(dBManager);
                            unitService = new UnitServiceImpl(dBManager);
                            cartService = new CartServiceImpl();
                            product = productService.getProductByTitle(productname);
                            List<RecipeIngredient> lstRecipeIngredient = recipeService.getRecipeIngredients(product.getRecipeId());
                            List<Ingredient> lstIngredient = ingredientService.getAllIngredient();
                            if(stockAvailableCheck(lstRecipeIngredient, lstIngredient)) {
                                updateStock(lstRecipeIngredient, lstIngredient, response);
                                setCart.add(cartService.addItem(product, quantity, total, subTotal));
                                calculateTotal(setCart);
                                calculateSubTotal(setCart);
                                session.setAttribute("cart", setCart);
                                view = "bakery?pro=gp";
                            }
                            else {
                                view = error.processError("0x00011", response);
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
    
    private void calculateTotal(Set<CartItem> setCart) {
        if(!setCart.isEmpty()) {
            for(CartItem ci : setCart){
                Product p = ci.getProduct();
                total = ci.getQuantity()*p.getPrice();
                ci.setTotal(total);
            }
        }
    }
    
    private void calculateSubTotal(Set<CartItem> setCart) {
        if(!setCart.isEmpty()) {
            for(CartItem ci : setCart){
                Product p = ci.getProduct();
                subTotal += p.getPrice();
                ci.setSubTotal(subTotal);
            }
        }
    }
    
    private boolean stockAvailableCheck(List<RecipeIngredient> lstRecipeIngredient, List<Ingredient> lstIngredient) {
        boolean retVal = true;
        if(!lstRecipeIngredient.isEmpty() && !lstIngredient.isEmpty()) {
            for(RecipeIngredient ri : lstRecipeIngredient) {
                for(Ingredient i : lstIngredient) {
                    if(i.getIngredientId() == ri.getIngredientId()) {
                        Unit unit = unitService.getUnitById(ri.getUnitId());
                        Unit iUnit = unitService.getUnitById(i.getUnitId());
                        if(unit != null && iUnit != null) {
                            switch(unit.getUnitName().toLowerCase()) {
                                case "grams":
                                case "mililiters":
                                    if(i.getQuantityInStock() < (ri.getQuantity()/1000)) {
                                        retVal = false;
                                        return retVal;
                                    }
                                    break;
                                case "kilograms":
                                case "liters":
                                case "units":
                                    if(i.getQuantityInStock() < ri.getQuantity()) {
                                        retVal = false;
                                        return retVal;
                                    }
                                    break;
                                case "teaspoon":
                                    if(iUnit.getUnitName().equals("kilograms")) {
                                        if(i.getQuantityInStock() < ((ri.getQuantity()*4.2)/1000)) {
                                            retVal = false;
                                            return retVal;
                                        }
                                    }
                                    if(iUnit.getUnitName().equals("liters")) {
                                        if(i.getQuantityInStock() < ((ri.getQuantity()*5)/1000)) {
                                            retVal = false;
                                            return retVal;
                                        }
                                    }
                                    break;
                                case "tablespoon":
                                    if(iUnit.getUnitName().equals("kilograms")) {
                                        if(i.getQuantityInStock() < ((ri.getQuantity()*21.25)/1000)) {
                                            retVal = false;
                                            return retVal;
                                        }
                                    }
                                    if(iUnit.getUnitName().equals("liters")){
                                        if(i.getQuantityInStock() < ((ri.getQuantity()*15)/1000)) {
                                            retVal = false;
                                            return retVal;
                                        }
                                    }
                                    break;
                                case "cup":
                                    if(iUnit.getUnitName().equals("kilograms")) {
                                        if(i.getQuantityInStock() < ((ri.getQuantity()*4.2)/1000)) {
                                            retVal = false;
                                            return retVal;
                                        }
                                    }
                                    if(iUnit.getUnitName().equals("liters")) {
                                        if(i.getQuantityInStock() < ((ri.getQuantity()*240)/1000)) {
                                            retVal = false;
                                            return retVal;
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        }
        return retVal;
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
                                    i.setQuantityInStock(i.getQuantityInStock()- (ri.getQuantity()/1000));
                                    ingredientService.editIngredient(i);
                                    retval = true;
                                    break;
                                case "kilograms":
                                case "liters":
                                case "units":
                                    i.setQuantityInStock(i.getQuantityInStock()- ri.getQuantity());
                                    ingredientService.editIngredient(i);
                                    retval = true;
                                    break;
                                case "teaspoon":
                                    if(iUnit.getUnitName().equals("kilograms")){
                                        i.setQuantityInStock(i.getQuantityInStock() - ((ri.getQuantity()*4.2)/1000));
                                        ingredientService.editIngredient(i);
                                    }
                                    if(iUnit.getUnitName().equals("liters")){
                                        i.setQuantityInStock(i.getQuantityInStock() - ((ri.getQuantity()*5)/1000));
                                        ingredientService.editIngredient(i);
                                    }
                                    retval = true;
                                    break;
                                case "tablespoon":
                                    if(iUnit.getUnitName().equals("kilograms")){
                                        i.setQuantityInStock(i.getQuantityInStock() - ((ri.getQuantity()*21.25)/1000));
                                        ingredientService.editIngredient(i);
                                    }
                                    if(iUnit.getUnitName().equals("liters")){
                                        i.setQuantityInStock(i.getQuantityInStock() - ((ri.getQuantity()*15)/1000));
                                        ingredientService.editIngredient(i);
                                    }
                                    retval = true;
                                    break;
                                case "cup":
                                    if(iUnit.getUnitName().equals("kilograms")){
                                        i.setQuantityInStock(i.getQuantityInStock() - ((ri.getQuantity()*4.2)/1000));
                                        ingredientService.editIngredient(i);
                                    }
                                    if(iUnit.getUnitName().equals("liters")){
                                        i.setQuantityInStock(i.getQuantityInStock() - ((ri.getQuantity()*240)/1000));
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
