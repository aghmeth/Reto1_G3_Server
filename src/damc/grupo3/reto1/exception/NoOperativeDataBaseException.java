/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *This exception is ussed to show a message wich says the database is no operative
 * @author Alejandro
 */
public class NoOperativeDataBaseException extends Exception{
    public NoOperativeDataBaseException(String msg) {
        super(msg);
    }
}
