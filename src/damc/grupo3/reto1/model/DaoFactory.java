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

/**
 *
 * @author Diego
 */
public class DaoFactory {
    private MessageType mst;
    private User user;
    public DaoImplementation getDao() throws PasswordErrorException, UserNotFoundException, ServerErrorException, UserAlreadyExitsException{
        DaoImplementation di = new DaoImplementation();
        if(mst == mst.SIGNIN_REQUEST){
            di.getExecuteSignIn(user);
        }else if(mst == mst.SIGNUP_REQUEST){
            di.getExecuteSignUp(user);
        }   
    return di;
    }
}
