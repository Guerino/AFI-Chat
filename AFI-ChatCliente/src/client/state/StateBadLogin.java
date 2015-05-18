package client.state;

import client.ChatClientReceiver;
import org.w3c.dom.Document;
import view.interfaces.ILoginJPanel;
import view.interfaces.IUsersJPanel;

/**
 *
 * @author Guerino
 */
public class StateBadLogin implements IClientState {

    @Override
    public void doProcess(ILoginJPanel loginPanel, IUsersJPanel userPanel, ChatClientReceiver receiver, Document doc) {
        if(loginPanel != null)
            loginPanel.setTexInfoLabel("Mal el usuario o la contraseña.");
    }
    
}