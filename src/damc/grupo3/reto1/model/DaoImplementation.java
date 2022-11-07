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
        //private Pool pool;
        private String url;
        private String user;
        private String password;
        private static final Logger LOG = Logger.getLogger(DaoImplementation.class.getName());
        //Secuencia SQL para registrar a un nuevo usuario
        //la secuencia NOW() registra la fecha del registro actual
        final String InsertDataSignUp = "INSERT INTO usuarios VALUES('?','?','?','?','?','?','?','NOW()')";
        //Secuencia SQL para comprobar que el usuario existe en la tabla de ususarios
        final String DataCheck = "SELECT * FROM usuarios WHERE login = ? AND passwd = ?";
        //Secuencia SQL para insertar los datos, una vez han sido comprobados a la tabla signin
        final String InsertDataSignIn = "INSERT INTO signin VALUES('?', '?')";
        
    public  DaoImplementation(){
        //Configuración estándar para conectarnos a nuestra base de datos
        this.configFile = ResourceBundle.getBundle("Reto1.properties");
        this.url = configFile.getString("URLDB");
        this.user = configFile.getString("USERDB");
        this.password = configFile.getString("PASSDB");
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
    public void getExecuteSignUp(User user) throws ServerErrorException, UserAlreadyExitsException{
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
            stmt = conn.prepareStatement(InsertDataSignUp);
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
                user.setPassword(rs.getString("passwd"));
                user.setLastPasswordChange(rs.getDate("lastPasswordChange"));
            }else{
                throw new SQLException("Something went wrong while inserting data");
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
    public void getExecuteSignIn(User user) throws PasswordErrorException, UserNotFoundException, ServerErrorException{
        ResultSet rs = null;
        Integer id;
        Date lastSign;
        this.OpenConnection();
        try{
            stmt = conn.prepareStatement(DataCheck);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            rs = stmt.executeQuery();
            
            if(rs.next()){
                stmt = conn.prepareStatement(InsertDataSignIn);
                //Pillamos los 2 parametros de la ventana de SignIn
                stmt.setInt(1, user.getId());
                stmt.setDate(2, (java.sql.Date) user.getLastPasswordChange());
                //Una vez logrados hacemos la consulta
                
                if(stmt.executeUpdate(InsertDataSignIn)>0){
                    //Almacenamos los datos en la tabla de signin
                    user.setId(rs.getInt("id"));
                    user.setLastPasswordChange(rs.getTimestamp("lastSign"));
                }else{
                    throw new SQLException("Something went wrong while inserting data");
                }
            }else{
               throw new SQLException("The SQL query couldn´t be execueted properly");
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