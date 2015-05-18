package server.state;

import controller.ControllerDB;
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
public class StateRegister implements IServerState {

    @Override
    public void doProcess(ChatServer server, ChatServerThread thread, IMainJFrameListener guiListener, Document doc, ControllerDB cdb) {
        
        NodeList name = doc.getElementsByTagName("name");
        String strName = thread.getCharacterDataFromElement((Element)name.item(0));

        NodeList password = doc.getElementsByTagName("password");
        String strPassword = thread.getCharacterDataFromElement((Element)password.item(0)).trim();
        
        NodeList email = doc.getElementsByTagName("email");
        String strEmail = thread.getCharacterDataFromElement((Element)email.item(0)).trim();
        
//        System.out.println("REGISTER: " + strName);
        //lo guardamos en la db
        cdb.registerUser(strName, strPassword, strEmail);
    }    
}