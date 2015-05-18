package client;

import client.crypto.Encryptor;
import client.state.IClientState;
import client.state.StateBadLogin;
import client.state.StateExit;
import client.state.StateMessage;
import client.state.StateUpdate;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
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
import view.interfaces.ILoginJPanel;
import view.interfaces.IUsersJPanel;

/**
 *
 * @author Guerino
 */
public class ChatClientReceiver  implements Runnable {
    private Socket skCliente;
    private DataInputStream entrada;
    private volatile boolean corriendo;    
    private IUsersJPanel userPanelListener; //Interface para mostrar actualizar la lista de usuarios
    private ILoginJPanel loginPanelListener;
    private IClientState state;

    public ChatClientReceiver(Socket skCliente, IUsersJPanel userPanel, ILoginJPanel loginPanel) {
        this.skCliente = skCliente;
        this.userPanelListener = userPanel;
        this.loginPanelListener = loginPanel;
        this.corriendo = true;
        this.state = null;
    }    
    
    @Override
    public void run() {
        open();
        DocumentBuilder db = null; 
        InputSource is = null;
        //bucle de lectura y procesamiento de los mensajes recibidos
        while(corriendo) {
            try {
                db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                is = new InputSource(); 
                String mensaje = entrada.readUTF();                
                String desc = Encryptor.decrypt(mensaje);
                
//                System.out.println("Recibido: " + mensaje);
//                System.out.println("Descifrado: " + Encryptor.decrypt(mensaje));
                
                //1-leer un nuevo mensaje enviado por el servidor                
                is.setCharacterStream(new StringReader(desc));
                
                Document doc = db.parse(is);
                NodeList nodeAction = doc.getElementsByTagName("action");                    
                String strAction = getCharacterDataFromElement((Element)nodeAction.item(0));
                //2-Segun el texto del action procesamos
                switch (strAction) {                   
                    case "update":
                        //System.out.println("doProcessUpdate: " + desc);
                        this.state = new StateUpdate();
                        this.state.doProcess(loginPanelListener, userPanelListener, this, doc);
                        break;
                        
                    case "message":
                        //System.out.println("doProcessMessage: " + desc);
                        this.state = new StateMessage();
                        this.state.doProcess(loginPanelListener, userPanelListener, this, doc);                       
                        break;
                        
                    case "exit":
                        //System.out.println("doProcessExit: " + desc);
                        this.state = new StateExit();
                        this.state.doProcess(loginPanelListener, userPanelListener, this, doc);  
                        break;
                    
                    case "bad_login":
                        this.state = new StateBadLogin();
                        this.state.doProcess(loginPanelListener, userPanelListener, this, doc);  
                        break;
                        
                    default:
                        break;
                }
                
            } catch (EOFException eof){
                //System.out.println("EOFException:ChatClientReceiver.run: por haca salto.");
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(ChatClientReceiver.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ChatClientReceiver.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(ChatClientReceiver.class.getName()).log(Level.SEVERE, null, ex);
            } 
           
        }        
        close();
    }
    
     /**
     * Abre el flujo de entrada para poder recibir los datos enviados por el servidor.
     */
    private void open() {
        try {
            entrada = new DataInputStream(new BufferedInputStream(skCliente.getInputStream()));
        } catch (IOException ex) {
            System.out.println("ChatClientReceiver.open(), por haca salto.");
            Logger.getLogger(ChatClientReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cierra el flujo de salida y cierra el socket.
     */
    private void close(){
        try {            
            entrada.close();
            skCliente.close();
        } catch (IOException ex) {
            System.out.println("ChatClientReceiver.close(), por haca salto.");
            Logger.getLogger(ChatClientReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }    
    
    /**
     * Detiene la ejecucion del hilo
     */
    public void stop() {
        this.corriendo = false;
    }
    
    /**
     * Metodo privado para el parser xml
     * @param e
     * @return 
     */
    public String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
          CharacterData cd = (CharacterData) child;
          return cd.getData();
        }
        return "";
    }    
}