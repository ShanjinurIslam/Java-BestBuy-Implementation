package BestBuySearch;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class UserPanel {
    String username ;
    Stage appStage ;
    DataStorage storage ;
    URLParser parser ;
    @FXML
    TextField searchonlineitem ;
    @FXML
    Button logout ;
    @FXML
    Button getonlineresults ;
    @FXML
    VBox showonliner ;
    @FXML
    ScrollPane resultonlinepage ;
    @FXML
    TextField searchofflineitem ;
    @FXML
    Button getofflineresults ;
    @FXML
    VBox showoffliner ;
    @FXML
    ScrollPane resultofflinepage ;
    @FXML

    HBox getIndividual(String name,String url){
        HBox box = new HBox();
        System.out.println("Woring on result :"+
                name + "\t link: " + url);
        box.getChildren().addAll(new Label(name),new ImageView(url)) ;
        return box ;
    }

    HBox getIndividualOffline(String name,String url){
        HBox box = new HBox();
        box.getChildren().addAll(new Label(name),new ImageView(new Image(new File(url).toURI().toString()).getUrl())) ;
        return box ;
    }

    @FXML
    public void getOnlineResult() throws Exception{
        appStage=(Stage)getonlineresults.getScene().getWindow();
        username = appStage.getTitle() ;
        storage = new DataStorage(username) ;
        String username = appStage.getTitle() ;
        String keyword = searchonlineitem.getText() ;
        String url = "https://www.bestbuy.ca/en-CA/Search/SearchResults.aspx?query="+
                keyword.replace(' ','+');
        parser = new URLParser(url) ;
        showonliner.getChildren().removeAll() ;
        ArrayList<SearchResult> results = parser.
                getParseResult(parser.getWebsiteFile()) ;
        parser.getImage(results);
        for(int i=0;i<results.size();i++){
            HBox box = getIndividual(results.get(i).getUrl(),
                    results.get(i).getImageurl()) ;
            showonliner.getChildren().add(box) ;
        }
        resultonlinepage.setContent(showonliner);
        storage = new DataStorage(username) ;
        storage.addResult(results,keyword);
        storage.storeData();
    }

    @FXML
    public void getOfflineResult() throws Exception{
        appStage=(Stage)getonlineresults.getScene().getWindow();
        username = appStage.getTitle() ;
        storage = new DataStorage(username) ;
        ArrayList<SearchResult> results = storage.viewSearchfromLocal(searchofflineitem.getText()) ;
        for(int i=0;i<results.size();i++){
            HBox box = getIndividualOffline(results.get(i).getUrl(),
                    System.getProperty("user.dir")+
                            "/DownloadedImages/"+results.get(i).getUrl().replace(" ","_")+".jpg") ;
            showoffliner.getChildren().add(box) ;
        }
        resultofflinepage.setContent(showoffliner);
    }

    @FXML
    public void logout() throws Exception{
        storage.close();
        Parent root= FXMLLoader.load(getClass().
                getResource("Home.fxml"));
        Scene scene=new Scene(root);
        appStage.setScene(scene);
        appStage.show();
    }
}
