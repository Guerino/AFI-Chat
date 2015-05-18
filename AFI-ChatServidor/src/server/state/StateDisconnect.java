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
public class StateDisconnect implements IServerState {

    @Override
    public void doProcess(ChatServer server, ChatServerThread thread, IMainJFrameListener guiListener, Document doc, ControllerDB cdb) {
       System.out.println("accion: DESCONECTAR");
        NodeList port = doc.getElementsByTagName("from_port_id");
        String strPortID = thread.getCharacterDataFromElement((Element)port.item(0));
        User us = server.buscarUsuario(Integer.valueOf(strPortID));
        if(us != null){
            guiListener.appendTextConsole(us.getName() + ", se ha desconectado.");
            server.removerUsuario(us.getPort());
            server.removerSocketCliente(us.getPort());

            guiListener.llenarModeloTabla(server.getListaUsuariosConectados());

            //le envia la lista de usuarios conectados a todos los clientess
            String strUpdateDisc = server.generarListaXMLUsuariosConectados();
            server.sendAllConnected(strUpdateDisc);                                
        }     
    }
    
}
