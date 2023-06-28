package za.co.bakery.process;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.email.confirmation.EmailSettings;
import za.co.bakery.email.invoice.CreateInvoice;
import za.co.bakery.model.OrderLineItem;
import za.co.bakery.model.User;
import za.co.bakery.service.OrderLineItemService;

public class SendInvoiceEmailProcess implements ProcessRequest{
    private OrderLineItemService orderLineItemService;
    private List<OrderLineItem> lstOrderLineItem = new ArrayList<>();
    private CreateInvoice inv;
    private String filename = "invoice.pdf";
    private String dest;
    private ProcessError error = new ProcessErrorController();
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String imagesPath = (String) request.getServletContext().getAttribute("path");
        String process = request.getParameter("pro");
        HttpSession session = request.getSession();
        lstOrderLineItem = (List<OrderLineItem>)request.getAttribute("orderlineitems");
        switch (process.toLowerCase()){
            case "si"://send invoice
                User user = (User)session.getAttribute("theUser");
                if(user != null & lstOrderLineItem != null && imagesPath != null) {
                    if(dBManager != null) {
                        dest = imagesPath + "\\" + filename;
                        inv = new CreateInvoice(dest);
                        try {
                            EmailSettings es = new EmailSettings();
                            es.sendInvoiceEmail(user.getfName(), user.getEmail(), inv.WriteInvoice(user, lstOrderLineItem, dBManager));
                            Files.delete(Paths.get(dest));
                        } 
                        catch (MessagingException | IOException ex) {
                            System.out.println("could not send email" + ex.getMessage());
                        }
                        request.setAttribute("orderlineitems", lstOrderLineItem);
                        view = "customer/thankyou.jsp";
                    }
                    else {
                        view = error.processError("0x00001", response);
                    }
                }
                else {
                    view = error.processError("0x00007", response);
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
