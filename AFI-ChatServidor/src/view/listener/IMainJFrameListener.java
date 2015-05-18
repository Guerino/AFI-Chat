package view.listener;

import java.util.List;
import model.User;

/**
 *
 * @author Guerino
 */
public interface IMainJFrameListener {
    public void setPort(int port);
    public int getPort();
    public void setIP(String strIP);
    public String getIP();
    public void appendTextConsole(String str);
    public void llenarModeloTabla(List<User> lista);
}