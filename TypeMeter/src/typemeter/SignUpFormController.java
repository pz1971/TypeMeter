/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typemeter;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Parvez
 */
public class SignUpFormController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password, confirmPassword;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Button cancelButton, doneButton;
    @FXML
    private Label signUpStatus;
    
    @FXML
    private void cancelButtonAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void doneButtonAction(ActionEvent event){
        String name, pass, con_pass;
        name = userName.getText();
        pass = password.getText();
        con_pass = confirmPassword.getText();
        
        boolean allOk = true;
        if(!pass.isEmpty() && !pass.equals(con_pass)){         // if password and confirm password are different
            signUpStatus.setText("Passwords not matched...");
            allOk = false;          // something was wrong in filling up the form
        }
        
        if(allOk){
            String pathToUser = System.getProperty("user.dir") + "/src/UsersFolder" ;
            File [] allUsers = (new File(pathToUser)).listFiles();
            
            for(File aUser : allUsers){     // iterate through all previous users record and check if this name has already been taken or not
                if(aUser.getName().equals(name)){   // this user name not available
                    allOk = false;
                    signUpStatus.setText("This user Name is not available...");
                    break;
                }
            }
        }
        
        if(!checkBox.isSelected()){ // not agree in terms and conditions.. so, we won't allow him to continue. :)
            allOk = false;
        }
        
        if(allOk){  // no problem found
            new User(name, pass);       // making an entry :D
            cancelButtonAction(event);  // when registration is done, it should work like a cancel button. :)
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
