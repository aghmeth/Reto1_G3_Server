/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *This exception is used to show a message wich says the connection to the database took too long
 * @author Alejandro
 */
public class TimeOutException extends Exception{
    public TimeOutException(String msg) {
        super(msg);
    }
}
