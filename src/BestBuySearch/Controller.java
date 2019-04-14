package BestBuySearch;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Controller {
    @FXML
    TextField username ;
    @FXML
    PasswordField password ;
    @FXML
    Button login ;
    @FXML
    Button guest ;
    Stage appStage;
    Parent root;

    @FXML protected void setLogin(ActionEvent event) throws Exception{
        String uname ;
        String pword ;
        uname = username.getText() ;
        pword = password.getText();
        if(event.getSource()==login)
        {
            if(!uname.isEmpty() & !pword.isEmpty()){
                BufferedReader reader = new BufferedReader(new FileReader(new File("users.txt"))) ;
                String data ;
                while ((data=reader.readLine())!=null){
                    String para[] = data.split("_") ;
                    if(para[0].equals(uname) & para[1].equals(pword)){
                        appStage=(Stage)login.getScene().getWindow();
                        root=FXMLLoader.load(getClass().getResource("UserPanel.fxml"));
                        Scene scene=new Scene(root);
                        appStage.setTitle(uname);
                        appStage.setScene(scene);
                        appStage.show();
                        break ;
                    }
                }
            }
        }
    }

    @FXML public void loadGuest() throws Exception{
        appStage=(Stage)guest.getScene().getWindow();
        root=FXMLLoader.load(getClass().getResource("Guest.fxml"));
        Scene scene=new Scene(root);
        appStage.setScene(scene);
        appStage.show();
    }
}
