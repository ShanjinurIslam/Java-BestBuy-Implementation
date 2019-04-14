package BestBuySearch;

import javax.xml.crypto.Data;
import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LocalStorage {
    private File file ;
    private String username ;
    private FileWriter fos ;
    private BufferedWriter writer ;
    private BufferedReader reader ;

    LocalStorage(String username,String filename) throws Exception{
        file = new File(filename) ;
        fos = new FileWriter(file,true) ;
        this.username = username ;
        writer = new BufferedWriter(fos) ;
    }

    void addResult(ArrayList<SearchResult> results,String keyword) throws Exception{
        for(int i=0;i<results.size();i++){
            writer.append(username+"_"+ keyword + "_" +results.get(i).getTimestamp()+ "_" +results.get(i).getUrl()+"_"+results.get(i).getImageurl() + "\n") ;
            writer.flush();
        }
    }

    void searchOfflineDatabyname() throws Exception{
        reader = new BufferedReader(new FileReader(file));
        String tem ;
        while ((tem=reader.readLine())!=null){
            String[] data = tem.split("_") ;
            if(data[0].equalsIgnoreCase(username)){
                System.out.println(tem);
            }
        }
    }

    ArrayList<SearchResult> searchOfflinedatabykeyword(String keyword) throws Exception{
        ArrayList<SearchResult> results = new ArrayList<>() ;
        reader = new BufferedReader(new FileReader(file));
        String tem ;
        while ((tem=reader.readLine())!=null){
            String[] data = tem.split("_") ;
            if(data[1].equalsIgnoreCase(keyword) & data[0].equals(username)){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                Date date = dateFormat.parse(tem.split("_")[2]) ;
                Timestamp timestamp1 = new java.sql.Timestamp(date.getTime());
                results.add(new SearchResult(tem.split("_")[3],tem.split("_")[4],timestamp1));
            }
        }

        return results ;
    }

    void searchOfflinedatabytime(Timestamp timestamp) throws Exception{
        reader = new BufferedReader(new FileReader(file));
        String tem ;
        while ((tem=reader.readLine())!=null){
            String[] data = tem.split("_") ;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date date = dateFormat.parse(data[1]) ;
            Timestamp timestamp1 = new java.sql.Timestamp(date.getTime());
            if(timestamp1.compareTo(timestamp)<1){
                System.out.println(tem);
            }
        }
    }

    void deleteOfflinedatabyname() throws IOException{
        reader = new BufferedReader(new FileReader(file));
        writer = new BufferedWriter(new FileWriter(file));
        String tem ;
        while ((tem=reader.readLine())!=null) {
            String[] data = tem.split("_");
            if(data[0].equalsIgnoreCase(username)){
            }
            else{
                writer.write(tem);
                writer.flush();
            }
        }
        reader.close();
        writer.close();
    }

    void deleteOfflinedatabykeyword(String keyword) throws IOException{
        reader = new BufferedReader(new FileReader(file));
        writer = new BufferedWriter(new FileWriter(file));
        String tem ;
        while ((tem=reader.readLine())!=null) {
            String[] data = tem.split("_");
            if(data[0].equalsIgnoreCase(username) & data[1].equals(keyword)){
            }
            else{
                writer.write(tem);
                writer.flush();
            }
        }
        reader.close();
        writer.close();
    }

    void deleteOfflinedatabytimestamp(){

    }

    void close() throws Exception{
        reader.close();
        writer.close();
    }
}
