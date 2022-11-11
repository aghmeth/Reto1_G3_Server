/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This exception shows a message saying that there was an error connecting with the database
 * @author Alejandro y Jessica
 */
public class ConnectionException extends Exception{
    public ConnectionException(String msg) {
        super(msg);
    }
}
