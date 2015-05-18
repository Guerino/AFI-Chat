package client.state;

import client.ChatClientReceiver;
import org.w3c.dom.Document;
import view.interfaces.ILoginJPanel;
import view.interfaces.IUsersJPanel;

/**
 * Aplicacion del Patron State(Estado)
 * para los distintos mensajes que va procesando el cliente
 * @author Guerino
 */
public interface IClientState {
    public void doProcess(ILoginJPanel loginPanel, IUsersJPanel userPanel, ChatClientReceiver receiver, Document doc);
}