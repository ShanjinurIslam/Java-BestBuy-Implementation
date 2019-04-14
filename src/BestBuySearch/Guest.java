package BestBuySearch;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Guest {
    private URLParser parser ;
    @FXML
    TextField searchitem ;
    @FXML
    Button logout ;
    @FXML
    Button getresults ;
    @FXML
    VBox showr ;
    @FXML
    ScrollPane resultpage ;

    HBox getIndividual(String name,String url){
        HBox box = new HBox();
        box.getChildren().addAll(new Label(name),new ImageView(url)) ;
        return box ;
    }

    @FXML
    public void getResult(ActionEvent event) throws Exception{
        String url = "https://www.bestbuy.ca/en-CA/Search/SearchResults.aspx?query="+searchitem.getText().replace(' ','+');
        parser = new URLParser(url) ;
        showr.getChildren().removeAll() ;
        ArrayList<SearchResult> results = parser.getParseResult(parser.getWebsiteFile()) ;
        for(int i=0;i<results.size();i++){
            System.out.println(results.get(i).getUrl()+ "\t" + results.get(i).getImageurl());
            HBox box = getIndividual(results.get(i).getUrl(),results.get(i).getImageurl()) ;
            showr.getChildren().add(box) ;
        }
        resultpage.setContent(showr);
    }

    @FXML
    public void setLogout() throws Exception{
        Stage appStage =(Stage)logout.getScene().getWindow();
        Parent root= FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene=new Scene(root);
        appStage.setScene(scene);
        appStage.show();
    }
}
