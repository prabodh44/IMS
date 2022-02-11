/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Helper {
    
    // TODO: create a single instance of the Connection object
    public static Connection conn = null;
    public static void initialize(){
        // method returns the connection object
        String url = "jdbc:sqlite:maindb.db";
        conn = null;
        try{
            
            conn = DriverManager.getConnection(url);
            System.out.println("The SQL connection has been opened");
            //System.out.println("Connection is created");
        }catch(SQLException e){
            logger("initialize", e);
        }
    }
    
    public static void closeSQLConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("The SQLConnection has been closed");
            } else {
                System.out.println("The connection has not been initialized");
            }
        } catch (SQLException e) {
            Helper.logger("Exception while closing connection", e);
        }
    }
    
    public static void logger(String functionName, Exception e){
        System.out.println(functionName + "() " + e.getMessage());
    }
}


   
