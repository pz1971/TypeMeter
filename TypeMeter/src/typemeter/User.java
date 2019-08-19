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
    private String user_name, password;
    
    User(String user_name, String password){
        this.user_name = user_name;
        this.password = password;
        
        String pathToUser = System.getProperty("user.dir") + "/src/UsersFolder" ;
        File dir = new File(pathToUser + "/" + user_name);  // created a file for a user
        dir.mkdir();    // now that file is a directory
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathToUser + "/" + user_name + "/password.txt"));
            writer.write(password);
            writer.close();
        }catch(IOException e){
                System.out.println(e.toString());
        }
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }
    
}
