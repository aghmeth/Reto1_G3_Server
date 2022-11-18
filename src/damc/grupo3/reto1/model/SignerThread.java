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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to perform the information given by the Sockets 
 * and call the method of the factory to register the user
 * @author Diego, Alejandro y Jessica
 */
public class SignerThread extends Thread {
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private Socket s = null;
    private Sign sign;
    private MessageType messT;
    private Encapsulator encap = null;
    private User user = null;
    
    public SignerThread(){
    
    }
    
    public SignerThread(Socket s) {
        this.s = s;
    }
    
     /**
     * This method manages de information received and makes an action 
     * depending of the information given
     * 
     */
    @Override
    public void run(){
        int cont = 0;
        try{
            ois = new ObjectInputStream(s.getInputStream());
            //LLamaos al método de la factoria por cada registro que hace el cliente
            DaoFactory daofact = new DaoFactory();
            sign = daofact.getDao();
            //Leemos los datos del encapsulador
            encap = (Encapsulator) ois.readObject();
            
           
                //Le decimos al hilo la accion que tiene que hacer dependiendo del mensaje que recive
                switch(encap.getMessage()){
                    case SIGNIN_REQUEST:
                        user = sign.getExecuteSignIn(encap.getUser());
                                //Si no da fallos guardamos el usuario y estableciendo el mensaje
                                encap.setUser(user);
                                encap.setMessage(MessageType.OK_RESPONSE);
                    
                        break;
                    case SIGNUP_REQUEST:
                        sign.getExecuteSignUp(encap.getUser());
                        encap.setUser(user);
                        encap.setMessage(MessageType.OK_RESPONSE);
                       
                        break;
                     
                }
            sleep(60000);
        }catch(IOException e){
            encap.setMessage(MessageType.ERROR_RESPONSE);
                Logger.getLogger(SignerThread.class.getName()).log(Level.SEVERE, null, e);
        }catch (ClassNotFoundException e){
            encap.setMessage(MessageType.ERROR_RESPONSE);
                Logger.getLogger(SignerThread.class.getName()).log(Level.SEVERE, null, e);
        }catch (UserNotFoundException e){
                 encap.setMessage(MessageType.USER_NOT_FOUND_RESPONSE);
                Logger.getLogger(SignerThread.class.getName()).log(Level.SEVERE, null, e);
        }catch (PasswordErrorException e){
            encap.setMessage(MessageType.PASSWORD_ERROR_RESPONSE);
                Logger.getLogger(SignerThread.class.getName()).log(Level.SEVERE, null, e);
        }catch(ServerErrorException e){
            encap.setMessage(MessageType.ERROR_RESPONSE);
            Logger.getLogger(SignerThread.class.getName()).log(Level.SEVERE, null, e);
        }catch(UserAlreadyExitsException e){
            encap.setMessage(MessageType.USER_ALREADY_EXISTS_RESPONSE);
            Logger.getLogger(SignerThread.class.getName()).log(Level.SEVERE, null, e);
        } catch (InterruptedException ex) {
            Logger.getLogger(SignerThread.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(encap);
                SignerServer.borrarCliente(this);
                ois.close();
                oos.close();
                s.close();
            } catch (IOException ex) {
                Logger.getLogger(SignerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

    

