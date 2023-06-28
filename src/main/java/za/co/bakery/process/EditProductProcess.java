package za.co.bakery.process;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Category;
import za.co.bakery.model.Product;
import za.co.bakery.model.Recipe;
import za.co.bakery.service.CategoryService;
import za.co.bakery.service.ProductService;
import za.co.bakery.service.RecipeService;
import za.co.bakery.serviceImpl.CategoryServiceImpl;
import za.co.bakery.serviceImpl.ProductServiceImpl;
import za.co.bakery.serviceImpl.RecipeServiceImpl;

public class EditProductProcess implements ProcessRequest{
    private ProductService productService;
    private CategoryService categoryService;
    private RecipeService recipeService;
    private List<Category> lstCategory = new ArrayList<>();
    private List<Recipe> lstRecipe = new ArrayList<>();
    private Product product = null;
    private String view = "";
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String imagesPath = (String) request.getServletContext().getAttribute("path");
        String process = request.getParameter("pro");
        HttpSession session=request.getSession();
        String title = null;
        switch (process.toLowerCase()){
            case "ep"://edit product
                title = request.getParameter("title");
                if(title != null) {
                    title = title.toLowerCase().trim();
                    if(!title.isEmpty()){
                        if (dBManager != null) {
                            productService = new ProductServiceImpl(dBManager);
                            categoryService = new CategoryServiceImpl(dBManager);
                            recipeService = new RecipeServiceImpl(dBManager);
                            product = productService.getProductByTitle(title);
                            lstCategory = categoryService.getAllCategory();
                            lstRecipe = recipeService.getAllRecipe();
                            if(product != null) {
                                session.setAttribute("product", product);
                                request.setAttribute("categories", lstCategory);
                                request.setAttribute("recipes", lstRecipe);
                                view = "pages/editProduct.jsp";
                            }
                        }
                    }
                }
                //else error print message
                break;
            case "eps"://edit product save
                String recipeName = request.getParameter("recipename");
                title = request.getParameter("title");
                String description = request.getParameter("description");
                String warnings = request.getParameter("warnings");
                String category = request.getParameter("category"); 
                double price = Double.parseDouble(request.getParameter("price"));
                int discount = Integer.parseInt(request.getParameter("discount"));
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
                product = (Product) session.getAttribute("product");
                if (title != null && description != null && warnings != null && category != null && price > -1
                            && discount < 101 && discount > -1 && fileName != null && filePart != null && dest != null && product != null) {
                    title = title.toLowerCase().trim();
                    description = description.toLowerCase().trim();
                    warnings = warnings.toLowerCase().trim();
                    category = category.toLowerCase().trim();
                    if (!title.isEmpty() && !description.isEmpty() && !warnings.isEmpty() && !category.isEmpty()) {
                        if (dBManager != null) {
                            categoryService = new CategoryServiceImpl(dBManager);
                            recipeService = new RecipeServiceImpl(dBManager);
                            Category categoryObj = categoryService.getCategoryByName(category);
                            Recipe recipe = recipeService.getRecipeByName(recipeName);
                            if(categoryObj != null && recipe != null) {
                                product.setRecipeId(recipe.getRecipeId());
                                product.setCategoryId(categoryObj.getCategoryId());
                                product.setTitle(title);
                                product.setDescription(description);
                                product.setWarnings(warnings);
                                product.setPrice(price);
                                product.setPicture(fileName);
                                product.setDiscount(discount);
                                productService = new ProductServiceImpl(dBManager);
                                if(productService.editProduct(product)) {
                                    try{
                                        Files.copy(filePart.getInputStream(), new File(dest).toPath());
                                    }
                                    catch(IOException ex){
                                        System.out.println("Copy picture to directory ERROR: " + ex.getMessage());
                                    }
                                    view = "";
                                }
                            }
                        }
                    }
                }
                //else print error message
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
