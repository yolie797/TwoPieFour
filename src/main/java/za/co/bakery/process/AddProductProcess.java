package za.co.bakery.process;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.service.ProductService;
import za.co.bakery.model.Product;
import za.co.bakery.serviceImpl.ProductServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Part;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.model.Category;
import za.co.bakery.model.Recipe;
import za.co.bakery.service.CategoryService;
import za.co.bakery.service.RecipeService;
import za.co.bakery.serviceImpl.CategoryServiceImpl;
import za.co.bakery.serviceImpl.RecipeServiceImpl;

public class AddProductProcess implements ProcessRequest{
    private ProductService productService;
    private CategoryService categoryService;
    private RecipeService recipeService;
    private List<Category> lstCategory = new ArrayList<>();
    private List<Recipe> lstRecipe = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    private String view;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String imagesPath = (String) request.getServletContext().getAttribute("path");
        String process = request.getParameter("pro");
        switch (process.toLowerCase()){
            case "ap"://add product
                if (dBManager != null) {
                    categoryService = new CategoryServiceImpl(dBManager);
                    recipeService = new RecipeServiceImpl(dBManager);
                    lstCategory = categoryService.getAllCategory();
                    lstRecipe= recipeService.getAllRecipe();
                    request.setAttribute("categories", lstCategory);
                    request.setAttribute("recipes", lstRecipe);
                    view = "pages/addPoduct.jsp";
                }
                //else print error message
                break;
            case "aps"://add product save
                String recipeName = "chocolate cupcake";// request.getParameter("recipename");
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String warnings = request.getParameter("warnings");
                String category = request.getParameter("category"); 
                double price = Double.parseDouble(request.getParameter("price"));
                String dest = null;
                String fileName = null;
                Part filePart = null;
                try{
                    filePart = request.getPart("picture");
                    fileName = getFileName(filePart);
                    dest = imagesPath + "\\" + fileName;
                }
                catch(IOException | ServletException ex){
                    System.out.println("Read picture ERROR: " + ex.getMessage());
                }
                if (title != null && description != null && warnings != null && category != null && price > -1 
                        && fileName != null && filePart != null && dest != null) {
                    title = title.toLowerCase().trim();
                    description = description.toLowerCase().trim();
                    warnings = warnings.toLowerCase().trim();
                    category = category.toLowerCase().trim();
                    if (!title.isEmpty() && !description.isEmpty() && !warnings.isEmpty() && !category.isEmpty()) {
                        if (dBManager != null) {
                            productService = new ProductServiceImpl(dBManager);
                            categoryService = new CategoryServiceImpl(dBManager);
                            recipeService = new RecipeServiceImpl(dBManager);
                            Product productExist = productService.getProductByTitle(title);
                            Category categoryObj = categoryService.getCategoryByName(category);
                            Recipe recipe = recipeService.getRecipeByName(recipeName);
                            if(productExist == null) {
                                if (categoryObj != null && recipe != null) {
                                    Product product = new Product(recipe.getRecipeId(), categoryObj.getCategoryId(),title, description, warnings, price, fileName, LocalDate.now());
                                    if(productService.addProduct(product)) {
                                        try{
                                            Files.copy(filePart.getInputStream(), new File(dest).toPath());
                                        }
                                        catch(IOException ex){
                                            System.out.println("Copy picture to directory ERROR: " + ex.getMessage());
                                        }
                                        view = "bakery?pro=gp";
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
                                view = error.processError("0x00010", response);
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
        } catch (ServletException | IOException ex) {
            System.out.println("Error Dispatching " + view + ": " + ex.getMessage());
        }
    }
    
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
