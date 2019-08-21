/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typemeter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
/**
 *
 * @author Parvez
 */
public class ScriptGenerator {
    public String generate(){
        int idx = (new Random()).nextInt(31);
        String filePath = ( System.getProperty("user.dir") + "/src/Scripts/" + Integer.toString(idx) + ".txt" );
        String ret = "";
        
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            ret = reader.readLine();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return ret;
    }
}
