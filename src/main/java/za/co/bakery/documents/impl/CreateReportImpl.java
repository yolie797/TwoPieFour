package za.co.bakery.documents.impl;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.documents.dao.CreateReportDao;
import za.co.bakery.model.Ingredient;
import za.co.bakery.model.Order;
import za.co.bakery.model.Unit;
import za.co.bakery.model.User;
import za.co.bakery.process.OrderReportProcess;
import za.co.bakery.service.UserService;
import za.co.bakery.serviceImpl.UserServiceImpl;

public class CreateReportImpl implements CreateReportDao {

    @Override
    public boolean createPlacedOrderReport(List<Order> lstOrder, HttpServletResponse response) {
        boolean retval = false;
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Orders Placed.pdf");
        
        // Create the PDF document using iText
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(OrderReportProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(OrderReportProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        document.open();
        document.addTitle("Orders Placed Report");

        // Create the table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add table headers
        PdfPCell cell;
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD);

        cell = new PdfPCell(new Phrase("Customer Email", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("date", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Order Current Status", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Payment Current Status", headerFont));
        table.addCell(cell);

        // Add ingredients to the table
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        for (Order order : lstOrder) {
            cell = new PdfPCell(new Phrase(order.getEmail(), cellFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(order.getDate().toString(), cellFont));
            table.addCell(cell);
            if (order.getDeliveryStatus().equals("pending")) {
                cell = new PdfPCell(new Phrase(order.getDeliveryStatus(), cellFont));
            }
            else if (order.getDeliveryStatus().equals("in progress")) {
                cell = new PdfPCell(new Phrase(order.getDeliveryStatus(), cellFont));
            }
            else if (order.getDeliveryStatus().equals("delivered")) {
                cell = new PdfPCell(new Phrase(order.getDeliveryStatus(), cellFont));
            }
            table.addCell(cell);
            
            if (order.getPaymentStatus().equals("not paid")) {
                cell = new PdfPCell(new Phrase(order.getPaymentStatus(), cellFont));
            } 
            else if (order.getPaymentStatus().equals("paid")) {
                cell = new PdfPCell(new Phrase(order.getPaymentStatus(), cellFont));
            } 
            table.addCell(cell);
        }

        try {
            // Add the table to the document
            document.add(table);
            retval = true;
        } catch (DocumentException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        document.close();
        
        return retval;
    }

    @Override
    public boolean createOutstandingOrderReport(List<Order> lstOrder, HttpServletResponse response) {
        boolean retval = false;
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Orders Placed.pdf");
        
        lstOrder = lstOrder.stream().filter(order -> !(order.getDeliveryStatus().equals("delivered"))).collect(Collectors.toList());
        
        // Create the PDF document using iText
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(OrderReportProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(OrderReportProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        document.open();
        document.addTitle("Orders Outstanding Report");

        // Create the table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add table headers
        PdfPCell cell;
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD);

        cell = new PdfPCell(new Phrase("Customer Email", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("date", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Order Current Status", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Payment Current Status", headerFont));
        table.addCell(cell);

        // Add ingredients to the table
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        for (Order order : lstOrder) {
            cell = new PdfPCell(new Phrase(order.getEmail(), cellFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(order.getDate().toString(), cellFont));
            table.addCell(cell);
            if (order.getDeliveryStatus().equals("pending")) {
                cell = new PdfPCell(new Phrase(order.getDeliveryStatus(), cellFont));
            }
            else if (order.getDeliveryStatus().equals("in progress")) {
                cell = new PdfPCell(new Phrase(order.getDeliveryStatus(), cellFont));
            }
            else if (order.getDeliveryStatus().equals("delivered")) {
                cell = new PdfPCell(new Phrase(order.getDeliveryStatus(), cellFont));
            }
            table.addCell(cell);
            
            if (order.getPaymentStatus().equals("not paid")) {
                cell = new PdfPCell(new Phrase(order.getPaymentStatus(), cellFont));
            } 
            else if (order.getPaymentStatus().equals("paid")) {
                cell = new PdfPCell(new Phrase(order.getPaymentStatus(), cellFont));
            } 
            table.addCell(cell);
        }

        try {
            // Add the table to the document
            document.add(table);
            retval = true;
        } catch (DocumentException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        document.close();
        
        return retval;
    }

    @Override
    public boolean createDeliveredOrderReport(List<Order> lstOrder, HttpServletResponse response) {
        boolean retval = false;
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Orders Placed.pdf");
        
         lstOrder = lstOrder.stream().filter(order -> (order.getDeliveryStatus().equals("delivered"))).collect(Collectors.toList());
        
        // Create the PDF document using iText
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(OrderReportProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(OrderReportProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        document.open();
        document.addTitle("Orders Delivered Report");

        // Create the table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add table headers
        PdfPCell cell;
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD);

        cell = new PdfPCell(new Phrase("Customer Email", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("date", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Order Current Status", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Payment Current Status", headerFont));
        table.addCell(cell);

        // Add ingredients to the table
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        for (Order order : lstOrder) {
            cell = new PdfPCell(new Phrase(order.getEmail(), cellFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(order.getDate().toString(), cellFont));
            table.addCell(cell);
            if (order.getDeliveryStatus().equals("pending")) {
                cell = new PdfPCell(new Phrase(order.getDeliveryStatus(), cellFont));
            }
            else if (order.getDeliveryStatus().equals("in progress")) {
                cell = new PdfPCell(new Phrase(order.getDeliveryStatus(), cellFont));
            }
            else if (order.getDeliveryStatus().equals("delivered")) {
                cell = new PdfPCell(new Phrase(order.getDeliveryStatus(), cellFont));
            }
            table.addCell(cell);
            
            if (order.getPaymentStatus().equals("not paid")) {
                cell = new PdfPCell(new Phrase(order.getPaymentStatus(), cellFont));
            } 
            else if (order.getPaymentStatus().equals("paid")) {
                cell = new PdfPCell(new Phrase(order.getPaymentStatus(), cellFont));
            } 
            table.addCell(cell);
        }

        try {
            // Add the table to the document
            document.add(table);
            retval = true;
        } catch (DocumentException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        document.close();
        
        return retval;
    }

    @Override
    public boolean createIngredientsInStockReport(List<Ingredient> lstIngredients, List<Unit> lstUnit, HttpServletResponse response) {
         boolean retval = false;
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Orders Placed.pdf");
        
         lstIngredients = lstIngredients.stream().filter(ing -> (ing.getMinStockQuantity() < ing.getQuantityInStock())).collect(Collectors.toList());
        
        // Create the PDF document using iText
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(OrderReportProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(OrderReportProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        document.open();
        document.addTitle("Orders Delivered Report");

        // Create the table
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add table headers
        PdfPCell cell;
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD);

        cell = new PdfPCell(new Phrase("Ingredient name", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Quantity in stock", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Minimun Quantity", headerFont));
        table.addCell(cell);

        // Add ingredients to the table
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        for (Ingredient ingredient : lstIngredients) {
            cell = new PdfPCell(new Phrase(ingredient.getName(), cellFont));
            table.addCell(cell);
            for(Unit unit : lstUnit) {
                if(unit.getUnitId() == ingredient.getUnitId()){
                    cell = new PdfPCell(new Phrase(String.valueOf(ingredient.getQuantityInStock()) 
                            + " " + unit.getUnitName(), cellFont));
                }
            }
            table.addCell(cell);
            for(Unit unit : lstUnit) {
                if(unit.getUnitId() == ingredient.getUnitId()){
                    cell = new PdfPCell(new Phrase(String.valueOf(ingredient.getMinStockQuantity()) 
                            + " " + unit.getUnitName(), cellFont));
                }
            }
            table.addCell(cell);
        }

        try {
            // Add the table to the document
            document.add(table);
            retval = true;
        } catch (DocumentException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        document.close();
        
        return retval;
    }

    @Override
    public boolean createIngredientsOutOfStockReport(List<Ingredient> lstIngredients, List<Unit> lstUnit, HttpServletResponse response) {
        boolean retval = false;
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Orders Placed.pdf");
        
         lstIngredients = lstIngredients.stream().filter(ing -> (ing.getMinStockQuantity() > ing.getQuantityInStock())).collect(Collectors.toList());
        
        // Create the PDF document using iText
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(OrderReportProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(OrderReportProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        document.open();
        document.addTitle("Orders Delivered Report");

        // Create the table
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add table headers
        PdfPCell cell;
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD);

        cell = new PdfPCell(new Phrase("Ingredient name", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Quantity in stock", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Minimun Quantity", headerFont));
        table.addCell(cell);

        // Add ingredients to the table
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        for (Ingredient ingredient : lstIngredients) {
            cell = new PdfPCell(new Phrase(ingredient.getName(), cellFont));
            table.addCell(cell);
            for(Unit unit : lstUnit) {
                if(unit.getUnitId() == ingredient.getUnitId()){
                    cell = new PdfPCell(new Phrase(String.valueOf(ingredient.getQuantityInStock()) 
                            + " " + unit.getUnitName(), cellFont));
                }
            }
            table.addCell(cell);
            for(Unit unit : lstUnit) {
                if(unit.getUnitId() == ingredient.getUnitId()){
                    cell = new PdfPCell(new Phrase(String.valueOf(ingredient.getMinStockQuantity()) 
                            + " " + unit.getUnitName(), cellFont));
                }
            }
            table.addCell(cell);
        }

        try {
            // Add the table to the document
            document.add(table);
            retval = true;
        } catch (DocumentException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        document.close();
        
        return retval;
    }

    @Override
    public boolean createCustomerListReport(List<Order> lstOrder, HttpServletResponse response, DBManager dBManager) {
         boolean retval = false;
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Orders Placed.pdf");
        UserService userService = new UserServiceImpl(dBManager); 
        Set<User> setUser = new HashSet<>();
         //lstIngredients = lstIngredients.stream().filter(ing -> (ing.getMinStockQuantity() > ing.getQuantityInStock())).collect(Collectors.toList());
        for(Order order : lstOrder) {
            setUser.add(userService.getUser(order.getEmail()));
        }
        // Create the PDF document using iText
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(OrderReportProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(OrderReportProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        document.open();
        document.addTitle("Orders Delivered Report");

        // Create the table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add table headers
        PdfPCell cell;
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD);

        cell = new PdfPCell(new Phrase("Customer Name", headerFont));
        table.addCell(cell);
         cell = new PdfPCell(new Phrase("Customer Initial", headerFont));
        table.addCell(cell);
         cell = new PdfPCell(new Phrase("Customer Last Name", headerFont));
        table.addCell(cell);
         cell = new PdfPCell(new Phrase("Customer Contact Details", headerFont));
        table.addCell(cell);

        // Add ingredients to the table
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        for (User user : setUser) {
            cell = new PdfPCell(new Phrase(user.getEmail(), cellFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(user.getfName().substring(0,1).toUpperCase(), cellFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(user.getLname().substring(0,1).toUpperCase() + user.getLname().substring(1), cellFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(user.getPhoneNo(), cellFont));
            table.addCell(cell);
        }

        try {
            // Add the table to the document
            document.add(table);
            retval = true;
        } catch (DocumentException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        document.close();
        
        return retval;
    }

}
