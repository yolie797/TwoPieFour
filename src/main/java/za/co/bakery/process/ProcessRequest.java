package za.co.bakery.process;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ProcessRequest {
    void processRequest(HttpServletRequest request, HttpServletResponse response);
    void processResponse(HttpServletRequest request, HttpServletResponse response);
}
