package za.co.bakery.process;

import java.io.IOException;
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
import za.co.bakery.model.CartItem;
import za.co.bakery.model.Invoice;
import za.co.bakery.service.InvoiceService;
import za.co.bakery.serviceImpl.InvoiceServiceImpl;

public class AddInvoiceProcess implements ProcessRequest{
    private InvoiceService invoiceService;
    private Set<CartItem> setCart;
    private double totalPayment = 0;
    private int identifier = 100;
    private List<Integer> lstIdentifier = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session = request.getSession();
        switch(process.toLowerCase()) {
            case "ci"://create invoice
                setCart = (Set<CartItem>)session.getAttribute("cart");
                if(setCart != null) {
                    if (!setCart.isEmpty()) {
                        for (CartItem ci : setCart) {
                            totalPayment += ci.getTotal();
                        }
                        if (dBManager != null) {
                            invoiceService = new InvoiceServiceImpl(dBManager);
                            lstIdentifier = invoiceService.getInvoiceIdentifier();
                            if (lstIdentifier != null) {
                                if (!lstIdentifier.isEmpty()) {
                                    for (int id : lstIdentifier) {
                                        if (id > identifier) {
                                            identifier = id;
                                        }
                                    }
                                    identifier += 1;
                                }
                            }
                            Invoice invoice = new Invoice(identifier, totalPayment, totalPayment);
                            invoiceService.createInvoice(invoice);
                            invoice = invoiceService.getInvoiceWithIdentifier(identifier);
                            request.setAttribute("invoice", invoice);
                            view = "bakery?pro=ao";
                        }
                        else {
                            view = error.processError("0x00004", response);
                        }
                    }
                    else {
                        view = error.processError("0x00004", response);
                    }
                }
                else {
                    view = error.processError("0x00002", response);
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
