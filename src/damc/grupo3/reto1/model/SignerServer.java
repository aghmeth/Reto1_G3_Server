/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author 2dam
 */
public class SignerServer extends Thread{
    private static final ResourceBundle RETO1 = ResourceBundle.getBundle("model.reto1");
    private static final int MAX_USERS = Integer.parseInt(RETO1.getString("MaxUsers"));
    private static final int PORT = Integer.parseInt(RETO1.getString("PORT"));

    //private static ArrayList<SignerThread> acutalClients = new ArrayList<>();

    private static final Logger LOGGER = Logger.getLogger(SignerServer.class.getName());

    private static boolean serverOn = true;
    private ServerSocket svSocket;
    private Socket skClient;
    private static Integer i;
    private SignerThread st;

    /**
     * Method use to start the server
     *
     */


    public void makeThread () {  //Hay que enviarle algo?!?!?!?
        
        try {
          svSocket = new ServerSocket(PORT);
           
          while (serverOn) {
                //Preguntar si no ha superado el limite de usuarios que pueden conectarse a la vez
                if (i < MAX_USERS) {
                     //EL servidor acepta un cliente y en soc guarda el socket de la conexion que se ha establecido
                    skClient = svSocket.accept();
                    //Crear hilo pasándole el Socket skCliente
                    st = new SignerThread(skClient);
                    añadirCliente(st);
               
                } else {
                    
                    //devolvemos un error al cliente con que no se aceptan mas peticiones
                    ObjectOutputStream oos = new ObjectOutputStream(skClient.getOutputStream());
                    Message mensaje = new Message();
                    mensaje.setMessageType(MessageType.MAX_USER);
                    oos.writeObject(mensaje);
                }  
           }
           
           //Cerramos el servidor
           svSocket.close();
      } catch (IOException ex) {
            Logger.getLogger(SignerServer.class.getName()).log(Level.SEVERE, null, ex);
        
      }

    }

    public static synchronized void añadirCliente (SignerThread signerT){
        i++;
    }
    public static synchronized  void borrarCliente (SignerThread signerT){
        i--;
    }
    
}