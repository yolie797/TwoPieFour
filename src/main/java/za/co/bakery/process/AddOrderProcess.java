package za.co.bakery.process;

import java.io.IOException;
import java.time.LocalDate;
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
import za.co.bakery.model.Address;
import za.co.bakery.model.CartItem;
import za.co.bakery.model.Ingredient;
import za.co.bakery.model.Invoice;
import za.co.bakery.model.Order;
import za.co.bakery.model.OrderLineItem;
import za.co.bakery.model.Product;
import za.co.bakery.model.RecipeIngredient;
import za.co.bakery.model.Unit;
import za.co.bakery.model.User;
import za.co.bakery.service.IngredientService;
import za.co.bakery.service.InvoiceService;
import za.co.bakery.service.OrderService;
import za.co.bakery.service.ProductService;
import za.co.bakery.service.RecipeService;
import za.co.bakery.service.UnitService;
import za.co.bakery.serviceImpl.IngredientServiceImpl;
import za.co.bakery.serviceImpl.InvoiceServiceImpl;
import za.co.bakery.serviceImpl.OrderServiceImpl;
import za.co.bakery.serviceImpl.ProductServiceImpl;
import za.co.bakery.serviceImpl.RecipeServiceImpl;
import za.co.bakery.serviceImpl.UnitServiceImpl;

public class AddOrderProcess implements ProcessRequest{
    private OrderService orderService;
    private IngredientService ingredientService;
    private ProductService productService;
    private RecipeService recipeService;
    private InvoiceService invoiceService;
    private UnitService unitService;
    private List<OrderLineItem> lstOrderLineItem = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    private String view;
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("theUser");
        Address delAddress = (Address)session.getAttribute("deliveryaddress");
        Invoice invoice = (Invoice)request.getAttribute("invoice");
        Set<CartItem> setCart = (Set<CartItem>)session.getAttribute("cart");
        switch(process.toLowerCase()) {
            case "ao"://add order
                if(user != null){
                    if(delAddress != null && invoice != null && setCart != null) {
                        if(dBManager != null) {
                            orderService = new OrderServiceImpl(dBManager);
                            ingredientService = new  IngredientServiceImpl(dBManager);
                            recipeService = new RecipeServiceImpl(dBManager);
                            invoiceService = new InvoiceServiceImpl(dBManager);
                            unitService = new UnitServiceImpl(dBManager);
                            Order order = new Order(user.getEmail(), delAddress.getAddressId(), invoice.getInvoiceId(), LocalDate.now(), "pending", "not paid");
                            if(orderService.addOrder(order)) {
                                order = orderService.getOrderByInvoiceId(invoice.getInvoiceId());
                                invoice.setOrderId(order.getOrderId());
                                invoiceService.updateInvoice(invoice);
                                request.setAttribute("order", order);
                                view = "bakery?pro=aoli";//add order line item
                            }
                            else {
                                view = error.processError("0x00009", response);
                            }
                        }
                        else {
                            view = error.processError("0x00001", response);
                        }
                    }
                    else {
                        view = error.processError("0x00002", response);
                    }
                }
                else {
                    view = error.processError("0x00002", response);
                }
                break;
            case "uo"://update order
                Order order = (Order)request.getAttribute("order");
                lstOrderLineItem = (List<OrderLineItem>)request.getAttribute("orderlineitems");
                if(order != null) {
                    if(dBManager != null) {
                        orderService = new OrderServiceImpl(dBManager);
                        ingredientService = new  IngredientServiceImpl(dBManager);
                        recipeService = new RecipeServiceImpl(dBManager);
                        productService = new ProductServiceImpl(dBManager);
                        invoiceService = new InvoiceServiceImpl(dBManager);
                        unitService = new UnitServiceImpl(dBManager);
                        List<Ingredient> lstIngredients = ingredientService.getAllIngredient();
                        invoice = invoiceService.getOrderInvoice(order.getOrderId());
                        order.setPaymentStatus("paid");
                        order.setDeliveryStatus("in progress");
                        invoice.setAmountDue(0.0);
                        invoiceService.updateInvoice(invoice);
                        if(orderService.updateOrder(order)) {
//                            for(OrderLineItem orderLineItem : lstOrderLineItem) {
//                                Product p = productService.getProductById(orderLineItem.getProductId());
//                                List<RecipeIngredient> lstRecipeIngredients = recipeService.getRecipeIngredients(p.getRecipeId());
//                                updateStock(lstRecipeIngredients, lstIngredients, response);
//                            }
                            view = "customer/paymentsuccess.jsp";
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
                    view = error.processError("0x00009", response);
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
