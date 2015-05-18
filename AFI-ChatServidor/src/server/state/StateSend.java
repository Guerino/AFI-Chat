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
public class StateSend implements IServerState {

    @Override
    public void doProcess(ChatServer server, ChatServerThread thread, IMainJFrameListener guiListener, Document doc, ControllerDB cdb) {
       
        NodeList to = doc.getElementsByTagName("to");
        String strToUser = thread.getCharacterDataFromElement((Element)to.item(0));        
        NodeList toID = doc.getElementsByTagName("to_id");
        String strToID = thread.getCharacterDataFromElement((Element)toID.item(0));
        NodeList toPortID = doc.getElementsByTagName("to_port_id");
        String strToPortID = thread.getCharacterDataFromElement((Element)toPortID.item(0)).trim();        
        NodeList toColor = doc.getElementsByTagName("to_color");
        String strToColor = thread.getCharacterDataFromElement((Element)toColor.item(0)).trim();
        
        NodeList from = doc.getElementsByTagName("from");
        String strFromUser = thread.getCharacterDataFromElement((Element)from.item(0));         
        NodeList fromID = doc.getElementsByTagName("from_id");
        String strFromID = thread.getCharacterDataFromElement((Element)fromID.item(0));
        NodeList fromPortID = doc.getElementsByTagName("from_port_id");
        String strFromPortID = thread.getCharacterDataFromElement((Element)fromPortID.item(0)).trim();
        NodeList fromColor = doc.getElementsByTagName("from_color");
        String strFromColor = thread.getCharacterDataFromElement((Element)fromColor.item(0)).trim();
        
        NodeList data = doc.getElementsByTagName("data");
        String messageData = thread.getCharacterDataFromElement((Element)data.item(0));
        
        //Recibimos el mensaje del from y lo armamos para el to
        String xmlMensaje = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                            "<message>" +
                            "<action>message</action>" +
                            "<head>" +
                            "<to>"+ strToUser + "</to>" +
                            "<to_id>" + strToID + "</to_id>" +
                            "<to_port_id>" + strToPortID + "</to_port_id>" + 
                            "<to_color>" + strToColor + "</to_color>" + 
                            "<from>"+strFromUser+"</from>" +
                            "<from_id>"+strFromID+"</from_id>" +
                            "<from_port_id>"+strFromPortID+"</from_port_id>" + 
                            "<from_color>" + "BLUE"  + "</from_color></head>" + 
                            "<data>"+messageData+"</data>" +
                            "</message>";

        User toUser = server.buscarUsuario(Integer.valueOf(strToPortID));
        //buscamos por el puerto y le enviamos el mensaje
        server.sendToClient(toUser.getPort(), xmlMensaje); 
        
        System.out.println("Guardando mensaje en la DB: " + xmlMensaje);
        //guardamos el mensaje en la db
        cdb.saveMessage(Integer.valueOf(strToID), Integer.valueOf(strFromID), messageData);
    }    
}