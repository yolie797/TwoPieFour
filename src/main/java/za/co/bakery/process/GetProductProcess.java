package za.co.bakery.process;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Category;
import za.co.bakery.model.Nutrition;
import za.co.bakery.model.Product;
import za.co.bakery.model.Role;
import za.co.bakery.service.CategoryService;
import za.co.bakery.service.ProductService;
import za.co.bakery.serviceImpl.CategoryServiceImpl;
import za.co.bakery.serviceImpl.ProductServiceImpl;

public class GetProductProcess implements ProcessRequest {

    private ProductService productService;
    private CategoryService categoryService;
    private List<Product> lstProduct = new ArrayList<>();
    private List<Product> lstFilteredProduct = new ArrayList<>();
    private List<Nutrition> lstNutrition = new ArrayList<>();
    private List<Category> lstCategory = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    private String view;
    private DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session = request.getSession();
        Role loggedInUserRole = (Role) session.getAttribute("userRole");
        switch (process.toLowerCase()) {
            case "gp"://get all products
                if (loggedInUserRole != null) {
                    if (dBManager != null) {
                        productService = new ProductServiceImpl(dBManager);
                        categoryService = new CategoryServiceImpl(dBManager);
                        lstProduct = productService.getAllProducts();
                        setDiscountPrice(lstProduct);
                        lstCategory = categoryService.getAllCategory();
                        request.setAttribute("products", lstProduct);
                        request.setAttribute("categories", lstCategory);
                        if (loggedInUserRole.getRoleName().equals("customer")) {
                            view = "customer/homepage.jsp";
                        } else if (loggedInUserRole.getRoleName().equals("admin")) {
                            view = "admin/prodTable.jsp";
                        }
                    } else {
                        view = error.processError("0x00001", response);
                    }
                } else {
                    if (dBManager != null) {
                        productService = new ProductServiceImpl(dBManager);
                        categoryService = new CategoryServiceImpl(dBManager);
                        lstProduct = productService.getAllProducts();
                        setDiscountPrice(lstProduct);
                        lstCategory = categoryService.getAllCategory();
                        request.setAttribute("products", lstProduct);
                        request.setAttribute("categories", lstCategory);
                        view = "customer/homepage.jsp";
                    }
                }
                break;
            case "gpc"://get product by category
                String category = request.getParameter("category");
                if (category != null) {
                    category = category.toLowerCase().trim();
                    if (!category.isEmpty()) {
                        if (loggedInUserRole != null) {
                            if (dBManager != null) {
                                categoryService = new CategoryServiceImpl(dBManager);
                                Category categoryObj = categoryService.getCategoryByName(category);
                                lstCategory = categoryService.getAllCategory();
                                if (categoryObj != null) {
                                    productService = new ProductServiceImpl(dBManager);
                                    lstProduct = productService.getProductByCategory(categoryObj.getCategoryId());
                                    setDiscountPrice(lstProduct);
                                    request.setAttribute("products", lstProduct);
                                    request.setAttribute("categories", lstCategory);
                                    if (loggedInUserRole.getRoleName().equals("customer")) {
                                        view = "customer/homepage.jsp";
                                    } else if (loggedInUserRole.getRoleName().equals("admin")) {
                                        view = "admin/prodTable.jsp";
                                    }
                                } else {
                                    view = error.processError("0x00004", response);
                                }
                            } else {
                                view = error.processError("0x00001", response);
                            }
                        } else {
                            if (dBManager != null) {
                                categoryService = new CategoryServiceImpl(dBManager);
                                Category categoryObj = categoryService.getCategoryByName(category);
                                lstCategory = categoryService.getAllCategory();
                                if (categoryObj != null) {
                                    productService = new ProductServiceImpl(dBManager);
                                    lstProduct = productService.getProductByCategory(categoryObj.getCategoryId());
                                    setDiscountPrice(lstProduct);
                                    request.setAttribute("products", lstProduct);
                                    request.setAttribute("categories", lstCategory);
                                    view = "customer/homepage.jsp";
                                }
                            }
                        }
                    } else {
                        view = error.processError("0x00004", response);
                    }
                } else {
                    view = error.processError("0x00004", response);
                }
                break;
            case "gps"://get product search
                String title = request.getParameter("title");
                if (title != null) {
                    title = title.toLowerCase().trim();
                    if (!title.isEmpty()) {
                        if (dBManager != null) {
                            categoryService = new CategoryServiceImpl(dBManager);
                            lstCategory = categoryService.getAllCategory();
                            productService = new ProductServiceImpl(dBManager);
                            lstProduct = productService.getAllProducts();
                            for (Product product : lstProduct) {
                                if (product.getTitle().contains(title)) {
                                    lstFilteredProduct.add(product);
                                }
                            }
                            setDiscountPrice(lstFilteredProduct);
                            request.setAttribute("products", lstFilteredProduct);
                            request.setAttribute("categories", lstCategory);
                            if (loggedInUserRole != null) {
                                if (loggedInUserRole.getRoleName().equals("customer")) {
                                    view = "customer/homepage.jsp";
                                } else if (loggedInUserRole.getRoleName().equals("admin")) {
                                    view = "admin/prodTable.jsp";
                                }
                            } else {
                                view = "customer/homepage.jsp";
                            }
                        } else {
                            view = error.processError("0x00001", response);
                        }
                    } else {
                        view = error.processError("0x00004", response);
                    }
                } else {
                    view = error.processError("0x00004", response);
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

    private void setDiscountPrice(List<Product> lstProd) {
        if (lstProd != null) {
            for (Product p : lstProd) {
                if (p.getDiscount() > 0) {
                    p.setPrice(Double.parseDouble(df.format(p.getPrice() - (p.getPrice() * p.getDiscount() / 100))));
                }
            }
        }
    }
}
