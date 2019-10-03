/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typemeter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Parvez
 */
public class AboutUsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    Hyperlink linkedInPZ ;
    @FXML
    public void linkedInPZAction(ActionEvent event){
        (new TypeMeter()).getHostServices().showDocument("https://www.linkedin.com/in/pz1971/");
    }
    
    @FXML
    Hyperlink GithubPZ ;
    @FXML
    public void GithubPZAction(ActionEvent event){
        (new TypeMeter()).getHostServices().showDocument("https://github.com/pz1971/");
    }
    
    @FXML
    Hyperlink stopstalkPZ ;
    @FXML
    public void stopstalkPZAction(ActionEvent event){
        (new TypeMeter()).getHostServices().showDocument("https://www.stopstalk.com/user/profile/pz1971");
    }
    
    @FXML
    Button doneButton, githubRepoButton ;
    
    @FXML
    public void githubRepoButtonAction(ActionEvent event){
        (new TypeMeter()).getHostServices().showDocument("https://github.com/pz1971/TypeMeter");
    }
    
    @FXML
    public void doneButtonAction(ActionEvent event){
        ((Stage) doneButton.getScene().getWindow()).close() ;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
