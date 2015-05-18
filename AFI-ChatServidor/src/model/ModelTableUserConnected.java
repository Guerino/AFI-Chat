package model;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Guerino
 */
public class ModelTableUserConnected extends DefaultTableModel {
    private final String tituloCabeceras[] = {
        "ID","Nombre","Puerto"
    };

    public ModelTableUserConnected() {
        //Establecemos el nombre de las columnas
        for (String columna : tituloCabeceras) 
            this.addColumn(columna);  
    }    
    
    //Método para definir que las tablas no sean editables
    @Override
    public boolean isCellEditable (int row, int column){
        return column==999999;
    }
}