package BestBuySearch;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class DataStorage {
    private String username ;
    private String keyword ;
    private HashMap<String, ArrayList<SearchResult>> dataStorageHashMap ;
    private DatabaseOperations operations ;
    private LocalStorage localStorage;
    private File log ;
    private BufferedWriter logwriter ;

    DataStorage(String username) throws Exception{
        this.username = username ;
        log = new File("Transactionlog.txt") ;
        logwriter = new BufferedWriter(new FileWriter(log,true));
        dataStorageHashMap = new HashMap<>() ;
        operations = new DatabaseOperations(username);
        localStorage = new LocalStorage(username,"Searchdata.txt") ;
    }


    void storeData() throws Exception{
        ArrayList<SearchResult> results = dataStorageHashMap.get(username);
        operations.addResult(results,keyword);
        localStorage.addResult(results,keyword);
    }

    void close() throws Exception{
        operations.close();
        localStorage.close();
        logwriter.close();
    }

    public void addResult(ArrayList<SearchResult> results, String keyword) throws Exception{
        this.keyword = keyword ;
        dataStorageHashMap.put(username,results) ;
        logwriter.append("Operation: Addition_Username : "+ username + "_Search Keyword: " + new String(Base64.getEncoder().encode(keyword.getBytes())) + "_" + "Showed Results : "+results.size() + "_Time: "+ new Timestamp(System.currentTimeMillis())+ "\n");
        logwriter.flush();
    }

    public ArrayList<SearchResult> viewSearchfromLocal(String text) throws Exception{
        return localStorage.searchOfflinedatabykeyword(text);
    }
}
