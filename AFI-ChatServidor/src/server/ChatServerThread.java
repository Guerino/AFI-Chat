package server;

import controller.ControllerDB;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import server.crypto.Encryptor;
import server.state.IServerState;
import server.state.StateConnect;
import server.state.StateDisconnect;
import server.state.StateRegister;
import server.state.StateSend;
import view.listener.IMainJFrameListener;

/**
 *
 * @author Guerino
 */
public class ChatServerThread extends Thread {
    private ChatServer       servidor  = null;
    private Socket           socket    = null;
    private int              skPort        = -1; //puerto que le asigna el ServerSocket
    private DataInputStream  streamIn  =  null;
    private DataOutputStream streamOut = null;
    private volatile boolean          stopThread = false;  
    private IMainJFrameListener guiListener = null;
    private ControllerDB cdb = null; // esta mal hay que enviarle un mensaje al server para que el lo almacene en su db  
    private IServerState state = null;
   
    public ChatServerThread(ChatServer server, Socket socket, IMainJFrameListener guiListener, String user) {  
      super();
      this.servidor = server;
      this.socket   = socket;
      this.skPort   = socket.getPort();
      this.cdb = new ControllerDB();
      this.guiListener = guiListener;
    }
   
    /**
    * Obtiene el puerto asignado al cliente
    * @return 
    * El puerto actual para este cliente
    */
    public int getSocketPort() {  
       return skPort;
    }
   
    public void open() {  
       try {
           streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));          
           streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
       } catch (IOException ex) {
           Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
    public void close() {  
       try {
           if (socket != null)    socket.close();
           if (streamIn != null)  streamIn.close();
           if (streamOut != null) streamOut.close();
       } catch (IOException ex) {
           Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   @Override
    public void run() {       
       //Bucle para la lectura de datos provenientes del cliente
        try {
            while(!stopThread){
                //1- leemos el mensaje del cliente
                String mensaje = streamIn.readUTF();        
                String desc = Encryptor.decrypt(mensaje);
                
//                System.out.println("Recibido: " + mensaje);
//                System.out.println("Descifrado: " + Encryptor.decrypt(mensaje));
                                
                //2-procesamos el protocolo
                DocumentBuilder dbf; 
                InputSource is;
                try {
                    dbf = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    is = new InputSource();
                    is.setCharacterStream(new StringReader(desc));

                    Document doc = dbf.parse(is);
                    NodeList action = doc.getElementsByTagName("action");    
                    //Chekamos el comando a procesar
                    //3- y enviamos la respuesta con los datos al cliente
                    switch(getCharacterDataFromElement((Element)action.item(0))){
                        case "connect":  
                            this.state = new StateConnect();
                            this.state.doProcess(servidor, this, guiListener, doc, cdb);
                            break;
                            
                        case "send":                           
                            this.state = new StateSend();
                            this.state.doProcess(servidor, this, guiListener, doc, cdb);
                            break;
                            
                        case "disconnect":                            
                            this.state = new StateDisconnect();
                            this.state.doProcess(servidor, this, guiListener, doc, cdb);
                            break;
                            
                        case "register":
                            //registro de un nuevo usuario
                            this.state = new StateRegister();
                            this.state.doProcess(servidor, this, guiListener, doc, cdb);
                            break;
                        default:
                            break;
                    }
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }               
            }
        } 
        catch (SocketException se){
            //System.out.println("Error al recivir datos");
        }
        catch (IOException ex) {
            //Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);       
        }
    }  
   
    /**
     * Envia el texto al cliente 
     * @param mensaje texto en formato xml para ser enviado al cliente
    */
    public void send(String mensaje) {
       try {
//           System.out.println("Enviando al cliente: " + mensaje);
//           System.out.println("Enviando al cliente cifrado: " + Encryptor.encript(mensaje) + "\n");
           
           streamOut.writeUTF(Encryptor.encript(mensaje));          
           streamOut.flush();
        } catch (IOException ex) {
           Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
          CharacterData cd = (CharacterData) child;
          return cd.getData();
        }
        return "";
    }
    
    public void stopThread() {
        this.stopThread = true;
    }
   
}