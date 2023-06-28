package za.co.bakery.process;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import za.co.bakery.service.UserService;
import za.co.bakery.serviceImpl.UserServiceImpl;

public class EditUserProcess implements ProcessRequest {
    private UserService userService;
    private String view;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String passEncrypt = (String) request.getServletContext().getAttribute("passwordencrypt");
        String process = request.getParameter("pro");
        HttpSession session=request.getSession();
        switch (process.toLowerCase()) {
            case "eu"://edit user
                User user = (User) session.getAttribute("theUser");
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String firstname = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String title = request.getParameter("title");
                String phonenumber = request.getParameter("phonenumber");
                if (username != null && firstname != null && lastname != null && title != null && phonenumber != null) {
                    username = username.trim().toLowerCase();
                    password = password.trim();
                    firstname = firstname.trim();
                    lastname = lastname.trim();
                    title = title.trim();
                    phonenumber = phonenumber.trim();
                    if (!username.isEmpty() && !password.isEmpty() && !firstname.isEmpty() && !lastname.isEmpty() && !title.isEmpty() && !phonenumber.isEmpty()) {
                        if(user != null) {
                            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                            if (dBManager != null) {
                                user.setEmail(username);
                                //user.setPassword(passwordEncoder.encode(passEncrypt + password).substring(0,5));
                                user.setPassword(password);
                                user.setfName(firstname);
                                user.setLname(lastname);
                                user.setTitle(title);
                                user.setPhoneNo(phonenumber);
                                userService = new UserServiceImpl(dBManager);
                                userService.editUser(user);
                                view = "bakery?pro=gp";
                            }
                        }
                    }
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
            System.out.println("Error Dispatching view: " + ex.getMessage());
        }
    }

}
