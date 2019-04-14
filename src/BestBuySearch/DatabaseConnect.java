package BestBuySearch;

import com.mysql.jdbc.* ;
import java.sql.DriverManager;

public class DatabaseConnect {
    private Connection connection = null ;
    private static DatabaseConnect databaseConnect;

    DatabaseConnect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12264723",
                    "sql12264723","IY272RgmK9");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public Connection getConnection(){
        return connection ;
    }

    public static Connection getDatabaseConnection(){
        if(databaseConnect ==null){
            databaseConnect = new DatabaseConnect() ;
            return databaseConnect.getConnection() ;
        }
        return databaseConnect.getConnection() ;
    }
}
