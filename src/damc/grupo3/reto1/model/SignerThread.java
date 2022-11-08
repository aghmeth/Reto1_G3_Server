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
import java.util.ArrayList;

/**
 * This class is a Thread, is used to read the Message and call the Factory to
 * start introducing User to the database, then, the Thread recieves a response
 * message wich sends to the Signer Client
 *
 * @author Alejandro
 */
public class SignerThread extends Thread {

    private final Socket skClient;
    MessageType mt;
    User user;

    public SignerThread(Socket skClient) {
        this.skClient = skClient;
    }

    public void run() {
        boolean terminar = true;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(skClient.getInputStream());
            Message m = (Message) ois.readObject();
            user = m.getUser();
            m.getMessageType();
            System.out.println("Usuario es: " + m.getUser());
            //DaoFactory df = new DaoFactory();
            //df.getDao(mt);

            oos = new ObjectOutputStream(skClient.getOutputStream());
            m.setMessageType(MessageType.MAX_USER);
            //m.getMessageType();
            oos.writeObject(m);

        } catch (IOException ex) {
            Logger.getLogger(SignerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
