package server.state;

import controller.ControllerDB;
import org.w3c.dom.Document;
import server.ChatServer;
import server.ChatServerThread;
import view.listener.IMainJFrameListener;

/**
 * Aplicacion del Patron State(Estado)
 * para los distintos mensajes que va procesando el servidor
 * @author Guerino
 */
public interface IServerState {
    public void doProcess(ChatServer server, ChatServerThread thread, IMainJFrameListener guiListener, Document doc, ControllerDB cdb);
}
