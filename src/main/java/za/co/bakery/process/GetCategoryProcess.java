package za.co.bakery.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Category;
import za.co.bakery.service.CategoryService;
import za.co.bakery.serviceImpl.CategoryServiceImpl;

public class GetCategoryProcess implements ProcessRequest {

    private CategoryService categoryService;
    private List<Category> lstCategory = new ArrayList<>();
    private String view;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        switch (process.toLowerCase()) {
            case "gc"://get all categories
                if (dBManager != null) {
                    categoryService = new CategoryServiceImpl(dBManager);
                    lstCategory = categoryService.getAllCategory();
                    request.setAttribute("categories", lstCategory);
                    view = "admin/category.jsp";
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
            System.out.println("Error Dispatching " + view + ": " + ex.getMessage());
        }
    }

}
