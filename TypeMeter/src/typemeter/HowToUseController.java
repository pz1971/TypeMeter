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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Parvez
 */
public class HowToUseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    TextArea insText ;
    
    @FXML
    Button doneButton ;
    
    @FXML
    public void doneButtonAction(ActionEvent event){
        ((Stage) doneButton.getScene().getWindow()).close() ;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        insText.setEditable(false) ;
    }    
    
}
