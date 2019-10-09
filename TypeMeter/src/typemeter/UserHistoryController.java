/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typemeter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
public class UserHistoryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    final String projectLocation = (new File("")).getAbsolutePath() + "/src/" ;
    
    @FXML
    TextArea historyText ;
    @FXML
    Button doneButton ;
    
    @FXML
    public void doneButtonAction(ActionEvent event){
        ((Stage) doneButton.getScene().getWindow()).close() ;   // exit the window
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        historyText.setEditable(false);
        
        String currentUserName ;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(projectLocation + "/CurrentUser/userName.txt")) ;
            currentUserName = reader.readLine() ;       // getting the name of current User
            reader.close() ;
            
            reader = new BufferedReader(new FileReader(projectLocation + "/UsersFolder/" + currentUserName + "/history.txt")) ;
            String temp = "", line ;
            while((line = reader.readLine()) != null){  // getting all previous history
                temp = temp + line + "\n" ;
            }
            reader.close() ;
            historyText.setText(temp) ; // showing history in text
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }    
    
}
