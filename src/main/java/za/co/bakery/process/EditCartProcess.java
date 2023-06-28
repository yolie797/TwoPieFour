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
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.CartItem;
import za.co.bakery.model.Ingredient;
import za.co.bakery.model.Product;
import za.co.bakery.model.RecipeIngredient;
import za.co.bakery.model.Unit;
import za.co.bakery.service.CartService;
import za.co.bakery.service.IngredientService;
import za.co.bakery.service.ProductService;
import za.co.bakery.service.RecipeService;
import za.co.bakery.service.UnitService;
import za.co.bakery.serviceImpl.IngredientServiceImpl;
import za.co.bakery.serviceImpl.ProductServiceImpl;
import za.co.bakery.serviceImpl.RecipeServiceImpl;
import za.co.bakery.serviceImpl.UnitServiceImpl;

public class EditCartProcess implements ProcessRequest{
    private Set<CartItem> setCart;
    private ProductService productService;
    private CartService cartService;
    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitService unitService;
    private Product product = null;
    private CartItem cartItem = null;
    private double total = 0;
    private double subTotal = 0;
    private ProcessError error = new ProcessErrorController();
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session=request.getSession();
        String productName;
        if(session.getAttribute("cart") != null){
            setCart = (Set<CartItem>) session.getAttribute("cart");
        }
        else{
            setCart = new HashSet<>();
            session.setAttribute("cart", setCart);
        }
        switch (process.toLowerCase()){
            case "ri"://remove item
                productName = request.getParameter("productname");
                if(productName != null && setCart != null) {
                    productName = productName.trim();
                    if(!productName.isEmpty()) {
                        for(CartItem ci : setCart) {
                            product = ci.getProduct();
                            if(product.getTitle().equals(productName)) {
                                cartItem = ci;
                                break;
                            }
                        }
                        if(cartItem != null) {
                            setCart.remove(cartItem);
                            if(dBManager != null) {
                                productService = new ProductServiceImpl(dBManager);
                                ingredientService = new IngredientServiceImpl(dBManager);
                                recipeService = new RecipeServiceImpl(dBManager);
                                unitService = new UnitServiceImpl(dBManager);
                                List<RecipeIngredient> lstRecipeIngredient = recipeService.getRecipeIngredients(product.getRecipeId());
                                List<Ingredient> lstIngredient = ingredientService.getAllIngredient();
                                updateStockInc(lstRecipeIngredient, lstIngredient, response);
                                if(product != null) {
                                    product.setOutOfStock(false);
                                    productService.editProduct(product);
                                }
                                view = "customer/cart.jsp";
                            }
                            else {
                                view = error.processError("0x00001", response);
                            }
                            view = "customer/cart.jsp";
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
            case "cc"://clear cart
                    setCart.removeAll(setCart);
                break;
            case "dec"://decremenet quantity
                productName = request.getParameter("productname");
                if(productName != null && setCart != null) {
                    productName = productName.trim();
                    if(!productName.isEmpty()) {
                        for(CartItem ci : setCart) {
                            product = ci.getProduct();
                            if(product.getTitle().equals(productName)) {
                                if(ci.getQuantity() > 1) {
                                    if(dBManager != null) {
                                        productService = new ProductServiceImpl(dBManager);
                                        ingredientService = new IngredientServiceImpl(dBManager);
                                        recipeService = new RecipeServiceImpl(dBManager);
                                        unitService = new UnitServiceImpl(dBManager);
                                        List<RecipeIngredient> lstRecipeIngredient = recipeService.getRecipeIngredients(product.getRecipeId());
                                        List<Ingredient> lstIngredient = ingredientService.getAllIngredient();
                                        ci.setQuantity(ci.getQuantity() - 1);
                                        calculateTotal(setCart);
                                        updateStockInc(lstRecipeIngredient, lstIngredient, response);
                                        if(product != null) {
                                            product.setOutOfStock(false);
                                            productService.editProduct(product);
                                        }
                                        view = "customer/cart.jsp";
                                        break;
                                    }
                                    else {
                                        view = error.processError("0x00001", response);
                                    }
                                }
                                view = "customer/cart.jsp";
                            }
                            else {
                                view = error.processError("0x00004", response);
                            }
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
            case "inc"://increment quantity
                productName = request.getParameter("productname");
                if(productName != null && setCart != null) {
                    productName = productName.trim();
                    if(!productName.isEmpty()) {
                        for(CartItem ci : setCart) {
                            product = ci.getProduct();
                            if(product.getTitle().equals(productName)) {
                                if(dBManager != null) {
                                    productService = new ProductServiceImpl(dBManager);
                                    ingredientService = new IngredientServiceImpl(dBManager);
                                    recipeService = new RecipeServiceImpl(dBManager);
                                    unitService = new UnitServiceImpl(dBManager);
                                    List<RecipeIngredient> lstRecipeIngredient = recipeService.getRecipeIngredients(product.getRecipeId());
                                    List<Ingredient> lstIngredient = ingredientService.getAllIngredient();
                                    if(stockAvailableCheck(lstRecipeIngredient, lstIngredient)) {
                                        ci.setQuantity(ci.getQuantity() + 1);
                                        calculateTotal(setCart);
                                        updateStockDec(lstRecipeIngredient, lstIngredient, response);
                                        view = "customer/cart.jsp";
                                    }
                                    else {
                                        if(product != null) {
                                            product.setOutOfStock(true);
                                            productService.editProduct(product);
                                        }
                                        view = error.processError("0x00016", response);
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
                    }
                    else {
                        view = error.processError("0x00004", response);
                    }
                }
                else {
                    view = error.processError("0x00004", response);
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
                product = ci.getProduct();
                total = ci.getQuantity()*product.getPrice();
                ci.setTotal(total);
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
    
    private boolean updateStockDec(List<RecipeIngredient> lstRecipeIngredient, List<Ingredient> lstIngredient, HttpServletResponse response) {
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
    
    private boolean updateStockInc(List<RecipeIngredient> lstRecipeIngredient, List<Ingredient> lstIngredient, HttpServletResponse response) {
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
                                    i.setQuantityInStock(i.getQuantityInStock() + (ri.getQuantity()/1000));
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
