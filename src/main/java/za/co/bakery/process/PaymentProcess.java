package za.co.bakery.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Order;
import za.co.bakery.model.OrderLineItem;
import za.co.bakery.service.OrderLineItemService;
import za.co.bakery.service.OrderService;
import za.co.bakery.service.PaymentService;
import za.co.bakery.serviceImpl.OrderLineItemServiceImpl;
import za.co.bakery.serviceImpl.OrderServiceImpl;
import za.co.bakery.serviceImpl.PaymentServiceImpl;

public class PaymentProcess implements ProcessRequest{
    private PaymentService paymentService;
    private OrderService orderService;
    private OrderLineItemService orderLineItemService;
    private List<OrderLineItem> lstOrderLineItem = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    private String[] payOpt = {"credit", "debit"};
    int newOrderId = -1;
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        lstOrderLineItem = (List<OrderLineItem>)request.getAttribute("orderlineitems");
        String orderIdent = request.getParameter("orderId");
        int orderID;
        if(orderIdent != null) {
            newOrderId = Integer.parseInt(request.getParameter("orderId"));
        }
        switch(process.toLowerCase()) {
            case "po"://pay order
                orderID = newOrderId;
                request.setAttribute("orderId", orderID);
                view = "customer/payment.jsp";
                break;
            case "p"://pay
                Random random = new Random();
                int index = random.nextInt(payOpt.length);
                String paymentOption = payOpt[index];
                if(paymentOption != null) {
                    paymentOption = paymentOption.toLowerCase().trim();
                    if(!paymentOption.isEmpty()) {
                        paymentService = new PaymentServiceImpl();
                        if(paymentService.generatePayment(paymentOption)) {
                            if(dBManager != null) {
                                orderLineItemService = new OrderLineItemServiceImpl(dBManager);
                                if(lstOrderLineItem != null) {
                                    for(OrderLineItem oli : lstOrderLineItem) {
                                        newOrderId = oli.getOrderId();
                                        break;
                                    }
                                }
                                else {
                                    lstOrderLineItem = orderLineItemService.getItemsByOrderId(newOrderId);
                                }
                                if(newOrderId > 0) {
                                    orderService = new OrderServiceImpl(dBManager);
                                    Order order = orderService.getOrderById(newOrderId);
                                    request.setAttribute("order", order);
                                    request.setAttribute("orderlineitems", lstOrderLineItem);
                                    view = "bakery?pro=uo";//update order
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
                            request.setAttribute("orderId", newOrderId);
                            view = "customer/paymentunsuccessful.jsp";
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
        } catch (ServletException | IOException ex) {
            System.out.println("Error Dispatching view: " + ex.getMessage());
        }
    }
    
}
