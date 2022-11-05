/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class SignInServer {
    
    public static int PUERTO = 5000;
    
    public SignInServer(){
        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        //Creamos un serverSocket para recojer la informacion del lado cliente 
        try{
        ServerSocket skServer = new ServerSocket(PUERTO);
        //Tenemos que atender a 10 clientes, con esta estructura de for vamos a crear 10 objectos socket para poder leer los 10 clientes
        for(int numUser = 0; numUser<9; numUser++){
            Socket skClient = new Socket();
            skClient = skServer.accept();
            User usr = new User();
            Message message = new Message();
            MessageType num;
            //Leemos cada usuario, cada mensaje y el tipo de mensaje
            input = new ObjectInputStream(skClient.getInputStream());
            User dato = (User) input.readObject();
            Message datoM = (Message) input.readObject();
            MessageType datoMT = (MessageType) input.readObject();
            input.close();
            skClient.close();
            skServer.close();
        }
        }catch (IOException e){
                Logger.getLogger(SignInServer.class.getName()).log(Level.SEVERE, null, e);
        }catch (ClassNotFoundException e){
                Logger.getLogger(SignInServer.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void setThread(){
        Socket sock = null; 
        Hilo hilo = new Hilo(sock);
        //hilo.start();
    }
    public int getNumThread(){
        int NumThread = 0, cont = 0;
        return NumThread;
    }
    public static void main(String[] args){
        new SignInServer();
    }
}
