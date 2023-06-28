package za.co.bakery.process;

import za.co.bakery.email.confirmation.EmailSettings;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Role;
import za.co.bakery.model.User;
import za.co.bakery.service.RoleService;
import za.co.bakery.service.UserService;
import za.co.bakery.serviceImpl.RoleServiceImpl;
import za.co.bakery.serviceImpl.UserServiceImpl;
import za.co.bakery.passwordEnc.PasswordEncrypt;

public class LoginProcess implements ProcessRequest {

    private UserService userService;
    private RoleService roleService;
    private Role loggedInUserRole = null;
    private ProcessError error = new ProcessErrorController();
    private String view;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String passEncrypt = (String) request.getServletContext().getAttribute("passwordencrypt");
        String process = request.getParameter("pro");
        HttpSession session = request.getSession();
        String confirmationCode;
        String username;
        switch (process.toLowerCase()) {
            case "li": // log in
                username = request.getParameter("username");
                String password = request.getParameter("password");
                if (username != null && password != null) {
                    username = username.trim().toLowerCase();
                    password = password.trim();
                    if (!username.isEmpty() && !password.isEmpty()) {
                        if (dBManager != null) {
                            userService = new UserServiceImpl(dBManager);
                            User dummyUser = userService.getUser(username);
                            if (dummyUser != null) {
                                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                                //if(passwordEncoder.matches((password + passEncrypt).substring(0, 5), dummyUser.getPassword())) {
                                //User user = new User(username, (password + passEncrypt).substring(0, 5));
                                User user = new User(username, password);
                                roleService = new RoleServiceImpl(dBManager);
                                User loggedInUser = userService.userLogin(user);
                                if (loggedInUser != null) {
                                    loggedInUserRole = roleService.getById(loggedInUser.getRoleId());
                                    session.setAttribute("theUser", loggedInUser);
                                    session.setAttribute("userRole", loggedInUserRole);
                                    if (loggedInUserRole.getRoleName().equals("customer")) {
                                        view = "bakery?pro=gp";
                                    } else if (loggedInUserRole.getRoleName().equals("admin")) {
                                        view = "admin/dashboard.jsp";
                                    }
                                } //}
                                else {
                                    view = error.processError("0x00013", response);
                                }
                            } else {
                                view = error.processError("0x00013", response);
                            }
                        } else {
                            view = error.processError("0x00001", response);
                        }
                    } else {
                        view = error.processError("0x00004", response);
                    }
                } else {
                    view = error.processError("0x00003", response);
                }
                break;
            case "lo": //logout               
                session.invalidate();
                view = "index.jsp";
                break;

            case "fp": //forget password
                username = request.getParameter("username");
                UUID uuid = UUID.randomUUID();
                confirmationCode = uuid.toString().substring(0, 8);
                if (username != null) {
                    username = username.toLowerCase().trim();
                    if (!username.isEmpty()) {
                        if (dBManager != null) {
                            userService = new UserServiceImpl(dBManager);
                            User userDummy = userService.getUser(username);
                            session.setAttribute("user", userDummy);
                            session.setAttribute("confirmationcode", confirmationCode);
                            try {
                                EmailSettings es = new EmailSettings(userDummy.getEmail(), confirmationCode);
                                es.sendPasswordRecoveryEmail(username, confirmationCode);
                            } catch (MessagingException ex) {
                                System.out.println("could not send email" + ex.getMessage());
                            }
                            view = "";
                        } else {
                            view = error.processError("0x00001", response);
                        }
                    } else {
                        view = error.processError("0x00004", response);
                    }
                } else {
                    view = error.processError("0x00003", response);
                }
                break;
            case "fpr": //forget password recovery
                String enteredCode = request.getParameter("enteredCode");
                String pass = request.getParameter("password");
                confirmationCode = (String) session.getAttribute("confirmationcode");
                User user = (User) session.getAttribute("user");
                if (confirmationCode.equals(enteredCode)) {
                    PasswordEncrypt pE = new PasswordEncrypt();// the password enc
                    if (user != null) {
                        if (dBManager != null) {
                            userService = new UserServiceImpl(dBManager);
                            pE.encryptPassword(pass);
                            user.setPassword(pass);
                            userService.editUser(user);
                            try (PrintWriter out = response.getWriter()) {
                                out.println("alert('Successfully recovered account');");
                            } catch (IOException ex) {
                                System.out.println("PrintWriter ERROR: " + ex.getMessage());
                            }
                            view = "";
                        } else {
                            view = error.processError("0x00001", response);
                        }
                    } else {
                        view = error.processError("0x00004", response);
                    }
                } else {
                    view = error.processError("0x00012", response);
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
