/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.exception;

/**
 *
 * @author 2dam
 */
public class MaxUsersException extends Exception {

    /**
     * Creates a new instance of <code>MaxUsersException</code> without detail
     * message.
     */
    public MaxUsersException() {
    }

    /**
     * Constructs an instance of <code>MaxUsersException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MaxUsersException(String msg) {
        super(msg);
    }
}
