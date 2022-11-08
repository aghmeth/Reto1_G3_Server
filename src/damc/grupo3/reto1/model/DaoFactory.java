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
    
    private MessageType mst;
    
    public DaoImplementation getDao(){
        DaoImplementation di = new DaoImplementation();
        if(mst == mst.SIGNIN_REQUEST){
            //di.UserGetSignIn();
        }else if(mst == mst.SIGNUP_REQUEST){
            //di.UserGetSignUp();
        }   
    return di;
    }
}