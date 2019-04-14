package BestBuySearch;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class URLParser {
    private URL url ;
    private URLConnection connection ;

    URLParser(String urlname) throws Exception{
        this.url = new URL(urlname) ;
    }

    File getWebsiteFile() throws Exception{
        connection = url.openConnection() ;
        File temp = new File("temp.html") ;
        DataInputStream in = new DataInputStream(connection.getInputStream()) ;
        FileOutputStream out = new FileOutputStream(temp) ;

        int c;

        while ((c = in.read()) != -1) {
            out.write(c);
        }

        return temp ;
    }

    public void getImage(ArrayList<SearchResult> results) throws IOException {
        BufferedImage image ;
        for(int i=0;i<results.size();i++){
            File outputImageFile = new File("DownloadedImages/"+results.get(i).getUrl().replace(" ","_")+".jpg") ;
            try {
                image = ImageIO.read(new URL(results.get(i).getImageurl()));
                ImageIO.write(image,"jpg",outputImageFile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    ArrayList<SearchResult> getParseResult(File f) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(f)) ;
        ArrayList<SearchResult> results = new ArrayList<>() ;
        String tem ;
        while ((tem=reader.readLine())!=null){
            if(tem.startsWith("\t      <a href=\"/en-ca/product/")){
                String names = tem.substring("\t      <a href=\"/en-ca/product/".length());
                String[] para = names.split("\"><img src=\"") ;
                String imgurl = para[1].split("\" width=\"150\"")[0] ;
                results.add(new SearchResult(para[0].substring(0,1).toUpperCase()+para[0].substring(1).replace('-',' ').split("/")[0],
                        imgurl,new Timestamp(System.currentTimeMillis()))) ;
            }
        }
        return results ;
    }


}
