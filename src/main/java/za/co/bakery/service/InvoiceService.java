package za.co.bakery.service;

import java.util.List;
import za.co.bakery.model.Invoice;

public interface InvoiceService {
    boolean createInvoice(Invoice invoice);
    boolean updateInvoice(Invoice invoice);
    Invoice getOrderInvoice(int orderId);
    Invoice getInvoiceWithIdentifier(int identifier);
    List<Integer> getInvoiceIdentifier();
}
