package view.panels.controls.jlist;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

/**
 *
 * @author Guerino
 */
/**
   * A cell renderer, which takes list items (as objects) and
   * translates them into list selection displays.  Used 
   * automatically by JList
   */

  public class CustomCellRenderer implements ListCellRenderer {    
    /**
     * When the CustomCellRenderer is loaded into our JList,
     * the list will call this method automatically to get the 
     * Components which are used for selection on the list.
     * @return 
     */
    @Override
    public Component getListCellRendererComponent(
                            JList list, // The list which is calling this method
                            Object value, // The object which represents the value
                            int index, // The index # of this object within the list
                            boolean isSelected, // Indicates that this item is currently
                                                // selected.
                            boolean cellHasFocus // Indicates whether the cell currently 
                                                 // has the focus
                            ) {
      
      // Cast our 'value' object into an object of our known class
      CustomListItem item = (CustomListItem) value;
      
      // Create a new JLabel (we'll return this to the list)
      JLabel cell = new JLabel();
      
      // Set the preferred size for each display 'label'
      cell.setPreferredSize(new Dimension(100,30));
      
      // Set the text portion of the cell, according to the value
      // object's .toString() method.
      cell.setText(item.getName());
      
      // Set the image portion of the cell, acquiring it from the 
      // value object.
      cell.setIcon(item.getIcon());
      
      // Do dome styling to the label.
      cell.setIconTextGap(10); // places 10 px between image & text
      cell.setFont(new Font( 
              "Times New Roman", 
              Font.PLAIN,
              12)); // Set ourselves a new font for the cells
      
      // If the current cell is selected
      if (isSelected) {
        // Set a blue border and backdrop
        cell.setBorder(new LineBorder(Color.blue));
        cell.setBackground(new Color(220,220,235));
      }
      else
      {
        // Set the deault background
        cell.setBorder(new LineBorder(list.getBackground()));
        cell.setBackground(list.getBackground());
      }
      
      // Turn opacity on so you can see our background changes
      cell.setOpaque(true);
      
      // Return the cell to the list for display
      return cell;
    }
  }