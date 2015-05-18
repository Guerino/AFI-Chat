package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import model.ModelTableUserConnected;
import model.User;
import server.ChatServer;
import view.listener.IMainJFrameListener;

/**
 *
 * @author Guerino 2015
 */
public class MainJFrame extends javax.swing.JFrame {
    private boolean startServer;    
    private ChatServer server = null;
    private IMainJFrameListener guiListener;
    private ModelTableUserConnected modelTableUserConnected;
    private String title = "AFI - Servidor de chat v";
    
    /**
     * Creates new form NewJFrame
     */
    public MainJFrame() {
        initComponents();           
        setTitle(title);
        setIconImage (new ImageIcon(getClass().getResource("/resources/server-32.png")).getImage());
        printVersion();
        //Centro el JFrame en la pantalla
        setLocationRelativeTo(null);
        
        this.startServer = false;
        this.title = getTitle();
        this.guiListener = new GUIListener();
        this.guiListener.setIP("localhost");
        
        iniciartTablaVectorDeEstado();
        this.modelTableUserConnected = new ModelTableUserConnected();
        jTableUserConected.setModel(modelTableUserConnected);
        
        this.jButtonExitClients.setEnabled(false);
    }

    private void iniciartTablaVectorDeEstado() {
        jTableUserConected.setDefaultRenderer(Object.class, new TableCellRenderer(){
            private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (row%2 == 0){
                         c.setBackground(Color.WHITE);                       
                    }
                    else {
//                        Color alternateColor = new Color(252,242,206);
                        c.setBackground(Color.LIGHT_GRAY);                        
                    }
                    
                    if(isSelected){
                           c.setForeground(Color.BLACK);
                           c.setFont(new Font("Tahoma", Font.BOLD,11));
                           c.setBackground(Color.CYAN);
                    }
                    else{
                           c.setForeground(Color.BLACK);
                    }
                    
                    return c;
                }
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaConsola = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldPort = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldIP = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableUserConected = new javax.swing.JTable();
        jButtonStartServer = new javax.swing.JButton();
        jButtonExitClients = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Consola"));

        jTextAreaConsola.setEditable(false);
        jTextAreaConsola.setBackground(new java.awt.Color(0, 0, 0));
        jTextAreaConsola.setColumns(20);
        jTextAreaConsola.setForeground(new java.awt.Color(0, 255, 0));
        jTextAreaConsola.setRows(5);
        jScrollPane2.setViewportView(jTextAreaConsola);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Puerto:");

        jTextFieldPort.setText("12345");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Server IP:");

        jTextFieldIP.setEditable(false);

        jTableUserConected.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Usuario", "Puerto"
            }
        ));
        jScrollPane1.setViewportView(jTableUserConected);

        jButtonStartServer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonStartServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/start_24.png"))); // NOI18N
        jButtonStartServer.setText("Iniciar servidor de chat");
        jButtonStartServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartServerActionPerformed(evt);
            }
        });

        jButtonExitClients.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/close_24.png"))); // NOI18N
        jButtonExitClients.setText("Cerrar todos los usuarios");
        jButtonExitClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitClientsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldIP, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonStartServer, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                    .addComponent(jButtonExitClients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonStartServer)
                        .addComponent(jLabel1)
                        .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jTextFieldIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonExitClients))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(server != null){
            server.stop();            
            server = null;
        }
    }//GEN-LAST:event_formWindowClosing

    private void jButtonStartServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartServerActionPerformed
        // Iniciamos o detenemos el servidor        
        if(!startServer)
        {
            //Instancia del servidor
            server = new ChatServer(Integer.valueOf(jTextFieldPort.getText().trim()), guiListener);
            server.start();
            jButtonStartServer.setIcon(null);
            jButtonStartServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/stop_24.png")));
            jButtonStartServer.setText("Detener servidor de chat");
            startServer = true;
            this.setTitle(title + " - [Corriendo...]");
            setIconImage (new ImageIcon(getClass().getResource("/resources/server-32-run.png")).getImage());
            //this.jButtonExitClients.setEnabled(true);
        }
        else
        {                       
            server.stop();            
            server = null;
                        
            jButtonStartServer.setText("Iniciar servidor de chat");
            jButtonStartServer.setIcon(null);
            jButtonStartServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/start_24.png")));
            startServer = false;
            jTableUserConected.setModel(new ModelTableUserConnected());
            this.setTitle(title + " - [Detenido]");
            setIconImage (new ImageIcon(getClass().getResource("/resources/server-32.png")).getImage());
            this.jButtonExitClients.setEnabled(false);
        }
        
    }//GEN-LAST:event_jButtonStartServerActionPerformed

    private void jButtonExitClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitClientsActionPerformed
        // TODO add your handling code here:
        this.sendExitAllConnected();
        this.jButtonExitClients.setEnabled(false);
    }//GEN-LAST:event_jButtonExitClientsActionPerformed

    private void sendExitAllConnected(){
        //Enviamos a todos los clientes la orden de salir
        String xmlExit = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                         "<message>" +
                         "<action>exit</action>" +
                         "</message>";
        server.sendAllConnected(xmlExit);
        server.removeAllConnected();
    }
    
    private Object[] getVectorFilaDeResultados(User u){
        Object[] datos = new Object[3];
        datos[0]  = " " + u.getId();
        datos[1]  = " " + u.getName();
        datos[2]  = " " + u.getPort();
        return datos;
    }
    
    /**
    * Metodo encagargado de leer la version del manifest.mf
    */
    private void printVersion()
    {
        String jarFileURL = MainJFrame.class.getProtectionDomain().getCodeSource().getLocation().toString();
               
        if(!jarFileURL.contains(".jar")){
            jarFileURL = "file:/D:/Arq.De.Sw-2015/AFI-ChatServidor/dist/AFI-ChatServidor.jar";
        }
        
        int pos = jarFileURL.indexOf("!");         
        if(pos != -1) {   
            jarFileURL = jarFileURL.substring(0,pos);  
        }
        
        if(!jarFileURL.startsWith("jar:")) {  
            jarFileURL = "jar:" + jarFileURL; 
        } 
        
        if(jarFileURL.lastIndexOf(".jar") != -1){
            URL manifestUrl; 
            try {

                manifestUrl = new URL(jarFileURL + "!/META-INF/MANIFEST.MF");
                Manifest manifest = new Manifest(manifestUrl.openStream());   

                if(manifest != null){
                    String str = this.getTitle() + manifest.getMainAttributes().getValue("Implementation-Version") 
                            + " (Build: " + manifest.getMainAttributes().getValue("Build-Number") 
                            + ", Compiled: "+  manifest.getMainAttributes().getValue("Built-Date") + ")";
                    setTitle(str);
                }
            }        
            catch (IOException ex) {
                Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
     /**
     * Clase interna que permite actualizar los controles
     * de la UI dentro de los hilos del servidor
     */
    class GUIListener implements IMainJFrameListener {

        @Override
        public void setPort(int port) {
            jTextFieldPort.setText(Integer.toString(port));
        }

        @Override
        public int getPort() {
            return Integer.parseInt(jTextFieldPort.getText().trim());
        }

        @Override
        public void setIP(String strIP) {
            jTextFieldIP.setText(strIP);
        }

        @Override
        public String getIP() {
            return jTextFieldIP.getText().trim();
        }

        @Override
        public void appendTextConsole(String str) {
            jTextAreaConsola.append(str + "\n");
            jTextAreaConsola.setCaretPosition(jTextAreaConsola.getDocument().getLength());
        }
                
        @Override
        public void llenarModeloTabla(List<User> lista){
            if(lista != null){
                jButtonExitClients.setEnabled(true);
                modelTableUserConnected = new ModelTableUserConnected();
                jTableUserConected.setModel(modelTableUserConnected);
                //Rellenamos la tabla con los resultados       
                for(User u : lista) {
                   modelTableUserConnected.addRow(getVectorFilaDeResultados(u));
                }
            }
        }
    }
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonExitClients;
    private javax.swing.JButton jButtonStartServer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableUserConected;
    private javax.swing.JTextArea jTextAreaConsola;
    private javax.swing.JTextField jTextFieldIP;
    private javax.swing.JTextField jTextFieldPort;
    // End of variables declaration//GEN-END:variables

}