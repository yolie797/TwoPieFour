package za.co.bakery.db.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private String url;
    private String database;
    private String username;
    private String password;
    private String driver;
    private Connection connection = null;

    public DBManager(String url, String database, String username, String password, String driver) {
        this.url = url;
        this.database = database;
        this.username = username;
        this.password = password;
        this.driver = driver;
    }
    
    public Connection getConnection(){
        if(connection == null)
            createConnection();
        return connection;
    }
    
    public boolean closeConnection(){
        boolean retVal = false;
        if(connection != null){
            try{
                connection.close();
                retVal = true;
            }
            catch(SQLException exception){
                System.out.println("Close connection ERROR: " + exception.getMessage());
            }
        }
        return retVal;
    }
    
    private boolean createConnection(){
        boolean retVal = false;
        try{
            Class.forName(driver);
        }
        catch(ClassNotFoundException exception){
            System.out.println("Error: " + exception.getMessage());
            return retVal;
        }
        try {
            connection = DriverManager.getConnection(url + database, username, password);
            retVal = true;
        } 
        catch (SQLException exception){
            System.out.println("Create connection ERROR: " + exception.getMessage());
            return retVal;
        }
        return retVal;
    }

    @Override
    public String toString() {
        return "DBManager{" + "url=" + url + ", database=" + database + ", username=" + username + ", password=" + password + ", driver=" + driver + ", connection=" + connection + '}';
    }
}
