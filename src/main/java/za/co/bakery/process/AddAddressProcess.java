package za.co.bakery.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Address;
import za.co.bakery.model.User;
import za.co.bakery.service.AddressService;
import za.co.bakery.serviceImpl.AddressServiceImpl;
import za.co.bakery.controller.ProcessErrorController;

public class AddAddressProcess implements ProcessRequest{
    private AddressService addressService;
    private List<Address> lstAddress = new ArrayList<>();
    private ProcessError error = new ProcessErrorController();
    private Address address = null;
    private String view;
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        DBManager dBManager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String process = request.getParameter("pro");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("theUser");
        User userReg = (User)session.getAttribute("user");
        Address resAddress = (Address)session.getAttribute("resaddress");
        String addLine1 = null;
        String addLine2 = null;
        String suburb = null;
        String city = null;
        int postalCode = -2;
        String province = null;
        switch (process.toLowerCase()){
            case "sa"://select address
                String isTrue1 = request.getParameter("isChecked");
                String isTrue2 = request.getParameter("default");
                if(isTrue1 != null || isTrue2 != null) {
                    if(isTrue1 == null){
                        isTrue1 = "";
                    }
                    if(isTrue2 == null){
                        isTrue2 = "";
                    }
                    if(isTrue1.equals("true") || isTrue2.equals("true")) {
                        if(user != null) {
                            if(dBManager != null) {
                                addressService = new AddressServiceImpl(dBManager);
                                address = addressService.getDefaultDeliveryAddress(user.getEmail());
                                if(address != null) {
                                    session.setAttribute("deliveryaddress", address);
                                    view = "customer/checkout1.jsp";
                                }
                                else {
                                    session.setAttribute("deliveryaddress", null);
                                    error.processError("0x00014", response);
                                }
                            }
                            else {
                                error.processError("0x00001", response);
                            }
                        }
                        else {
                            error.processError("0x00002", response);
                        }
                    }
                    else {
                        session.setAttribute("deliveryaddress", null);
                        view = "customer/checkout1.jsp";
                    }
                }
                else {
                    session.setAttribute("deliveryaddress", null);
                    view = "customer/checkout.jsp";
                }
                break;
            case "aas"://add address save
                addLine1 = request.getParameter("addressline1");
                addLine2 = request.getParameter("addressline2");
                suburb = request.getParameter("suburb");
                city = request.getParameter("city");
                String pc = request.getParameter("postalcode");
                if(pc != null) {
                    postalCode = Integer.parseInt(request.getParameter("postalcode"));
                }
                province = request.getParameter("province");
                String defaultDelAdd = request.getParameter("defaultdeliveryaddress");
                boolean isDefault = Boolean.parseBoolean(defaultDelAdd);
                if(addLine1 != null && addLine2 != null && suburb != null && city != null && postalCode > -1 && province != null) {
                    if(user != null) {
                        addLine1 = addLine1.toLowerCase().trim();
                        addLine2 = addLine2.toLowerCase().trim();
                        suburb = suburb.toLowerCase().trim();
                        city = city.toLowerCase().trim();
                        province = province.trim();
                        if(!addLine1.isEmpty() && !addLine2.isEmpty() && !suburb.isEmpty() && !city.isEmpty() && !province.isEmpty()) {
                            if(dBManager != null) {
                                addressService = new AddressServiceImpl(dBManager);
                                    if(!isDefault) {
                                        Address address = new Address(addLine1, addLine2, suburb, city, postalCode, province);
                                        addressService.addAddress(address, user.getEmail(), isDefault, true);
                                        view = "bakery?pro=eu";
                                    }
                                    else {
                                        address = addressService.getDefaultDeliveryAddress(user.getEmail());
                                        if(address != null) {
                                            addressService.editAddress(address, isDefault, true);
                                            view = "bakery?pro=eu";
                                        }
                                        else {
                                            
                                        }
                                    }
                                }
                            else {
                                error.processError("0x00001", response);
                            }
                        }
                        else {
                            error.processError("0x00004", response);
                        }
                    }
                    else {
                        error.processError("0x00002", response);
                    }
                }
                else if(resAddress != null) {
                    boolean isDefAdd = (boolean) session.getAttribute("defaultdeladdress");
                    if(dBManager != null) {
                        addressService = new AddressServiceImpl(dBManager);
                        addressService.addAddress(resAddress, userReg.getEmail(), isDefAdd, true);
                        view = "jsp/login2.jsp";
                    }
                    else {
                        error.processError("0x00001", response);
                    }
                }
                else {
                    error.processError("0x00003", response);
                }
                break;
            case "das"://delivery address save
                addLine1 = request.getParameter("addressline1");
                addLine2 = request.getParameter("addressline2");
                suburb = request.getParameter("suburb");
                city = request.getParameter("city");
                postalCode = Integer.parseInt(request.getParameter("postalcode"));
                province = request.getParameter("province");
                if(addLine1 != null && addLine2 != null && suburb != null && city != null && postalCode > -1 && province != null) {
                    addLine1 = addLine1.toLowerCase().trim();
                    addLine2 = addLine2.toLowerCase().trim();
                    suburb = suburb.toLowerCase().trim();
                    city = city.toLowerCase().trim();
                    province = province.trim();
                    if(!addLine1.isEmpty() && !addLine2.isEmpty() && !suburb.isEmpty() && !city.isEmpty() && !province.isEmpty()) {
                        if(user != null) {
                            if(dBManager != null) {
                                addressService = new AddressServiceImpl(dBManager);
                                Address delAddress = new Address(addLine1, addLine2, suburb, city, postalCode, province);
                                Address add = addressService.getDefaultDeliveryAddress(user.getEmail());
                                if(add != null) {
                                    addressService.editAddress(address, false, false);
                                }
                                addressService.addAddress(delAddress, user.getEmail(), true, false);
                                delAddress = addressService.getAddressByName(addLine1);
                                session.setAttribute("deliveryaddress", delAddress);
                                view = "bakery?pro=ci";//create invoice
                            }
                            else {
                                error.processError("0x00001", response);
                            }
                        }
                        else {
                            error.processError("0x00002", response);
                        }
                    }
                    else {
                        error.processError("0x00004", response);
                    }
                }
                else {
                    error.processError("0x00003", response);
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
