package client.state;

import client.ChatClientReceiver;
import model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import view.interfaces.ILoginJPanel;
import view.interfaces.IUsersJPanel;

/**
 *
 * @author Guerino
 */
public class StateMessage implements IClientState {

    @Override
    public void doProcess(ILoginJPanel loginPanel, IUsersJPanel userPanel, ChatClientReceiver receiver, Document doc) {
       //extraer el contenido del mensaje
        NodeList nodeTo = doc.getElementsByTagName("to");
       String  strTo = receiver.getCharacterDataFromElement((Element)nodeTo.item(0));       
       NodeList nodeToID = doc.getElementsByTagName("to_id");
       String  strToID = receiver.getCharacterDataFromElement((Element)nodeToID.item(0));       
       NodeList nodeToPortID = doc.getElementsByTagName("to_port_id");
       String  strToPortID = receiver.getCharacterDataFromElement((Element)nodeToPortID.item(0));
       NodeList nodeToColor = doc.getElementsByTagName("to_color");
       String  strToColor = receiver.getCharacterDataFromElement((Element)nodeToColor.item(0));  
       
       NodeList nodeFrom = doc.getElementsByTagName("from");
       String  strFrom = receiver.getCharacterDataFromElement((Element)nodeFrom.item(0));       
       NodeList nodeFromID = doc.getElementsByTagName("from_id");
       String  strFromID = receiver.getCharacterDataFromElement((Element)nodeFromID.item(0));       
       NodeList nodeFromPortID = doc.getElementsByTagName("from_port_id");
       String  strFromPortID = receiver.getCharacterDataFromElement((Element)nodeFromPortID.item(0));       
       NodeList fromColor = doc.getElementsByTagName("from_color");
       String strFromColor = receiver.getCharacterDataFromElement((Element)fromColor.item(0)).trim();
       
       NodeList nodeData = doc.getElementsByTagName("data");
       String  strMensaje = receiver.getCharacterDataFromElement((Element)nodeData.item(0));  
       
       //Abrimos la ventana de chat
       if(userPanel != null){
//            userPanel.openChatFrame(strFrom, strTo, 0, Integer.valueOf(strFromPortID), strData);
           User to = new User();
           to.setId(Integer.valueOf(strToID));
           to.setPort(Integer.valueOf(strToPortID));
           to.setName(strTo);
           to.setColor(strToColor);
           
           User from = new User();
           from.setId(Integer.valueOf(strFromID));
           from.setPort(Integer.valueOf(strFromPortID));
           from.setName(strFrom);
           from.setColor(strFromColor);
                      
           /**
            * Aqui los usuarios se invierten
            * del servidor venia [from]->to
            * y queda [to]->from
            */
           userPanel.openChatFrame(from, to, strMensaje); 
       }
    }
    
}