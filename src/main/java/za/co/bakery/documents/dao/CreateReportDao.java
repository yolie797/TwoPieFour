package za.co.bakery.documents.dao;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Ingredient;
import za.co.bakery.model.Order;
import za.co.bakery.model.Unit;
import za.co.bakery.model.User;

public interface CreateReportDao {
    boolean createPlacedOrderReport(List<Order> lstOrder, HttpServletResponse response);
    boolean createOutstandingOrderReport(List<Order> lstOrder, HttpServletResponse response);
    boolean createDeliveredOrderReport(List<Order> lstOrder, HttpServletResponse response);
    boolean createIngredientsInStockReport(List<Ingredient> lstIngredients, List<Unit> lstUnit, HttpServletResponse response);
    boolean createIngredientsOutOfStockReport(List<Ingredient> lstIngredients, List<Unit> lstUnit, HttpServletResponse response);
    boolean createCustomerListReport(List<Order> lstOrder, HttpServletResponse response, DBManager dBManager);
}
