package za.co.bakery.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.CartItem;
import za.co.bakery.model.Order;
import za.co.bakery.model.OrderLineItem;
import za.co.bakery.model.Product;
import za.co.bakery.service.OrderLineItemService;
import za.co.bakery.serviceImpl.OrderLineItemServiceImpl;

public class AddOrderLineItemProcess implements ProcessRequest{
    private OrderLineItemService orderLineItemService;
    private Set<CartItem> setCart = new TreeSet<>();
    private List<OrderLineItem> lstOrderLineItem = new ArrayList<>();
    private OrderLineItem orderLineItem = null;
    private Product product = null;
    private ProcessError error = new ProcessErrorController();
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session = request.getSession();
        switch (process.toLowerCase()){
            case "aoli"://add order line items 
                setCart = (Set<CartItem>)session.getAttribute("cart");
                Order order = (Order)request.getAttribute("order");
                if(!setCart.isEmpty() && order != null){
                    if(dBManager != null){
                        orderLineItemService = new OrderLineItemServiceImpl(dBManager);
                        orderLineItem = new OrderLineItem();
                        for(CartItem ci : setCart){
                            product = ci.getProduct();
                            orderLineItem.setOrderId(order.getOrderId());
                            orderLineItem.setInvoiceId(order.getInvoiceId());
                            orderLineItem.setProductId(product.getProductId());
                            orderLineItem.setQuantity(ci.getQuantity());
                            orderLineItem.setTotalPrice(ci.getQuantity() * product.getPrice());
                            orderLineItemService.addOrderItem(orderLineItem);
                        }
                        lstOrderLineItem = orderLineItemService.getItemsByOrderId(order.getOrderId());
                        request.setAttribute("orderlineitems", lstOrderLineItem);
                        view = "bakery?pro=si";//sendInvoice
                    }
                    else {
                        view = error.processError("0x00001", response);
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
    
}
