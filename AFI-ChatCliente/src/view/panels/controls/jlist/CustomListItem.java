package view.panels.controls.jlist;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Guerino
 */
public class CustomListItem {
    // The image representing this item
    Image image;   

    // The name of this item
    String name; 
    int id; //id en la DB
    int port; //puerto del socket asignado por el server

    /**
     * Constructor de la clase
     * @param image
     * @param name
     * @param id
     * @param port 
     */
    public CustomListItem(Image image, String name, int id, int port) {
        this.image = image;
        this.name = name;
        this.id = id;
        this.port = port;
    }
    
    /**
     * Constructor
     * @param pathImage
     * @param name
     * @param id
     * @param port 
     */
    public CustomListItem(String pathImage, String name, int id, int port) {
        this.setImage(pathImage);
        this.name = name;
        this.id = id;
        this.port = port;
    }
    
    /**
     * Set Image for this item 
     * @param image 
     *   The image representing this item
     */

    public void setImage(Image image) {
      this.image = image;
    }
    
    /**
     * Set Image from your path
     * @param path 
     */
    public void setImage(String path) {
      setImage(loadImage(path));
    }
    
    private Image loadImage(String pathname) {    
        Image img = new ImageIcon(getClass().getResource(pathname)).getImage();
        return img;

        // if things get messed up
//        return null;    
    }

    /**
     * Get the Icon for this item.
     * @return 
     *   An iconized form of image.
     */

    public Icon getIcon() {
      ImageIcon icon = new ImageIcon(image.getScaledInstance(24,24,Image.SCALE_FAST));
      return icon;
    }

    /**
     * Set the name of this item
     * @param name 
     *  The new name of this item
     */

    public void setName(String name) {
      this.name = name;
    }

    /**
     * Get the name of this item
     * @return 
     * The name of this item
     */

    public String getName() {
      return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
}