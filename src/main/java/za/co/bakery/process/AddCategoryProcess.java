package za.co.bakery.process;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Category;
import za.co.bakery.service.CategoryService;
import za.co.bakery.serviceImpl.CategoryServiceImpl;

public class AddCategoryProcess implements ProcessRequest{
    private CategoryService categoryService;
    private Category categoryExist = null;
    private ProcessError error = new ProcessErrorController();
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        switch (process.toLowerCase()){
            case "acs"://add category save
                String categoryName = request.getParameter("categoryname");
                if(categoryName != null) {
                    categoryName = categoryName.toLowerCase().trim();
                    if(!categoryName.isEmpty()) {
                        if (dBManager != null) {
                            categoryService = new CategoryServiceImpl(dBManager);
                            categoryExist = categoryService.getCategoryByName(categoryName);
                            if(categoryExist == null) {
                                Category category = new Category(categoryName);
                                categoryService.addCategory(category);
                                view = "pages/addCategory.jsp";
                            }
                            else {
                                view = error.processError("0x00006", response);
                            }
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
