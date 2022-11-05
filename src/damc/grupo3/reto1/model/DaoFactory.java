/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damc.grupo3.reto1.model;

import java.net.Socket;

/**
 *
 * @author Diego
 */
public class DaoFactory {
    /*Los dao dependen de los hilos para que puedan atender a varios clientes, los hilos recogen
    la informacion del SignInServer, un socket, por lo tanto nuestro Dao neceitara el socket
    como parámetro*/
    private Socket s;
    //Generamos una constrcuion de Dao con el parámetro del Socket
    private Hilo getDao(){
        Hilo daoHilo;
        daoHilo = new Hilo(s);
         return daoHilo;
    }
}
