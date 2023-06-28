package za.co.bakery.process;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Category;
import za.co.bakery.service.CategoryService;
import za.co.bakery.serviceImpl.CategoryServiceImpl;

public class EditCategoryProcess implements ProcessRequest{
    private CategoryService categoryService;
    private Category category = null;
    private ProcessError error = new ProcessErrorController();
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session=request.getSession();
        String categoryName;
        switch (process.toLowerCase()){
            case "ec":
                categoryName = request.getParameter("categoryname");
                if(categoryName != null) {
                    categoryName = categoryName.toLowerCase().trim();
                    if(!categoryName.isEmpty()) {
                        if (dBManager != null) {
                            categoryService = new CategoryServiceImpl(dBManager);
                            category = categoryService.getCategoryByName(categoryName);
                            session.setAttribute("category", category);
                            view = "";
                        }
                        else {
                            view = error.processError("0x00001", response);
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
            case "ecs":
                categoryName = request.getParameter("categoryname");
                category = (Category)session.getAttribute("category");
                if(categoryName != null && category != null) {
                    categoryName = categoryName.toLowerCase().trim();
                    if(!categoryName.isEmpty()) {
                        if (dBManager != null) {
                            category.setCategoryName(categoryName);
                            categoryService.editCategory(category);
                            view = "";
                        }
                        else {
                            view = error.processError("0x00001", response);
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
        } 
        catch (ServletException | IOException ex) {
            System.out.println("Error Dispatching " + view + ": " + ex.getMessage());
        }
    }
    
}
