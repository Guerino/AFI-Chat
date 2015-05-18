package client.state;

import client.ChatClientReceiver;
import java.util.LinkedList;
import java.util.List;
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
public class StateUpdate implements IClientState {

    @Override
    public void doProcess(ILoginJPanel loginPanel, IUsersJPanel userPanel, ChatClientReceiver receiver, Document doc) {
       
        //este metodo solo se invoca la primera vez
        if(!loginPanel.isLogueado())
            loginPanel.loginAccepted();
        
        //iterar y armar la lista de usuarios conectados                            
        List<User> uList = new LinkedList<>();
        NodeList nodes = doc.getElementsByTagName("user");

        for (int i = 0; i < nodes.getLength(); i++) {
          Element element = (Element) nodes.item(i);

          NodeList name = element.getElementsByTagName("name");
          String  nombre = receiver.getCharacterDataFromElement((Element)name.item(0));        

          NodeList id = element.getElementsByTagName("id");
          String strID = receiver.getCharacterDataFromElement((Element)id.item(0));
          
          NodeList port = element.getElementsByTagName("port_id");
          String strPort = receiver.getCharacterDataFromElement((Element)port.item(0));

          //agregamos a la lista
          uList.add(new User(Integer.valueOf(strID), Integer.valueOf(strPort), nombre)); 
        } 
        
//        System.out.println("Usuarios en la lista: " + uList.size());
        //actualizamos el JList con los usuarios conectados
//        if(userPanel != null) //userPanel no deja de ser null apenas el usuario se loguea
//        {           
            userPanel.updateTableModel(uList);
//        }
        
    }
    
}