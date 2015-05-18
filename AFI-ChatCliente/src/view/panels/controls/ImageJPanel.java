package view.panels.controls;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Guerino
 */
 
public class ImageJPanel extends JPanel {
 
    private Image imagen;
 
    public ImageJPanel() {
    }
 
    public ImageJPanel(String nombreImagen) {
        if (nombreImagen != null) {
            imagen = new ImageIcon(getClass().getResource(nombreImagen)).getImage();
        }
    }
 
    public ImageJPanel(Image imagenInicial) {
        if (imagenInicial != null) {
            imagen = imagenInicial;
        }
    }
 
    public void setImagen(String nombreImagen) {
        if (nombreImagen != null) {
            imagen = new ImageIcon(getClass().getResource(nombreImagen)).getImage();
        } else {
            imagen = null;
        }
 
        repaint();
    }
 
    public void setImagen(Image nuevaImagen) {
        imagen = nuevaImagen;
 
        repaint();
    }
 
    @Override
    public void paint(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(),this); 
            setOpaque(false);
            
        } else {
            setOpaque(true);
        }
 
        super.paint(g);
    }

}