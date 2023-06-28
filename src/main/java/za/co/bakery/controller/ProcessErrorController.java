package za.co.bakery.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import za.co.bakery.process.ProcessError;

public class ProcessErrorController implements ProcessError{

    @Override
    public String processError(String errorCode, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        String view = "";
        if(errorCode != null) {
            switch(errorCode) {
                case "0x00001"://dbManager = null
                    try(PrintWriter out = response.getWriter();) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Oops! Something went wrong...(error: 0x00001)');");
                        out.println("</script>");
                        view = "";
                    }
                    catch(IOException ex) {
                        System.out.println("PrintWriter ERROR: " + ex.getMessage());
                    }
                    break;
                case "0x00002"://User Session expired
                    try(PrintWriter out = response.getWriter();) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Session expired! Try logging in again');");
                        out.println("</script>");
                        view = "jsp/login.jsp";
                    }
                    catch(IOException ex) {
                        System.out.println("PrintWriter ERROR: " + ex.getMessage());
                    }
                    break;
                case "0x00003"://Invalid data entered by user
                    try(PrintWriter out = response.getWriter()) {
                            out.println("<script type=\"text/javascript\">");
                            out.println("alert('Invalid data entered!');");
                            out.println("</script>");
                            view = "components/home.jsp";
                    }
                    catch(IOException ex) {
                       System.out.println("PrintWriter ERROR: " + ex.getMessage());
                    }
                    break;
                case "0x00004"://Problem occurred while trying to process data
                    try(PrintWriter out = response.getWriter()) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Problem occurred while trying to process data');");
                        out.println("</script>");
                        view = "";
                    }
                    catch(IOException ex) {
                       System.out.println("PrintWriter ERROR: " + ex.getMessage());
                    }
                    break;
                case "0x00005"://Address already exists
                    try(PrintWriter out = response.getWriter()) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Address already exists');");
                        out.println("</script>");
                        view = "";
                    }
                    catch(IOException ex) {
                       System.out.println("PrintWriter ERROR: " + ex.getMessage());
                    }
                    break;
                case "0x00006"://Category already exists
                    try(PrintWriter out = response.getWriter()) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Category already exists');");
                        out.println("</script>");
                        view = "";
                    }
                    catch(IOException ex) {
                       System.out.println("PrintWriter ERROR: " + ex.getMessage());
                    }
                    break;
                case "0x00007"://Could not load context attributes
                    try(PrintWriter out = response.getWriter()) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Oops! Something went wrong...(error: 0x00007)');");
                        out.println("</script>");
                        view = "";
                    }
                    catch(IOException ex) {
                       System.out.println("PrintWriter ERROR: " + ex.getMessage());
                    }
                    break;
                case "0x00008"://Ingredient already exists
                    try(PrintWriter out = response.getWriter()) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Ingredient already exists');");
                        out.println("</script>");
                        view = "";
                    }
                    catch(IOException ex) {
                       System.out.println("PrintWriter ERROR: " + ex.getMessage());
                    }
                    break;
                case "0x00009"://Order could not be placed
                    try(PrintWriter out = response.getWriter()) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Order could not be placed');");
                        out.println("</script>");
                        view = "";
                    }
                    catch(IOException ex) {
                       System.out.println("PrintWriter ERROR: " + ex.getMessage());
                    }
                    break;
                case "0x00010"://Product already exists
                    try(PrintWriter out = response.getWriter()) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Product already exists');");
                        out.println("</script>");
                        view = "bakery?pro=ap";
                    }
                    catch(IOException ex) {
                       System.out.println("PrintWriter ERROR: " + ex.getMessage());
                    }
                    break;
                case "0x00011"://Order could not be placed
                    try(PrintWriter out = response.getWriter()) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Product out of stock');");
                        out.println("</script>");
                        view = "";
                    }
                    catch(IOException ex) {
                       System.out.println("PrintWriter ERROR: " + ex.getMessage());
                    }
                    break;
                case "0x00012"://Recovery code is invalid
                    try(PrintWriter out = response.getWriter()) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Recovery code is invalid');");
                        out.println("</script>");
                        view = "";
                    }
                    catch(IOException ex) {
                       System.out.println("PrintWriter ERROR: " + ex.getMessage());
                    }
                    break;
                default:
                    view = "components/home.jsp";
            }
        }
        return view;
    }
    
}
