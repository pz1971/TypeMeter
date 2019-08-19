/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typemeter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Parvez
 */
public class LoginPageController implements Initializable {
    
    @FXML
    private TextField userNameBox;
    @FXML
    private PasswordField passwordBox;
    @FXML
    private Label loginStatus;
    
    @FXML
    private void loginButtonAction(ActionEvent event) {
        String userName = userNameBox.getText();
        String pass = passwordBox.getText();
        
        String pathToUser = System.getProperty("user.dir") + "/src/UsersFolder" ;
        File dir = new File(pathToUser);     // project directory + the folder where users infos are recorded
        File [] listOfUsers = dir.listFiles();  // all the folders named on behalf of the userNames...
        
        boolean userFound = false;
        for (File aUser : listOfUsers){     // iterate through all the users to find that certain one!
            if(aUser.getName().equals(userName)){       // if this user exists
                userFound = true;
                break;
            }
        }
        
        if(!userFound){     // the user name not found in the record
            loginStatus.setText("Invalid user_name or password... Please Try again or register.");
        }
        else{
            BufferedReader reader;
            boolean passwordMatched = false;
            try{
                reader = new BufferedReader(new FileReader(pathToUser + "/" + userName + "/password.txt")); // opens the file where the password is stored
                if(reader.readLine().equals(pass)){
                    passwordMatched = true;
                }
            }
            catch(IOException e){
                System.out.println(e.toString());
            }
            
            if(!passwordMatched){       // password not matched
                loginStatus.setText("Invalid user_name or password... Please Try again or register.");
            }
            else{
                loginStatus.setText("Welcome!");
                // home opens...
            }
        }
    }
    
    @FXML
    private void newUserButtonAction(ActionEvent event) {
        
        try {       // new user registration form is being opened...
            Stage st = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("SignUpForm.fxml"));
            Scene sc = new Scene(root);
            st.setTitle("TypeMeter Sign up");
            st.getIcons().add(new Image("/Images/icon.png"));
            st.setScene(sc);
            st.show();
        }
        catch(IOException e){
            System.out.println(e.toString());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
