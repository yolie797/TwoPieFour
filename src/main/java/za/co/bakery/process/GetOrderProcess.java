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
import za.co.bakery.model.Address;
import za.co.bakery.model.Order;
import za.co.bakery.model.OrderLineItem;
import za.co.bakery.model.Product;
import za.co.bakery.model.User;
import za.co.bakery.service.AddressService;
import za.co.bakery.service.OrderLineItemService;
import za.co.bakery.service.OrderService;
import za.co.bakery.service.ProductService;
import za.co.bakery.serviceImpl.AddressServiceImpl;
import za.co.bakery.serviceImpl.OrderLineItemServiceImpl;
import za.co.bakery.serviceImpl.OrderServiceImpl;
import za.co.bakery.serviceImpl.ProductServiceImpl;

public class GetOrderProcess implements ProcessRequest {

    private OrderService orderService;
    private AddressService addressService;
    private ProductService productService;
    private OrderLineItemService orderLineItemService;
    private List<Order> lstOrder = new ArrayList<>();
    private List<Product> lstProduct = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    private String view;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session = request.getSession();
        User user = null;
        switch (process) {
            case "gco"://get customer order
                user = (User) session.getAttribute("theUser");
                if (user != null) {
                    if (dBManager != null) {
                        orderService = new OrderServiceImpl(dBManager);
                        addressService = new AddressServiceImpl(dBManager);
                        lstOrder = orderService.getCustomerOrders(user.getEmail());
                        request.setAttribute("orders", lstOrder);
                        view = "customer/order.jsp";
                    }
                } else {
                    view = error.processError("0x00004", response);
                }
                break;
            case "gao"://get all order
                if (dBManager != null) {
                    orderService = new OrderServiceImpl(dBManager);
                    addressService = new AddressServiceImpl(dBManager);
                    productService = new ProductServiceImpl(dBManager);
                    orderLineItemService = new OrderLineItemServiceImpl(dBManager);
                    lstOrder = orderService.getAllOrders();
                    lstProduct = productService.getAllProducts();
                    request.setAttribute("orders", lstOrder);
                    request.setAttribute("products", lstProduct);
                    request.setAttribute("dbmanager", dBManager);
                    view = "admin/order.jsp";
                } else {
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
        } catch (ServletException | IOException ex) {
            System.out.println("Error Dispatching " + view + ": " + ex.getMessage());
        }
    }

}
