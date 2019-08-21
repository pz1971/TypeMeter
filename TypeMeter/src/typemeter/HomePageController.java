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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author Parvez
 */
public class HomePageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    Text text1, text2, text3; // text1 = the portion already typed correctly, text2 = typed but not correct, text3 = not typed yet
  
    @FXML
    TextFlow typeScript ;
    
    @FXML
    public void StartTypingButtonActon(ActionEvent event){
        String script = (new ScriptGenerator()).generate();
        text1.setText(script);
        text2.setText("");
        text3.setText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        text1.setFill(Color.GREEN);
        text2.setFill(Color.RED);
    }
    
}
