/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typemeter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
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
    Button startTypingButton;
    
    @FXML
    TextArea typeArea;
    
    @FXML
    ProgressBar pb;
    
    String script;
    int correct = 0, incorrect = 0;
    
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
    }
    
    private void endOfScript(){ // when user finishes typing the current script
        startTypingButton.setVisible(true);     // you can type again... :)
        typeArea.setEditable(false) ;
        
        script = "";
        text1.setText("");
        text2.setText("");
        text3.setText("");
        
        pb.setProgress(1.0);
        correct = incorrect = 0;
    }
    
    private void calculate(String typed){
        correct = 0;
        for(int i=0; i<typed.length(); i++){
            if(script.charAt(i) == typed.charAt(i)){
                correct++;
            }
            else
                break;
        }
        
        pb.setProgress(correct / (double)script.length()) ;
        incorrect = typed.length() - correct;
    }
    
    private void i_am_typing(){
        String typed = typeArea.getText();
        
        if(typed.equals(script)){
            endOfScript();
            return;
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
        text1.setFill(Color.GREEN);
        text2.setFill(Color.RED);
        typeArea.setEditable(false) ;
        
        typeArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                i_am_typing();
            }
        });
    }
    
}
