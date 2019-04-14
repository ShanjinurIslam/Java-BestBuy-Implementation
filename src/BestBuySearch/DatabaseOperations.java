package BestBuySearch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DatabaseOperations implements AutoCloseable{
    private Connection connection ;
    String username ;
    private Statement statement;
    private ResultSet resultSet;

    DatabaseOperations(String username) throws Exception{
        this.username=username ;
        connection = DatabaseConnect.getDatabaseConnection() ;
        statement = connection.createStatement();
    }

    void addResult(ArrayList<SearchResult> results,String keyword) throws Exception{
        for(int i=0;i<results.size();i++){
            addResult(results.get(i),keyword);
        }
    }

    void addResult(SearchResult result,String keyword) throws Exception{
        String sql = "INSERT INTO " +
                "`sql12264723`.`SearchData`(`username`, `keyword` ,`timestamp`, `productname`,`imageurl`) " +
                "VALUES ('"+username+"', '"+keyword+"', '"+result.getTimestamp()+"', '"+result.getUrl()+"','"+result.getImageurl()+"')" ;
        System.out.println(sql) ;
        statement.execute(sql) ;
    }

    ResultSet searchResultbyUsername() throws Exception{
        String sql = "SELECT * from `sql12264723`.`SearchData` " +
                "where username='"+username+"'" ;
        ResultSet resultSet = statement.executeQuery(sql) ;
        return resultSet ;
    }

    ResultSet searchResultbyKeyword(String keyword) throws Exception{
        String sql = "SELECT * from `sql12264723`.`SearchData` " +
                "where username='"+username+"' and keyword='"+keyword+"'" ;
        ResultSet resultSet = statement.executeQuery(sql) ;
        return resultSet ;
    }

    ResultSet searchResultbyTime(Timestamp t) throws Exception{
        String sql = "SELECT * from `sql12264723`.`SearchData` " +
                "where username='"+username+"' and timestamp <'"+t.toString()+"'" ;
        ResultSet resultSet = statement.executeQuery(sql) ;
        return resultSet ;
    }

    void deleteResultbyuser()throws Exception{
        String sql = "DELETE FROM `sql12264723`.`SearchData` " +
                "where  username='"+username+"'" ;
        statement.execute(sql) ;
    }

    void deleteResultbykeyword(String keyword)throws Exception{
        String sql = "DELETE FROM `sql12264723`.`SearchData` " +
                "where  username='"+username+"' and keyword='"+keyword+"'" ;
        statement.execute(sql) ;
    }

    void deleteResultbytime(Timestamp t) throws Exception{
        String sql = "DELETE from `sql12264723`.`SearchData` " +
                "where username='"+username+"' and timestamp <'"+t.toString()+"'" ;
        statement.execute(sql) ;
    }

    @Override
    public void close() throws Exception {
        statement.close();
        connection.close();
    }
}
