package za.co.bakery.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Role;
import za.co.bakery.model.User;
import za.co.bakery.service.RoleService;
import za.co.bakery.service.UserService;
import za.co.bakery.serviceImpl.RoleServiceImpl;
import za.co.bakery.serviceImpl.UserServiceImpl;

public class GetUserProcess implements ProcessRequest {

    private UserService userService;
    private RoleService roleService;
    private List<User> lstUser = new ArrayList<>();
    private List<Role> lstRole = new ArrayList<>();
    private Role roleObj;
    private ProcessError error = new ProcessErrorController();
    private String view;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        switch (process.toLowerCase()) {
            case "gau": //get all user
                if (dBManager != null) {
                    userService = new UserServiceImpl(dBManager);
                    roleService = new RoleServiceImpl(dBManager);
                    lstUser = userService.getAllUsers();
                    lstRole = roleService.getAllRoles();
                    request.setAttribute("users", lstUser);
                    request.setAttribute("roles", lstRole);
                    view = "tables/user.jsp";
                }
                else {
                    view = error.processError("0x00001", response);
                }
                break;
            case "gur": //get user by username
                String username = request.getParameter("username");
                if (username != null) {
                    username = username.toLowerCase().trim();
                    if (!username.isEmpty()) {
                        if (dBManager != null) {
                            userService = new UserServiceImpl(dBManager);
                            User userObj = userService.getUser(username);
                            if (userObj != null) {
                                roleService = new RoleServiceImpl(dBManager);
                                request.setAttribute("roles", lstRole);
                                request.setAttribute("user", userObj);
                                view = "";
                            } 
                            else {
                                view = error.processError("0x00004", response);
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
