package za.co.bakery.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.documents.dao.CreateReportDao;
import za.co.bakery.documents.impl.CreateReportImpl;
import za.co.bakery.model.Order;
import za.co.bakery.model.User;
import za.co.bakery.service.OrderService;
import za.co.bakery.service.UserService;
import za.co.bakery.serviceImpl.OrderServiceImpl;

public class CustomerReportProcess implements ProcessRequest {
    private OrderService orderService;
    private List<Order> lstOrder = new ArrayList<>();
    private CreateReportDao report = new CreateReportImpl();
    private String view;
  
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        switch (process.toLowerCase()) {
             case "cr": // customer report
                   if (dBManager != null) {
                       orderService = new OrderServiceImpl(dBManager);
                       lstOrder = orderService.getAllOrders();
                        report.createCustomerListReport(lstOrder, response, dBManager);
                   }
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
