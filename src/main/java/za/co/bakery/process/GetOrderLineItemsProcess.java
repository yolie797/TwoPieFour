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
import za.co.bakery.model.Invoice;
import za.co.bakery.model.Nutrition;
import za.co.bakery.model.Order;
import za.co.bakery.model.OrderLineItem;
import za.co.bakery.model.Product;
import za.co.bakery.model.Recipe;
import za.co.bakery.service.NutritionService;
import za.co.bakery.service.OrderLineItemService;
import za.co.bakery.service.OrderService;
import za.co.bakery.service.ProductService;
import za.co.bakery.service.RecipeService;
import za.co.bakery.service.InvoiceService;
import za.co.bakery.serviceImpl.InvoiceServiceImpl;
import za.co.bakery.serviceImpl.NutritionServiceImpl;
import za.co.bakery.serviceImpl.OrderLineItemServiceImpl;
import za.co.bakery.serviceImpl.OrderServiceImpl;
import za.co.bakery.serviceImpl.ProductServiceImpl;
import za.co.bakery.serviceImpl.RecipeServiceImpl;

public class GetOrderLineItemsProcess implements ProcessRequest{
    private OrderLineItemService orderLineItemService;
    private ProductService productService;
    private RecipeService recipeService;
    private NutritionService nutritionService;
    private OrderService orderService;
    private InvoiceService invoiceService;
    private List<OrderLineItem> lstOrderLineItem = new ArrayList<>();
    private List<Recipe> lstRecipe = new ArrayList<>();
    private List<Nutrition> lstNutrition = new ArrayList<>();
    private List<Product> lstProduct = new ArrayList<>();
    private Order order = null;
    private Invoice invoice = null;
    private ProcessError error = new ProcessErrorController();
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        switch(process){
            case "gioli"://get invoice, order line items
                int invoiceId = Integer.parseInt(request.getParameter("invoiceid"));
                if(invoiceId > 0) {
                   orderLineItemService = new OrderLineItemServiceImpl(dBManager);
                   lstOrderLineItem = orderLineItemService.getItemsByInvoiceId(invoiceId);
                   request.setAttribute("orderlineitems", lstOrderLineItem);
                   view = "";
                }
                else {
                    view = error.processError("0x00004", response);
                }
                break;
            case "gooli"://get order, order line items
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                if(orderId > 0) {
                    if(dBManager != null) {
                        orderLineItemService = new OrderLineItemServiceImpl(dBManager); 
                        productService = new ProductServiceImpl(dBManager);
                        recipeService = new RecipeServiceImpl(dBManager);
                        nutritionService = new NutritionServiceImpl(dBManager);
                        orderService = new OrderServiceImpl(dBManager);
                        invoiceService = new InvoiceServiceImpl(dBManager);
                        lstOrderLineItem = orderLineItemService.getItemsByOrderId(orderId);
                        lstProduct = productService.getAllProducts();
                        lstRecipe = recipeService.getAllRecipe();
                        lstNutrition = nutritionService.getAllNutrition();
                        order = orderService.getOrderById(orderId);
                        invoice = invoiceService.getOrderInvoice(orderId);
                        request.setAttribute("orderlineitems", lstOrderLineItem);
                        request.setAttribute("products", lstProduct);
                        request.setAttribute("recipes", lstRecipe);
                        request.setAttribute("nutritions", lstNutrition);
                        request.setAttribute("order", order);
                        request.setAttribute("invoice", invoice);
                        view = "customer/orderdetails.jsp";
                    }
                }
                else {
                    view = error.processError("0x00004", response);
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
