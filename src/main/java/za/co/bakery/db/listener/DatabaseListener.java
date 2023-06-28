package za.co.bakery.db.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import za.co.bakery.db.manager.DBManager;

@WebListener()
public class DatabaseListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        String url = sc.getInitParameter("url");
        String username = sc.getInitParameter("username");
        String password = sc.getInitParameter("password");
        String driver = sc.getInitParameter("driver");
        String database = sc.getInitParameter("database");
        String passEnrypt = sc.getInitParameter("passEncrypt");
        DBManager dbm = new DBManager(url, database, username, password, driver);
        dbm.getConnection();
        sc.setAttribute("dbmanager", dbm);
        sc.setAttribute("passwordencrypt", passEnrypt);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        DBManager dbm = (DBManager) sc.getAttribute("dbmanager");
        if (dbm != null) {
            dbm.closeConnection();
        }
    }
}
