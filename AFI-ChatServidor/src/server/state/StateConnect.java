package server.state;

import controller.ControllerDB;
import model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import server.ChatServer;
import server.ChatServerThread;
import view.listener.IMainJFrameListener;

/**
 *
 * @author Guerino
 */
public class StateConnect implements IServerState {

    @Override
    public void doProcess(ChatServer server, ChatServerThread thread, IMainJFrameListener guiListener, Document doc, ControllerDB cdb) {
        System.out.println("accion: CONECTAR");
        
        NodeList usuario = doc.getElementsByTagName("name");
        String strName = thread.getCharacterDataFromElement((Element)usuario.item(0)); 
        
        NodeList password = doc.getElementsByTagName("password");
        String strPassword = thread.getCharacterDataFromElement((Element)password.item(0));
        
        NodeList port_id = doc.getElementsByTagName("port_id");
        String strPort_id = thread.getCharacterDataFromElement((Element)port_id.item(0));                 
        
        if(cdb.loginUser(strName, strPassword))
        {       
            guiListener.appendTextConsole(strName + ", ha ingresado al chat.");
            //si el usuario y el password existen en la db, continuar
            server.agregarListaUsuariosConectados(new User(cdb.getUserId(strName, strPassword), thread.getSocketPort(), strName));
                      
            //generamos la lista de usuarios conectados y se la enviamos junto con el action=update
            guiListener.llenarModeloTabla(server.getListaUsuariosConectados());
            //le envia la lista de usuarios conectados a todos los clientess
            String strUpdateCon = server.generarListaXMLUsuariosConectados();
            
            System.out.println("UPDATE: " +strUpdateCon);
            
            server.sendAllConnected(strUpdateCon);
            
        }
        else
        {
            //si no exite, le enviamos un mensaje de no existencia, y lo eliminamos de la lista de sockets            
             String xmlMensaje = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                                "<message>" +
                                "<action>bad_login</action>" +
                                "</message>";  
          
            //enviamos al cliente el mensaje de mal login
            server.sendToClient(Integer.valueOf(strPort_id), xmlMensaje);
                        
            guiListener.appendTextConsole(strName + ", mal el usuario o la contraseña.");
            server.removerSocketCliente(Integer.valueOf(strPort_id));        
        }       
    }
}