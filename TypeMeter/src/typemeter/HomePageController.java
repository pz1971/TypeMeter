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
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
        
        speedField.setText("Speed(WPM): ");
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
        
        speedField.setText("Speed(WPM):        " + Integer.toString((int)Math.round(((double)script.length() / 4.7) / ( timeSeconds.get() / 60.0 ) )));
        accuracyField.setText("Accuracy:        " + Integer.toString((int)Math.round(100.0 - ( (double)mistake / (double)script.length() * 100.0 ) )) + "%") ;
        
        script = "";        // all the text flows and script will be erased
        text1.setText("");
        text2.setText("");
        text3.setText("");
        
        pb.setProgress(1.0);    // 100% progress achieved :D
        correct = incorrect = 0;    // reset computation
    }
    
    private void calculate(String typed){
        correct = 0;
        for(int i=0; i<typed.length(); i++){
            if(script.charAt(i) == typed.charAt(i)){
                correct++;
            }
            else{
                correctness[i] = 1 ;    // this letter typed wrong :(
                
                break;
            }
        }
        
        pb.setProgress(correct / (double)script.length()) ;
        incorrect = typed.length() - correct;
    }
    
    private void i_am_typing(){
        String typed = typeArea.getText();
        
        if(typed.equals(script)){
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            BufferedReader reader = new BufferedReader(new FileReader((new File("")).getAbsolutePath() + "/src/CurrentUser/userName.txt")) ;
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
