/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.model;

/**
 * Reads the parameters of the 'SignerServer' and depending on the type of 
 * message executes one of the mehods of the dao implementation
 * @author Diego
 */
public class DaoFactory {
   
    /**
     * Method that returns one of the methods that we have in the implementation
     * @return sign
     */
    public Sign getDao() {
        Sign sign;
        sign = new DaoImplementation();
        return sign;
    }
}
