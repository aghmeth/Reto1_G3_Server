/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.model;
import damc.grupo3.reto1.exception.PasswordErrorException;
import damc.grupo3.reto1.exception.ServerErrorException;
import damc.grupo3.reto1.exception.UserAlreadyExitsException;
import damc.grupo3.reto1.exception.UserNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Diego y Alejandro
 */
public class DaoImplementation {
        private Connection conn;
        private PreparedStatement stmt;
        private ResourceBundle configFile;
        private Pool pool;
        private String url;
        private String user;
        private String password;
        //Secuencia SQL para registrar a un nuevo usuario
        //la secuencia NOW() registra la fecha del registro actual
        final String UserSignUp = "INSERT INTO usuarios VALUES('?','?','?','?'"
                + ",'?','?','?','NOW()')";
        //Secuencia SQL para ver si un usuario está registrado ya
        final String UserSignIn = "SELECT * FROM signin WHERE login = ? AND password = ?";
        
    public  DaoImplementation(){
        //Configuración estándar para conectarnos a nuestra base de datos
        this.configFile = ResourceBundle.getBundle("Reto1.properties");
        this.url = configFile.getString("Conn");
        this.user = configFile.getString("DBUser");
        this.password = configFile.getString("DBPass");
     }
    
    /*Public Pool getPool(){
        Aquí se llamaria al pool
    }*/
    
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
    public void UserGetSignUp(User user) throws ServerErrorException, UserAlreadyExitsException{
        ResultSet rs = null;
        Integer id;
        String login;
        String email;
        String fullname;
        Enum status = user.getStatus();
        Enum priviledge = user.getPrivilegde();
        String password;
        Date lastPasswordChange;
        this.OpenConnection();
        try{
            stmt = conn.prepareStatement(UserSignUp);
            //Pillamos los parametros de la ventana de SignUp
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getFullname());
            stmt.setInt(4, status.ordinal());
            stmt.setInt(5, priviledge.ordinal());
            stmt.setString(6, user.getPassword());
            rs = stmt.executeQuery();
            if(rs.next()){
                //Almacenamos los datos en la tabla de usuarios
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setEmail(rs.getString("email"));
                user.setFullname(rs.getString("fullname"));
                user.setStatus(UserStatus.values()[rs.getInt("status")]);
                user.setPrivilegde(UserPrivilege.values()[rs.getInt("priviledge")]);
                user.setPassword(rs.getString("password"));
                user.setLastPasswordChange(rs.getDate("lastPasswordChange"));
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
    public void UserGetSignIn(User user) throws PasswordErrorException, UserNotFoundException, ServerErrorException{
        ResultSet rs = null;
        Integer id;
        Date lastSign;
        this.OpenConnection();
        try{
            stmt = conn.prepareStatement(UserSignIn);
            //Pillamos los 2 parametros de la ventana de SignIn
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            //Una vez logrados hacemos la consulta
            rs = stmt.executeQuery(UserSignIn);
            if(rs.next()){
                //Almacenamos los datos en la tabla de signin
                user.setId(rs.getInt("id"));
                user.setLastPasswordChange(rs.getTimestamp("lastSign"));
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