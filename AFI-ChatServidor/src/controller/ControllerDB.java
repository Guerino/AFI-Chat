package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guerino
 */
public class ControllerDB {
    private Connection connection;
    private Statement statement;
    private final String pathDB = "D:/Arq.De.Sw-2015/afi-chat-db.db";

    public ControllerDB() {
       this.connection = null;
       this.statement = null;
    }
    
    private void connect() {
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + pathDB); //ejemplo: jdbc:sqlite:C:/work/mydatabase.db
            statement = connection.createStatement();
            statement.setQueryTimeout(1);  // set timeout to 30 sec. 
            
        } catch (SQLException ex) {
            System.out.println("ControllerDB.connect(), Por haca salto.");
            Logger.getLogger(ControllerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void disconnect() {
      try {
        if(connection != null)
          connection.close();
               
      }catch(SQLException e) {
        // connection close failed.
        System.err.println(e);
      }        
    }
    
    /**
     * Genera una clave unica de 40 caracteres
     * @param input
     * @return 
     */
    private String sha1(String input) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ControllerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    public void insert(String sql) {
        try {
            this.connect();
            this.statement.execute(sql);
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(ControllerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void registerUser(String nombre, String clave, String email) {
        try {
            String sql = "INSERT INTO Usuario (nombre, clave, email) VALUES('" + nombre + "','" + sha1(clave) + "','" + email + "')";
            this.connect();
            this.statement.execute(sql);
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(ControllerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean loginUser(String name, String password) {
        try {
            String sql = "SELECT id, nombre, clave FROM Usuario WHERE nombre='" + name + "' AND clave='" + sha1(password) + "'";
            this.connect();
            ResultSet rs = statement.executeQuery(sql);
            
            if(rs != null) {
                rs.next();
                if(rs.getString("nombre").equals(name) && rs.getString("clave").equals(sha1(password))) {
                    this.disconnect();
                    return true;
                }
            }            
            rs.close();
        } catch (SQLException ex) {
            //Logger.getLogger(ControllerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    public int getUserId(String strName, String strPassword) {
         int id = 0;
         try {
            String sql = "SELECT id FROM Usuario WHERE nombre='" + strName + "' AND clave='" + sha1(strPassword) + "'";      
            this.connect();
            ResultSet rs = statement.executeQuery(sql);
            
            if(rs != null) {
                rs.next();
                id = rs.getInt("id");
                
                rs.close();
                this.disconnect();
                return id;
            }            
            
        } catch (SQLException ex) {
            //Logger.getLogger(ControllerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public void saveMessage(int toUserID, int fromUserID, String messaje) {
        try {
            Date date = new Date();
            Object date_time = new Timestamp(date.getTime());
            System.out.println("Date-Time: " + date_time);
                        
            String sql = "INSERT INTO Mensaje (to_user_id, from_user_id, text, date_time) "
                    + "VALUES(" + toUserID + "," + fromUserID + ", '" + messaje + "', '" + date_time + "')";
            
            this.connect();
            this.statement.execute(sql);
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(ControllerDB.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }     
}