package client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import view.interfaces.ILoginJPanel;
import view.interfaces.IUsersJPanel;

/**
 *
 * @author Guerino
 */
public class ChatClientManager {
     private Socket skCliente; //Socket para conectarse con el servidor
     private String ip; //IP del servidor al cual se conecta
     private int    puerto; //puerto del servidor en el cual este escucha a los clientes que se quieren conectar     
     private ExecutorService executor; //Para correr los threads
     private boolean isConnected;
//     public static volatile boolean connectAccept;
     
    /**
     * Constructor de la clase
     * @param ip
     * @param puerto 
     */
    public ChatClientManager(String ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
        this.executor = null;
        this.isConnected = false;
        this.initSocket();       
    }     

    /**
     * Metodo privado que instancia el socket cliente
     */
    private void initSocket() {
        try {
            star();//iniciamos el pool de hilos   
            //System.out.println("Iniciando socket cliente...");
            skCliente = new Socket(ip, puerto);        
            //System.out.println("Conectado: " + skCliente);
            this.isConnected = true;
         } catch (ConnectException ex) {
            System.out.println("No se pudo conectar con el servidor.");
            //Logger.getLogger(ChatClientManager.class.getName()).log(Level.SEVERE, null, ex);
           // System.exit(-1);
         } catch (IOException ex) {
             //Logger.getLogger(ChatClientManager.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    /**
     * Metodo que inicia los hilos de envio y recepcion de datos al servidor
     */
    private void star(){
        executor = Executors.newCachedThreadPool();               
    }
        
    /**
     * Metodo que detiene la ejecucion de los hilos de envio y recepcion de datos al servidor
     */
    public void stop() {        
        executor.shutdown();
    }

    public boolean isConnected() {
        return isConnected;
    }
    public int getLocalPort(){
        return this.skCliente.getLocalPort();
    } 
    
    public void sendText(String text){     
        executor.execute(new ChatClientSend(skCliente, text));        
    }    
       
    public void setListener(IUsersJPanel userPanel, ILoginJPanel loginPanel) {
        executor.execute(new ChatClientReceiver(skCliente, userPanel, loginPanel));
    }
        
}