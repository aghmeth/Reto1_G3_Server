/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.model;


/**
 *
 * @author Diego
 */
public class DaoFactory {
    
    MessageType mt;
    
    public DaoImplementation getDao(MessageType mt){
        DaoImplementation di = new DaoImplementation();
        if(mt == mt.SIGNIN_REQUEST){
            //di.UserGetSignIn();
        }else if(mt == mt.SIGNUP_REQUEST){
            //di.UserGetSignUp();
        }   
    return di;
    }
}