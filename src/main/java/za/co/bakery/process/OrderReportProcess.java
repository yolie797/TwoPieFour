package za.co.bakery.process;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.documents.dao.CreateReportDao;
import za.co.bakery.documents.impl.CreateReportImpl;
import za.co.bakery.model.Order;
import za.co.bakery.service.OrderService;
import za.co.bakery.serviceImpl.OrderServiceImpl;

public class OrderReportProcess implements ProcessRequest {
    private OrderService orderService;
    private List<Order> lstOrder = new ArrayList<>();
    private CreateReportDao report = new CreateReportImpl();
    private String view;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {        
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        switch (process.toLowerCase()) {
            case "aor": //all order report
                if (dBManager != null) {
                    orderService = new OrderServiceImpl(dBManager);
                    lstOrder = orderService.getAllOrders();
                    report.createPlacedOrderReport(lstOrder, response);
                    request.setAttribute("orders", lstOrder);
                }
            case "oor": //outstanding order report
                if (dBManager != null) {
                    orderService = new OrderServiceImpl(dBManager);
                    lstOrder = orderService.getAllOrders();
                    report.createOutstandingOrderReport(lstOrder, response);
                    request.setAttribute("orders", lstOrder);
                }
                
            case "dor": //delivered order report
            if (dBManager != null) {
                orderService = new OrderServiceImpl(dBManager);
                lstOrder = orderService.getAllOrders();
                report.createDeliveredOrderReport(lstOrder, response);
                request.setAttribute("orders", lstOrder);
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
