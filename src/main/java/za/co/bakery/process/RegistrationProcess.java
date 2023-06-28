package za.co.bakery.process;

import za.co.bakery.email.confirmation.EmailSettings;
import java.io.IOException;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakery.passwordEnc.PasswordEncrypt;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Role;
import za.co.bakery.model.User;
import za.co.bakery.service.RoleService;
import za.co.bakery.service.UserService;
import za.co.bakery.serviceImpl.RoleServiceImpl;
import za.co.bakery.serviceImpl.UserServiceImpl;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.model.Address;

public class RegistrationProcess implements ProcessRequest {
    private UserService userService;
    private RoleService roleService;
    private Role roleObj;
    private ProcessError error = new ProcessErrorController();
    private String view;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session = request.getSession();
        String confirmationCode;
        String enteredCode;
        int postalCode = -2;
        switch (process.toLowerCase()) {
            case "rp":
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String firstname = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String title = request.getParameter("title");
                String phonenumber = request.getParameter("phonenumber");
                boolean active = true;
                String addLine1 = request.getParameter("addressline1");
                String addLine2 = request.getParameter("addressline2");
                String suburb = request.getParameter("suburb");
                String city = request.getParameter("city");
                String pc = request.getParameter("postalcode");
                if(pc != null) {
                    postalCode = Integer.parseInt(request.getParameter("postalcode"));
                }
                String province = request.getParameter("province");
                String defaultDelAdd = request.getParameter("defaultdeliveryaddress");
                boolean isDefault = false;
                if(defaultDelAdd != null) {
                    if(defaultDelAdd.equals("on")) {
                        isDefault = true;
                    }
                }
                UUID uuid = UUID.randomUUID();
                confirmationCode = uuid.toString().substring(0, 8);
                session.setAttribute("confirmationcode", confirmationCode);
                if (username != null && password != null && firstname != null && lastname != null && title != null && phonenumber != null
                        && addLine1 != null && addLine2 != null && suburb != null && city != null && postalCode > -1 && province != null) {
                    username = username.trim().toLowerCase();
                    password = password.trim();
                    firstname = firstname.trim().toLowerCase();
                    lastname = lastname.trim().toLowerCase();
                    title = title.trim().toLowerCase();
                    phonenumber = phonenumber.trim();
                    addLine1 = addLine1.trim().toLowerCase();
                    addLine2 = addLine2.trim().toLowerCase();
                    suburb = suburb.trim().toLowerCase();
                    city = city.trim().toLowerCase();
                    province = province.trim().toLowerCase();
                    //PasswordEncrypt pE = new PasswordEncrypt();
                    if (!username.isEmpty() && !password.isEmpty() && !firstname.isEmpty() && !lastname.isEmpty() && !title.isEmpty() && !phonenumber.isEmpty()
                            && !addLine1.isEmpty() && !addLine2.isEmpty() && !suburb.isEmpty() && !city.isEmpty() && !province.isEmpty()) {
                        if(dBManager != null){
                            roleService = new RoleServiceImpl(dBManager);
                            roleObj = roleService.getByName("customer");
                            int role = roleObj.getRoleId();
                            if(roleObj != null && role > 0) {
                                User user = new User(username, role, firstname, lastname, title, password, phonenumber, active);
                                Address resAddress = new Address(addLine1, addLine2, suburb, city, postalCode, province);
                                session.setAttribute("user", user);
                                session.setAttribute("resaddress", resAddress);
                                session.setAttribute("defaultdeladdress", isDefault);
                                try {
                                    EmailSettings es = new EmailSettings();
                                    es.sendRegistrationEmail(firstname, username,  confirmationCode);
                                } 
                                catch (MessagingException ex) {
                                    System.out.println("could not send email" +ex.getMessage());
                                    return;
                                }
                                view = "customer/registerconfirm.jsp";
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
            case "rcp"://registration confirmation process
                enteredCode = request.getParameter("enteredCode"); 
                confirmationCode = (String) session.getAttribute("confirmationcode");
                User user = (User)session.getAttribute("user");
                if(confirmationCode != null) {
                    confirmationCode = confirmationCode.trim();
                    if(!confirmationCode.isEmpty()) {
                        if (confirmationCode.equals(enteredCode)) {
                            if(user != null){
                                if(dBManager != null){
                                    userService = new UserServiceImpl(dBManager);
                                    if(userService.addUser(user)) {
                                        view = "bakery?pro=aas";
                                    }
                                    else {
                                        error.processError("0x00005", response);
                                    }
                                }
                                else {
                                    error.processError("0x00001", response);
                                }
                            }
                            else {
                                error.processError("0x00007", response);
                            }
                        }
                        else {
                            error.processError("0x00015", response);
                        }
                    }
                    else {
                        error.processError("0x00004", response);
                    }
                }
                else {
                    error.processError("0x00015", response);
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
