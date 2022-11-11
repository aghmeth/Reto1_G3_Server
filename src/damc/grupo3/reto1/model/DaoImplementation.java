/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.model;
import damc.grupo3.reto1.exception.PasswordErrorException;
import damc.grupo3.reto1.exception.ServerErrorException;
import damc.grupo3.reto1.exception.TimeOutException;
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
 * This class is used to manage the Database
 * @author Diego, Alejandro, Jessica y Josu
 */
public class DaoImplementation implements Sign{
        private Connection conn;
        private PreparedStatement stmt;
        private ResourceBundle configFile;
        private static Pool pool;
        private String url;
        private String user;
        private String password;
        private static final Logger LOG = Logger.getLogger(DaoImplementation.class.getName());
        //Secuencia SQL para registrar a un nuevo usuario
        //la secuencia NOW() registra la fecha del registro actual
        final String InsertDataSignUp = "INSERT INTO usuario (login, email, fullname, sttatus, privilege, passwd, lastPasswordChange) VALUES(?,?,?,?,?,?,NOW())";
        //Secuencia SQL para comprobar que el usuario existe en la tabla de ususarios
        final String DataCheck = "SELECT * FROM usuario WHERE login = ? AND passwd = ?";
        //Secuencia SQL para insertar los datos, una vez han sido comprobados a la tabla signin
        final String InsertDataSignIn = "INSERT INTO signin VALUES(?, ?)";
        
        
        
    public  DaoImplementation(){
        //Configuración estándar para conectarnos a nuestra base de datos
        this.configFile = ResourceBundle.getBundle("damc.grupo3.reto1.model.Config");
        this.url = configFile.getString("URLDB");
        this.user = configFile.getString("USERDB");
        this.password = configFile.getString("PASSDB");
        
        this.pool = pool.getPool();
     }
    
    /**
     * This method inserts to the database the user who used to sign up
     * @param user
     * @return user
     * @throws ServerErrorException
     * @throws UserAlreadyExitsException 
     */
    
    public User getExecuteSignUp(User user) throws ServerErrorException, UserAlreadyExitsException{
        try{
            ResultSet rs = null;
            Integer id;
            String login;
            String email;
            String fullname;
            Enum status = user.getStatus();
            Enum priviledge = user.getPrivilegde();
            String password;
            Date lastPasswordChange;
            conn = pool.getConnection();
            try{
                stmt = conn.prepareStatement(InsertDataSignUp);
                //Pillamos los parametros de la ventana de SignUp
                stmt.setString(1, user.getLogin());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getFullname());
                stmt.setString(4, "enabled"); // enabled
                stmt.setString(5, "user");// user
                stmt.setString(6, user.getPassword());
                stmt.executeUpdate();
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
                pool.devolverConexion(conn);
                if(rs != null){
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(DaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }catch(TimeOutException ex){
                Logger.getLogger(DaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
    /**
     * This method checks if the user is already logged and it enters the user
     * in the signin table
     * @param user
     * @return user
     * @throws PasswordErrorException
     * @throws UserNotFoundException
     * @throws ServerErrorException 
     */
    public User getExecuteSignIn(User user) throws PasswordErrorException,
            UserNotFoundException, ServerErrorException{
        try{
            ResultSet rs = null;
            Integer id;
            Date lastSign;
            conn = pool.getConnection();
            
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
                    
                    if(stmt.executeUpdate(DataCheck)>0){
                        //Almacenamos los datos en la tabla de signin
                        user.setId(rs.getInt("id"));
                        user.setLastPasswordChange(rs.getTimestamp("lastSign"));
                    }else{
                        throw new ServerErrorException("Something went wrong while inserting data");
                    }
                }else{
                    throw new UserNotFoundException("The SQL query couldn´t be execueted properly");
                }
            }catch(SQLException e){
                e.printStackTrace();
                throw new ServerErrorException("Something went wrong while inserting data");
            }finally{
                pool.devolverConexion(conn);
                if(rs != null){
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(DaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }catch(TimeOutException ex){
                Logger.getLogger(DaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
            return user;
    }
}

