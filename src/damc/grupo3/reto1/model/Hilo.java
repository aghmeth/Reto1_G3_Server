/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.model;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class Hilo extends Thread {
    private ObjectInputStream ois = null;
    private Socket s = null;
    private Message mess;
    private MessageType messT;
    private User user;
    

    public Hilo(Socket s) {
        this.s = s;
    }
    public void Run(){
        int cont = 0;
        try{
            ois = new ObjectInputStream(s.getInputStream());
            cont++;
            do{
                mess = (Message) ois.readObject();
                messT = (MessageType) ois.readObject();
                user = (User) ois.readObject();
            }while(cont <= 10);
                ois.close();
                s.close();
        }catch(IOException e){
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, e);
        }catch (ClassNotFoundException e){
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
