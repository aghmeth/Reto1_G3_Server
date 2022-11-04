/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import damc.grupo3.reto1.model.Message;
/**
 *
 * @author Ale
 */
public class SignerThread extends Thread{
    
    private final Socket skCliente;
    
    public SignerThread(Socket skCliente) {
        this.skCliente = skCliente;
    }
    
    public synchronized void run() {
        boolean terminar = true;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        
        try {
            ois = new ObjectInputStream(skCliente.getInputStream());
            Message m = (Message) ois.readObject();
            m.getUser();
            m.getMessageType();

            DaoFactory df = new DaoFactory();
            
            
        } catch (IOException ex) {
            Logger.getLogger(SignerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
