/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typemeter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Parvez
 */
public class User {
    
    User(String user_name, String password){
        
        String pathToUser = (new File("")).getAbsolutePath() + "/src/UsersFolder" ;
        
        File dir = new File(pathToUser + "/" + user_name);  // created a file for a user
        dir.mkdir();    // now that file is a directory
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathToUser + "/" + user_name + "/password.txt"));
            writer.write(password);     // store the password
            writer.close();
            writer = new BufferedWriter(new FileWriter(pathToUser + "/" + user_name + "/bestSpeed.txt"));
            writer.write("0");        // initial best speed is 0
            writer.close();
            writer = new BufferedWriter(new FileWriter(pathToUser + "/" + user_name + "/history.txt")); // empty file to store histories
            writer.close();
        }catch(IOException e){
            System.out.println(e.toString());
        }
    }
    
}
