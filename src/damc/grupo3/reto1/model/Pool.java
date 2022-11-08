/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.model;

import damc.grupo3.reto1.exception.ConnectionException;
import damc.grupo3.reto1.exception.NoOperativeDataBaseException;
import damc.grupo3.reto1.exception.TimeOutException;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Logger;

/**
 *
 * @author 2dam
 */
public class Pool {
     private static final Logger LOG = Logger.getLogger(Pool.class.getName());
    // fichero config.properties
    private ResourceBundle configFile;
    private String driverBD;
    private String urlDB;
    private String userDB;
    private String passDB;

    private static Pool pool;
    // en este tipo de colecion
    private Stack<Connection> poolStack;

    /**
     * Metodo para hacer la conexion con la base de datos
     *
     * 
     */
    public void openConnection() throws ConnectionException, NoOperativeDataBaseException {
        this.configFile = ResourceBundle.getBundle("model.Reto1");
        this.driverBD = configFile.getString("DRIVERDB");
        this.urlDB = configFile.getString("URLDB");
        this.userDB = configFile.getString("USERDB");
        this.passDB = configFile.getString("PASSDB");
        
        try {
            Connection conn = DriverManager.getConnection(urlDB, userDB, passDB);
            //push añadir una conexion a la pila 
            poolStack.push(conn);
        } catch (SQLException e) {
            throw new NoOperativeDataBaseException(urlDB);
        }
    }
    /**
     * creamos la colecion para almacenar las conexiones
     * Lanzamos excepciones
     *
     */
    public void createStackPool() throws SQLException, NoOperativeDataBaseException, ConnectionException {
        //Creamos  un Stack donde se alcenaran las conexiones.
        poolStack = new Stack<>();
        this.openConnection();
    }
/**
 * 
 * @return controla que solo haya un pool conection creado
 */
    public static Pool poolInstance(){
        if (pool == null) {
            pool = new Pool();
            return pool;
        } else {
           return pool;
        }
    }
    /**
     * Abrimos una conexion del stack que hemos creado anteriormente
     * 
     */
    public Connection getConnection() throws TimeOutException {
        Connection conn = null;
        if (poolStack.size() > 0) {
            conn = poolStack.pop();
        }
        return conn;
    }
    /**
     *cerrar una conexion cuando sea necesario
     *
     */
    public void closeConnection(Connection con) throws TimeOutException {
        LOG.info("Cerrar una conexion ");
        poolStack.push(con);
    }
    
}
