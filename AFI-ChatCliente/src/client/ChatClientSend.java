package client;

import client.crypto.Encryptor;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Guerino
 */
public class ChatClientSend implements Runnable {
    private String mensaje;
    private Socket skCliente;
    private DataOutputStream salida;

    /**
     * Constructor de la clase ChatClientReceiver.
     * @param skCliente 
     * @param toSend 
     */
    public ChatClientSend(Socket skCliente, String toSend) {
        this.skCliente = skCliente;
        this.mensaje = toSend;
    }

    @Override
    public void run() {
        try {
            salida = new DataOutputStream(new BufferedOutputStream(skCliente.getOutputStream())); 
            
            //System.out.println("ChatClientSend-Mensaje: "+ mensaje);
            //System.out.println("ChatClientSend-Encriptado: "+ Encryptor.encript(mensaje));            
            //Enviamos el mensaje con el texto al servidor 
            salida.writeUTF(Encryptor.encript(this.mensaje));         
            salida.flush();
        } 
        catch(EOFException eof) { 
        }
        catch (IOException ex) {
            //Logger.getLogger(ChatClientSend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void close(){
        try {
            if(salida != null)              
                salida.close();     
            
            if(skCliente != null)
                skCliente.close();
        } catch (SocketException ex) {
            System.out.println("closeInputStream_socket -por haca salto");
            Logger.getLogger(ChatClientSend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChatClientSend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}