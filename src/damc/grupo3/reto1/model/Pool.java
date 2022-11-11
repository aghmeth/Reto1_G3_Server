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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is a connection pool that manages the connections
 *
 * @author Jessica
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
    private static Stack<Connection> poolStack = new Stack<>();

    /**
     * method to make the connection with de database
     *
     * @return conn
     * @throws ConnectionException
     * @throws NoOperativeDataBaseException
     */
    public Connection openConnection() throws ConnectionException, NoOperativeDataBaseException {
        this.configFile = ResourceBundle.getBundle("damc.grupo3.reto1.model.Config");
        this.driverBD = configFile.getString("DRIVERDB");
        this.urlDB = configFile.getString("URLDB");
        this.userDB = configFile.getString("USERDB");
        this.passDB = configFile.getString("PASSDB");

        try {
            Connection conn = DriverManager.getConnection(urlDB, userDB, passDB);
            //Devuelve la conexion para ser usada 
            return conn;
        } catch (SQLException e) {
            throw new NoOperativeDataBaseException(urlDB);
        }
    }

    /**
     * check that there is only one pool connection created
     *
     * @return pool
     */
    public static Pool getPool() {
        if (pool == null) {
            pool = new Pool();
        }

        return pool;
    }

    /**
     * We open a connection from the stack that we created previously
     * @return conn 
     * @throws TimeOutException
     */
    public Connection getConnection() throws TimeOutException {
        Connection conn = null;
        if (poolStack.size() > 0) {
            conn = poolStack.pop();
        } else {
            try {
                conn = openConnection();
            } catch (ConnectionException ex) {
                Logger.getLogger(Pool.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoOperativeDataBaseException ex) {
                Logger.getLogger(Pool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }

    /**
     * Return a connection when necessary
     * @param con
     * @throws TimeOutException 
     */
    public void devolverConexion(Connection con) throws TimeOutException {
        LOG.info("Devolver una conexion ");
        poolStack.push(con);
    }

}
