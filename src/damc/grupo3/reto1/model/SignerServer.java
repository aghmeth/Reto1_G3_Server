/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.model;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 *
 * @author 2dam
 */
public class SignerServer extends Thread{
    private static final ResourceBundle RETO1 = ResourceBundle.getBundle("model.reto1");
    private static final int MAX_USERS = Integer.parseInt(RETO1.getString("MaxUsers"));
    private static final int PORT = Integer.parseInt(RETO1.getString("PORT"));

    private static ArrayList<SignerThread> receiveClients = new ArrayList<>();

    private static final Logger LOGGER = Logger.getLogger(SignerServer.class.getName());

    private static boolean serverOn = true;
    private static ServerSocket svSocket;

    /**
     * Method use to start the server
     *
     */


}
