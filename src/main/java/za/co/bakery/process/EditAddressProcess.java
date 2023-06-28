package za.co.bakery.process;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakery.controller.ProcessErrorController;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Address;
import za.co.bakery.model.User;
import za.co.bakery.service.AddressService;
import za.co.bakery.service.UserService;
import za.co.bakery.serviceImpl.AddressServiceImpl;

public class EditAddressProcess implements ProcessRequest{
    private AddressService addressService;
    private UserService userService;
    private Address address = null;
    private ProcessError error = new ProcessErrorController();
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session=request.getSession();
        String addLine1 = null;
        User user = null;
        switch(process.toLowerCase()){
            case "ea"://edit address
                user = (User)session.getAttribute("theUser");
                if(user != null) {
                    if(dBManager != null) {
                        addressService = new AddressServiceImpl(dBManager);
                        Address address = addressService.getResidentAddress(user.getEmail());
                        session.setAttribute("address", address);
                        view = "customer/profile.jsp";
                    }
                    else {
                        view = error.processError("0x00001", response);
                    }
                }
                else {
                    view = error.processError("0x00004", response);
                }
                break;
            case "eas"://edit address save
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String firstname = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String title = request.getParameter("title");
                String phonenumber = request.getParameter("phonenumber");
                request.setAttribute("username", username);
                request.setAttribute("password", password);
                request.setAttribute("firstname", firstname);
                request.setAttribute("lastname", lastname);
                request.setAttribute("title", title);
                request.setAttribute("phonenumber", phonenumber);
                addLine1 = request.getParameter("addressline1");
                String addLine2 = request.getParameter("addressline2");
                String suburb = request.getParameter("suburb");
                String city = request.getParameter("city");
                int postalCode = Integer.parseInt(request.getParameter("postalcode"));
                String province = request.getParameter("province");
                String defaultDelAdd = request.getParameter("defaultdeliveryaddress");
                if(defaultDelAdd == null) {
                    defaultDelAdd = "0";
                }
                boolean isDefault = false;
                if(defaultDelAdd.equals("on")){
                    isDefault = true;
                }
                Address address = (Address)session.getAttribute("address");
                user = (User)session.getAttribute("theUser");
                if(addLine1 != null && addLine2 != null && suburb != null && city != null && postalCode > -1 && province != null) {
                    if(address != null) {
                        addLine1 = addLine1.toLowerCase().trim();
                        addLine2 = addLine2.toLowerCase().trim();
                        suburb = suburb.toLowerCase().trim();
                        city = city.toLowerCase().trim();
                        province = province.toLowerCase().trim();
                        if(!addLine1.isEmpty() && !addLine2.isEmpty() && !suburb.isEmpty() && !city.isEmpty() && !province.isEmpty()) {
                            if(dBManager != null) {
                                addressService = new AddressServiceImpl(dBManager);
                                if(!isDefault) {
                                    Address add = addressService.getResidentAddress(user.getEmail());
                                    addressService.editAddress(add, false, false);
                                    address.setAddressLine1(addLine1);
                                    address.setAddressLine2(addLine2);
                                    address.setSuburb(suburb);
                                    address.setCity(city);
                                    address.setPostalCode(postalCode);
                                    address.setProvince(province);
                                    addressService.editAddress(address, isDefault, true);
                                    address = addressService.getResidentAddress(user.getEmail());
                                    session.setAttribute("address", address);
                                    view = "bakery?pro=eu";
                                    }
                                    else {
                                        if(user != null) {
                                            Address add = addressService.getDefaultDeliveryAddress(user.getEmail());
                                            addressService.editAddress(add, false, false);
                                            address.setAddressLine1(addLine1);
                                            address.setAddressLine2(addLine2);
                                            address.setSuburb(suburb);
                                            address.setCity(city);
                                            address.setPostalCode(postalCode);
                                            address.setProvince(province);
                                            addressService.addAddress(address, user.getEmail(), isDefault, true);
                                            address = addressService.getResidentAddress(user.getEmail());
                                            session.setAttribute("address", address);
                                            view = "bakery?pro=eu";
                                        }
                                        else {
                                            view = error.processError("0x00004", response);
                                        }
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
                        addLine1 = addLine1.toLowerCase().trim();
                        addLine2 = addLine2.toLowerCase().trim();
                        suburb = suburb.toLowerCase().trim();
                        city = city.toLowerCase().trim();
                        province = province.toLowerCase().trim();
                        if(!addLine1.isEmpty() && !addLine2.isEmpty() && !suburb.isEmpty() && !city.isEmpty() && !province.isEmpty()) {
                            if(dBManager != null) {
                                addressService = new AddressServiceImpl(dBManager);
                                if(!isDefault) {
                                    Address add = addressService.getResidentAddress(user.getEmail());
                                    addressService.editAddress(add, false, false);
                                    address.setAddressLine1(addLine1);
                                    address.setAddressLine2(addLine2);
                                    address.setSuburb(suburb);
                                    address.setCity(city);
                                    address.setPostalCode(postalCode);
                                    address.setProvince(province);
                                    addressService.editAddress(address, isDefault, true);
                                    address = addressService.getResidentAddress(user.getEmail());
                                    session.setAttribute("address", address);
                                    view = "bakery?pro=eu";
                                }
                                else {
                                    if(user != null) {
                                        Address add = addressService.getDefaultDeliveryAddress(user.getEmail());
                                        if (add != null) {
                                            addressService.editAddress(add, false, false);
                                        }
                                        address.setAddressLine1(addLine1);
                                        address.setAddressLine2(addLine2);
                                        address.setSuburb(suburb);
                                        address.setCity(city);
                                        address.setPostalCode(postalCode);
                                        address.setProvince(province);
                                        addressService.addAddress(address, user.getEmail(), isDefault, true);
                                        address = addressService.getResidentAddress(user.getEmail());
                                        session.setAttribute("address", address);
                                        view = "bakery?pro=eu";
                                    }
                                    else {
                                        view = error.processError("0x00004", response);
                                    }
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
                }
                else {
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
    
}
