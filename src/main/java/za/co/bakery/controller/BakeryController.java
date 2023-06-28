package za.co.bakery.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.process.AddAddressProcess;
import za.co.bakery.process.AddCategoryProcess;
import za.co.bakery.process.AddIngredientProcess;
import za.co.bakery.process.AddInvoiceProcess;
import za.co.bakery.process.AddOrderLineItemProcess;
import za.co.bakery.process.AddOrderProcess;
import za.co.bakery.process.PaymentProcess;
import za.co.bakery.process.AddProductProcess;
import za.co.bakery.process.AddRecipeProcess;
import za.co.bakery.process.AddToCartProcess;
import za.co.bakery.process.CustomerReportProcess;
import za.co.bakery.process.EditAddressProcess;
import za.co.bakery.process.EditCartProcess;
import za.co.bakery.process.EditCategoryProcess;
import za.co.bakery.process.EditNutritionProcess;
import za.co.bakery.process.EditProductProcess;
import za.co.bakery.process.EditRecipeProcess;
import za.co.bakery.process.EditUserProcess;
import za.co.bakery.process.GetCategoryProcess;
import za.co.bakery.process.GetIngredientProcess;
import za.co.bakery.process.GetOrderLineItemsProcess;
import za.co.bakery.process.GetOrderProcess;
import za.co.bakery.process.GetProductProcess;
import za.co.bakery.process.GetRecipeProcess;
import za.co.bakery.process.GetUserProcess;
import za.co.bakery.process.IngredientsReportProcess;
import za.co.bakery.process.LoginProcess;
import za.co.bakery.process.OrderReportProcess;
import za.co.bakery.process.ProcessRequest;
import za.co.bakery.process.RegistrationProcess;
import za.co.bakery.process.SendInvoiceEmailProcess;

@MultipartConfig
@WebServlet(name = "BakeryController", urlPatterns = {"/bakery"})
public class BakeryController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String process = request.getParameter("pro");
        if (process != null) {
            ProcessRequest pr = RequestFactory.createProcess(process, request);
            if (pr != null) {
                pr.processRequest(request, response);
                pr.processResponse(request, response);
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}

abstract class RequestFactory {

    public static ProcessRequest createProcess(String process, HttpServletRequest request) {
        switch (process.toLowerCase()) {
            case "sa": //select Address
            case "aas": //select Address
            case "das"://delivery Address save
                return new AddAddressProcess();
            case "acs": //add category 
                return new AddCategoryProcess();
            case "ao":  //Add Order
            case "uo"://update order
                return new AddOrderProcess();
            case "gco"://get customer order
                return new GetOrderProcess();
            case "aoli"://add order line items 
                return new AddOrderLineItemProcess();
            case "ar": //add recipe
            case "arsi"://add recipe save ingredient  
            case "ars"://add recipe save
                return new AddRecipeProcess();
            case "atc": //add to cart 
            case "sc":
                return new AddToCartProcess();
            case "ea": //address edit
            case "eas": //address edit save
                return new EditAddressProcess();
            case "ec": // edit category
            case "ecs": //edit category save
                return new EditCategoryProcess();
            case "en": //edit nutrition
            case "ens": //edit nutrition save
                return new EditNutritionProcess();
            case "gr"://get all recipe
                return new GetRecipeProcess();
            case "er": //edit recipe
            case "ers": //edit recipe save
                return new EditRecipeProcess();
            case "p":  //pay
            case "po"://pay order
                return new PaymentProcess();
            case "li":  //Login
            case "lo":
                return new LoginProcess();
            case "gau": //get all user
            case "gur": //get user by username
                return new GetUserProcess();
            case "eu"://edit user
                return new EditUserProcess();
            case "rp":  //registrartion process
            case "rcp":  //registrartion process
                return new RegistrationProcess();
            case "gp":  //get all products
            case "gpc":  //get products by category
            case "gps":  //get products by category
                return new GetProductProcess();
            case "aps": //add product save
            case "ap":  //add product
                return new AddProductProcess();
            case "ep":   //edit product
            case "eps":  //edit product save
                return new EditProductProcess();
            case "gi":  //get ingredient
            case "gpi": //get product ingredient
                return new GetIngredientProcess();
            case "ai":   //add ingredient
            case "ais": //add ingredient save
                return new AddIngredientProcess();
            case "ri"://remove item
            case "cc"://clear cart
            case "dec"://decremenet quantity
            case "inc"://increment quantity
                return new EditCartProcess();
            case "gc":  //get categories
                return new GetCategoryProcess();
            case "ci"://create invoice
                return new AddInvoiceProcess();
            case "si"://send invoice
                return new SendInvoiceEmailProcess();
            case "gao"://get all order
                return new GetOrderProcess();
            case "gooli"://get order, order line items
                return new GetOrderLineItemsProcess();
            case "cr": // customer report
                return new CustomerReportProcess();
            case "aor": //all order report
            case "oor": //outstanding order report
            case "dor": //delivered order report
                return new OrderReportProcess();
            case "iis": //ingredients in stock
            case "ios": //ingredients out stock
                return new IngredientsReportProcess();

        }
        return null;
    }
}
