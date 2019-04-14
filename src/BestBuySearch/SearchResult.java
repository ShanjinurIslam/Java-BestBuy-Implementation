package BestBuySearch;

import java.sql.Timestamp;

public class SearchResult {
    private String url ;
    private String imageurl ;
    private Timestamp timestamp ;

    SearchResult(String url, String imageurl, Timestamp timestamp){
        this.url = url ;
        this.imageurl = imageurl ;
        this.timestamp =timestamp ;
    }

    String getTimestamp(){

        return timestamp.toString() ;
    }

    String getUrl(){

        return url ;
    }

    String getImageurl(){

        return imageurl ;
    }
}
