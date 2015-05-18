package view.interfaces;

import java.util.List;
import model.User;

/**
 *
 * @author Guerino
 */
public interface IUsersJPanel {
    public void updateTableModel(List<User> lista);
    public void openChatFrame(User to, User from, String message);
    public void exitChat();
}
