/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Diego/Alejandro
 */
public class DaoImplementation {
        private Connection conn;
        private PreparedStatement stmt;
        private ResourceBundle configFile;
        private String url;
        private String user;
        private String password;
        private final String UserSignUp = "INSERT INTO usuarios VALUES('user.getId()','user.getLogin()','user.getEmail()','user.getFullname()'"
                + ",'user.getStatus()','user.getPriviledge()','user.getPassword()','user.getLastPasswordChange()')";
        private final String UserSignIn = "SELECT * FROM signin WHERE EXISTS (SELECT * FROM sigin WHERE "
                + "sigin.id = 'user.getId()' AND sigin.lastSignin = 'getLastPasswordChange()')";
        
    public  DaoImplementation(){
        this.configFile = ResourceBundle.getBundle("Reto1.properties");
        this.url = configFile.getString("Conn");
        this.user = configFile.getString("DBUser");
        this.password = configFile.getString("DBPass");
     }
    
    public void OpenConnection() {
        try{
            conn = DriverManager.getConnection(url, user, password);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void CloseConnection(){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void UserGetSignUp(){
        ResultSet rs = null;
        int id;
        String login;
        String email;
        String fullname;
        int status;
        int priviledge;
        String password;
        Date lastPasswordChange;
        
        this.OpenConnection();
        try{
            stmt = conn.prepareStatement(UserSignUp);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                id = rs.getInt(1);
                login = rs.getString(1);
                email = rs.getString(1);
                fullname = rs.getString(1);
                status = rs.getByte(1);
                priviledge = rs.getInt(1);
                password = rs.getString(1);
                lastPasswordChange = rs.getDate(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            CloseConnection();
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public void UserGetSignIn(){
        ResultSet rs = null;
        int id;
        Date lastSign;
        
        this.OpenConnection();
        try{
            stmt = conn.prepareStatement(UserSignIn);
            rs = stmt.executeQuery(UserSignIn);
            if(rs.next()){
                id = rs.getInt(1);
                lastSign = rs.getDate(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            CloseConnection();
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}