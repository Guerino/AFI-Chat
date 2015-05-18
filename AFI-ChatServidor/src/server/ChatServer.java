package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import view.listener.IMainJFrameListener;

/**
 *
 * @author Guerino
 */
public class ChatServer implements Runnable {   
    private int puerto;
    private Socket conexion; //Socket para conectarse con el cliente
    private ServerSocket servidor;   
    private Thread thread;
    private List<ChatServerThread> listaSkClientes;//Lista de socket clientes conectados al server
    private List<User> listaUsuariosConectados; 
    private ExecutorService executor;//Para el pool de hilos conectados
    private volatile boolean ejecute;
    private IMainJFrameListener guiListener;
    private int contUserID;

    public ChatServer(int puerto, IMainJFrameListener listener) {
        this.puerto = puerto;
        this.guiListener = listener;
        this.thread = null;
        this.ejecute = true;
        this.contUserID = 0;
        this.listaSkClientes = new LinkedList<>();
        this.listaUsuariosConectados = new LinkedList<>();        
    }
    
    
     /**
    * Inicia el hilo del servidor
    */
    public void start() { 
       if (thread == null) {
           try {
               this.guiListener.appendTextConsole("Enlazando con el puerto " + puerto + ", por favor espere...");               
               servidor = new ServerSocket(this.puerto);               
               //System.out.println("ChatServerThread: " + "Server started: " + server);
               this.guiListener.appendTextConsole("Servidor iniciado: " + servidor);
               
               thread = new Thread(this);
               thread.start();
           } catch (IOException ex) {
               Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
           }
       } 
    }
   
   /**
    * Detiene el hilo del servidor
    */
   public void stop() {
      this.ejecute = false;
      if (thread != null) {
          this.guiListener.appendTextConsole("Deteniendo servidor....");
          
          if(listaSkClientes != null){
            for (int i = 0; i < listaSkClientes.size(); i++) {
                  listaSkClientes.get(i).close();
                  listaSkClientes.get(i).interrupt();
            }
          }
          
          try {
              if(!servidor.isClosed())
                        servidor.close();
          } catch (IOException ex) {
              Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
          }
          this.guiListener.llenarModeloTabla(null);
      }
      
   }
    
    @Override
    public void run() {
        while(ejecute){ //cuando la variable ejecute se pone a false sale del bucle
            try {
                conexion = servidor.accept(); //Permite al servidor aceptar conexiones
                //Lista con los clientes conectados
                agregarListaDeSocketCLientes(conexion);
                
                guiListener.appendTextConsole("Cliente conectado: "+conexion);
                
            } catch (IOException ex) {
                //System.out.println("servidor.accept() - IOException: " + ex);
            }
        }
        
        this.guiListener.appendTextConsole("Servidor detenido.");
    }
 
    private void agregarListaDeSocketCLientes(Socket socket) {
        ChatServerThread t = new ChatServerThread(this, socket, guiListener, "usuario");
        t.open();
        t.start();        
        //Lo agregamos a la lista
        listaSkClientes.add(t);
        contUserID++;
    }
    
    public void agregarListaUsuariosConectados(User usuario){
        listaUsuariosConectados.add(usuario);
    }
    
    public User buscarUsuario(int port){
        User client = null;
        for (int i = 0; i < listaUsuariosConectados.size(); i++) {
           if(listaUsuariosConectados.get(i).getPort() == port){
               return listaUsuariosConectados.get(i);//Devolvemos el cliente encontrado
           }            
        }
        return client;
    }
    
    public ChatServerThread buscarSocketCliente(int port){
        ChatServerThread client = null;
        for (int i = 0; i < listaSkClientes.size(); i++) {
           if(listaSkClientes.get(i).getSocketPort() == port){               
               return listaSkClientes.get(i);//Devolvemos el cliente encontrado
           }            
        }
        return client;
    }
    
    //Metodos para cuando un cliente se desconecta
    public void removerUsuario(int port){        
        for (int i = 0; i < listaUsuariosConectados.size(); i++) {
           if(listaUsuariosConectados.get(i).getPort() == port){              
               listaUsuariosConectados.remove(i);
           }            
        }
    }
    
    public void removerSocketCliente(int port){
        for (int i = 0; i < listaSkClientes.size(); i++) {
           if(listaSkClientes.get(i).getSocketPort() == port) {
               listaSkClientes.get(i).interrupt();
               listaSkClientes.get(i).close();
               listaSkClientes.remove(i);
           }            
        }
    }
    
    public void sendToClient(int port, String message) {
        //buscamos el cliente por el numero de puerto y le enviamos el mensaje
        ChatServerThread sk = buscarSocketCliente(port);
        sk.send(message);
    }

    public List<User> getListaUsuariosConectados() {
        return listaUsuariosConectados;
    }

    public int getContUserID() {
        return contUserID;
    }
    
    
    public String generarListaXMLUsuariosConectados() {
        //El protocolo esta armado en base a un archivo xml
        String xmlMensaje = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                            "<message>" +
                            "<action>update</action>";
        
        for (int i = 0; i < listaUsuariosConectados.size(); i++) {
            xmlMensaje += "<user><name>" + listaUsuariosConectados.get(i).getName() +"</name>";
            xmlMensaje += "<id>" + listaUsuariosConectados.get(i).getId()  + "</id>";
            xmlMensaje += "<port_id>" + listaUsuariosConectados.get(i).getPort()  + "</port_id></user>";
        }
        
        xmlMensaje += "</message>";
        return xmlMensaje;
    }
    
    public void sendAllConnected(String str){
        for (int i = 0; i < listaSkClientes.size(); i++) {
            listaSkClientes.get(i).send(str);
        }
    }
    
    public void removeAllConnected(){
        for (int i = 0; i < listaSkClientes.size(); i++) {  
           listaSkClientes.get(i).close();
           listaSkClientes.get(i).stopThread();                
        }

        this.listaSkClientes = new LinkedList<>();
        this.listaUsuariosConectados = new LinkedList<>(); 
        
        //Limpiamos la tabla
        guiListener.llenarModeloTabla(listaUsuariosConectados);        
    }
    
}