package za.co.bakery.process;

import javax.servlet.http.HttpServletResponse;

public interface ProcessError {
    String processError(String errorCode, HttpServletResponse response);
}
