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
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Parvez
 */
public class HomePageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    String projectLocation = (new File("")).getAbsolutePath() + "/src/" ;
    
    private static final int STARTTIME = 0;     // timer always starts from 0 seconds... okay?!
    private Timeline timeline;      // well, this is the timeline... um, that's it :D
    private final IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);   // I don't know what the hell is this, just stacksoverflow'ed it :P
    
    int correctness[] = new int[1000] ;
    
    @FXML
    Text text1, text2, text3; // text1 = the portion already typed correctly, text2 = typed but not correct, text3 = not typed yet
  
    @FXML
    TextFlow typeScript ;
    
    @FXML
    Button startTypingButton;
    
    @FXML
    TextArea typeArea;
    
    @FXML
    ProgressBar pb;
    
    @FXML
    TextField speedField, accuracyField;
    
    @FXML
    Label timerField, timerLabel, userNameFiled ;
    
    String script;
    int correct = 0, incorrect = 0;
    
    private void updateTime() {     // updates the time each seconds
        // increment seconds
        int seconds = timeSeconds.get();
        timeSeconds.set(seconds+1);
        if(timeSeconds.get() >= 180){   // 3 minutes are more than enough, I think :)
            times_up() ;
        }
    }
    
    @FXML
    public void startTypingButtonActon(ActionEvent event){
        
        script = (new ScriptGenerator()).generate();    // generates a random script
        text1.setText("");      // already typed correctly
        text2.setText("");      // typed but not correct
        text3.setText(script);  // remaining protion of the script not yet 'd been typed
        
        startTypingButton.setVisible(false);    // button disappears :D
        typeArea.setEditable(true) ;            // enabled typing
        
        correct = incorrect = 0;
        pb.setProgress(0.0) ;       // 0% has been typed
        typeArea.setText("");
        
        speedField.setText("Speed(WPM): "); // default texts
        accuracyField.setText("Accuracy: ") ;
        
        // reset the timer
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> updateTime())); 
        timeline.setCycleCount(Animation.INDEFINITE); // repeat over and over again
        timeSeconds.set(STARTTIME);
        timeline.play();
    }
    
    private void endOfScript(){ // when user finishes typing the current script
        startTypingButton.setVisible(true);     // you can type again... :)
        typeArea.setEditable(false) ;
        
        timeline.pause() ;  // pause the timeline :D
        int mistake = 0 ;   // number of mistakes
        
        for(int i = 0 ; i < script.length() ; i++){
            if(correctness[i] == 1){
                mistake++ ;   // mistake found here
                correctness[i] = 0 ;    // reset status of this index
            }
        }
        
        int speed = (int)Math.round(((double)script.length() / 4.7) / ( timeSeconds.get() / 60.0 ) ) ;  // speed in wpm
        int accuracy = (int)Math.round(100.0 - ( (double)mistake / (double)script.length() * 100.0 ) ) ;    // accuracy out of 100%
        
        speedField.setText("Speed(WPM):        " + Integer.toString(speed));
        accuracyField.setText("Accuracy:        " + Integer.toString(accuracy) + "%") ;
        
        java.util.Date date=new java.util.Date();  // Current date and time
        String dateAndTime = date.toString() ;  // converting to string
        
        updateUserProfile(speed, accuracy, dateAndTime) ; // update the profile of the user
        
        script = "";        // all the text flows and script will be erased
        text1.setText("");
        text2.setText("");
        text3.setText("");
        
        pb.setProgress(1.0);    // 100% progress achieved :D
        correct = incorrect = 0;    // reset computation
    }
    
    private void updateUserProfile(final int speed, final int accuracy, final String dateAndTime){
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
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(projectLocation + "/UsersFolder/" + currentUserName + "/history.txt")) ;
            writer.write(dateAndTime + " , Speed = " + speed + " WPM, Accuracy = " + accuracy + "%\n" + temp) ; // places the new performance at the top of the history
            writer.close() ;
            
            reader = new BufferedReader(new FileReader(projectLocation + "/UsersFolder/" + currentUserName + "/bestSpeed.txt")) ;
            int best = Integer.parseInt(reader.readLine()) ;
            reader.close() ;
            
            if(best < speed)    // new speed is the best
                best = speed ;
            
            writer = new BufferedWriter(new FileWriter(projectLocation + "/UsersFolder/" + currentUserName + "/bestSpeed.txt")) ;
            writer.write(Integer.toString(best)) ;   // updating the best...
            writer.close() ;
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    private void times_up(){
        startTypingButton.setVisible(true);     // you can type again... :)
        typeArea.setEditable(false) ;
        
        timeline.pause() ;  // pause the timeline :D
        
        speedField.setText("Speed(WPM):        " + 0);      // speed 0 ;
        accuracyField.setText("Accuracy:        " + 0 + "%") ;  // accuracy 0 ;
        
        script = "";        // all the text flows and script will be erased
        text1.setText("");
        text2.setText("\n\nYou took too much time! Please concentrate and try again.");
        text3.setText("");
        
        pb.setProgress(0.0);    // reset progress bar
    }
    
    private void calculate(String typed){
        correct = 0;
        for(int i=0; i<typed.length(); i++){
            if(script.charAt(i) == typed.charAt(i)){    // typed correctly
                correct++;
            }
            else{
                correctness[i] = 1 ;    // this letter typed wrong :(
                
                break;
            }
        }
        
        pb.setProgress(correct / (double)script.length()) ; // update progress bar
        incorrect = typed.length() - correct;
    }
    
    private void i_am_typing(){
        String typed = typeArea.getText();
        
        if(typed.equals(script)){   // completed typing
            endOfScript();
        }
        else{
            if(typed.length() > script.length()){
                typeArea.setText(typed.substring(0, script.length())) ;     // can't type more than required... :)
            }
            else{
                calculate(typed);   
                
                text1.setText(script.substring(0, correct)) ;
                text2.setText(script.substring(correct, correct + incorrect));
                text3.setText(script.substring(correct + incorrect, script.length()));
            }
        }
    }
    
    // option section
    @FXML
    Button aboutUsButton, howToButton, exitButton ;
    
    @FXML
    public void exitButtonAction(ActionEvent event){
        ((Stage) exitButton.getScene().getWindow()).close() ;
    }
    
    @FXML
    public void howToButtonAction(ActionEvent event){
        try{
            Stage st = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("HowToUse.fxml"));
            Scene sc = new Scene(root);
            st.setTitle("TypeMeter");
            st.getIcons().add(new Image("/Images/icon.png"));
            st.setScene(sc);
            st.show();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    @FXML
    public void aboutUsButtonAction(ActionEvent event){
        try{
            Stage st = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AboutUs.fxml"));
            Scene sc = new Scene(root);
            st.setTitle("TypeMeter");
            st.getIcons().add(new Image("/Images/icon.png"));
            st.setScene(sc);
            st.show();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    // switch user
    Button switchUserButton ;
    // profile section
    public void switchUserButtonButtonAction(ActionEvent event){
        try{        // opens login page
            Stage st = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
            Scene sc = new Scene(root);
            st.setTitle("TypeMeter");
            st.getIcons().add(new Image("/Images/icon.png"));
            st.setScene(sc);
            st.show();
        }catch(Exception e){
            System.out.println(e.toString());
        }
        
        exitButtonAction(event) ;   // closes the current window calling exit button action
    }
    
    @FXML
    Button refreshButton, historyButton, addScriptButton ;
    @FXML
    Label profAvgSpeed, profAccuracy, profBestSpeed ;
    @FXML
    public void refreshButtonAction(ActionEvent event){
        try{
            // getting the name of current user
            BufferedReader reader = new BufferedReader(new FileReader(projectLocation + "/CurrentUser/userName.txt")) ;
            String currentUserName = reader.readLine() ;       // getting the name of current User
            reader.close() ;
            // reading the best speed of current user
            reader = new BufferedReader(new FileReader(projectLocation + "UsersFolder/" + currentUserName + "/bestSpeed.txt")) ;
            profBestSpeed.setText(Integer.parseInt(reader.readLine()) + " WPM");    // ready to show
            reader.close() ;
            // processing the avg speed and accuracy...
            
            int speed = 0, accuracy = 0, n = 0 ;
            
            reader = new BufferedReader(new FileReader(projectLocation + "UsersFolder/" + currentUserName + "/history.txt")) ;
            String line ;
            
            while((line = reader.readLine()) != null){
                String str = line.substring(39, 40) ;
                if(line.charAt(40) != ' '){
                    str = str + line.substring(40, 41) ;
                    if(line.charAt(41) != ' ')
                        str = str + line.substring(41, 42) ;                    
                }
                
                speed += Integer.parseInt(str) ;
                
                str = line.substring(58,59) ;
                if(line.charAt(59) != '%'){
                    str = str + line.substring(59, 60) ;
                    if(line.charAt(60) != '%')
                        str = str + line.substring(60, 61) ;
                }
                
                accuracy += Integer.parseInt(str) ;
                
                n++ ;
                if(n == 10)
                    break ;
            }
            
            profAvgSpeed.setText(Math.round((double)speed / (double) n) + " WPM") ;
            profAccuracy.setText(Math.round((double)accuracy / (double) n) + " %") ;
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    @FXML
    public void historyButtonAction(ActionEvent event){     // opens a new window to show the previous records
        try{
            Stage st = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("UserHistory.fxml"));
                    Scene sc = new Scene(root);
                    st.setTitle("TypeMeter");
                    st.getIcons().add(new Image("/Images/icon.png"));
                    st.setScene(sc);
                    st.show();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    @FXML
    public void addScriptButtonAction(ActionEvent event){
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            BufferedReader reader = new BufferedReader(new FileReader(projectLocation + "/CurrentUser/userName.txt")) ;
            userNameFiled.setText(reader.readLine()) ;  // shows the name of current user...
        }catch(Exception e){
            System.out.println(e.toString());
        }
        
        text1.setFill(Color.GREEN); 
        text2.setFill(Color.RED);
        typeArea.setEditable(false) ;
        
        timerLabel.textProperty().bind(timeSeconds.asString());  //   for animation over time
        
        typeArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                i_am_typing();
            }
        });
    }
    
}
