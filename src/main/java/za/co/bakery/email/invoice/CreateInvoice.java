package za.co.bakery.email.invoice;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.OrderLineItem;
import za.co.bakery.model.Product;
import za.co.bakery.model.User;
import za.co.bakery.service.ProductService;
import za.co.bakery.serviceImpl.ProductServiceImpl;

public class CreateInvoice {
    private int invoiceId;
    private PDDocument invoice;
    private File file;

    public CreateInvoice(String filepath) {
        this.invoice = new PDDocument();
        PDPage newpage = new PDPage();
        this.invoice.addPage(newpage);
        this.file = new File(filepath);
    }
    
    public File WriteInvoice(User user, List<OrderLineItem> lstOrderLineItem, DBManager dBManager) {
    PDPage mypage = invoice.getPage(0);
    double total = 0.0;
    ProductService productService = new ProductServiceImpl(dBManager);
    try {
        //Prepare Content Stream
        PDPageContentStream cs = new PDPageContentStream(invoice, mypage);

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 20);
        cs.newLineAtOffset(200, 760);
        cs.showText("Bakery Two Pie Four");
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 18);
        cs.newLineAtOffset(270, 690);
        cs.showText("Invoice");
        cs.newLineAtOffset(-15, 32);
        cs.showText(LocalDate.now().toString());
        cs.endText();
        
        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.setLeading(20f);
        cs.newLineAtOffset(60, 610);
        cs.showText("Customer Name: ");
        cs.newLine();
        cs.showText("Phone Number: ");
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.setLeading(20f);
        cs.newLineAtOffset(170, 610);
        cs.showText(user.getfName());
        cs.newLine();
        cs.showText(user.getPhoneNo());
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.newLineAtOffset(80, 540);
        cs.showText("Product Name");
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.newLineAtOffset(200, 540);
        cs.showText("Unit Price");
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.newLineAtOffset(310, 540);
        cs.showText("Quantity");
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.newLineAtOffset(410, 540);
        cs.showText("Price");
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 12);
        cs.setLeading(20f);
        cs.newLineAtOffset(80, 520);
        for(OrderLineItem item : lstOrderLineItem) {
            Product product = productService.getProductById(item.getProductId());
            if(product != null) {
                if(product.getTitle().length() > 15){
                    cs.showText(product.getTitle().substring(0, 16) + "...");
                    cs.newLine();
                }
                else {
                    cs.showText(product.getTitle());
                    cs.newLine();
                }
              
            }
        }
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 12);
        cs.setLeading(20f);
        cs.newLineAtOffset(200, 520);
        for(OrderLineItem item : lstOrderLineItem) {
            Product product = productService.getProductById(item.getProductId());
            if(product != null) {
              cs.showText(String.valueOf(product.getPrice()));
              cs.newLine();
            }
        }
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 12);
        cs.setLeading(20f);
        cs.newLineAtOffset(310, 520);
        for(OrderLineItem item : lstOrderLineItem) {
          cs.showText(String.valueOf(item.getQuantity()));
          cs.newLine();
        }
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 12);
        cs.setLeading(20f);
        cs.newLineAtOffset(410, 520);
        for(OrderLineItem item : lstOrderLineItem) {
              cs.showText(String.valueOf(item.getTotalPrice()));
              cs.newLine();
        }
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.newLineAtOffset(310, (500-(20*lstOrderLineItem.size())));
        cs.showText("Total: ");
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.newLineAtOffset(410, (500-(20*lstOrderLineItem.size())));
        for(OrderLineItem item : lstOrderLineItem) {
              total += item.getTotalPrice();
        }
        cs.showText(String.valueOf(total));
        cs.endText();

        cs.close();
        invoice.save(file);
      
    } 
    catch (IOException e) {
        e.printStackTrace();
    }
        return file;
    }
    
}
