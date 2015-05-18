package client.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Guerino
 */
public class Encryptor {
    final static int padSize = 256;
    final static String myKey = "1234567890ABCDEF0123456789ABCDEF";
    final static String myIV = "89ABCDEF0123456789ABCDEF01234567";          
    
    public static String encript(String text) {
        try {
            //Aqui ciframos el mensaje enviado al servidor
            final LSLAESCrypto aes = new LSLAESCrypto(LSLAESCrypto.LSLAESCryptoMode.CFB,
                    LSLAESCrypto.LSLAESCryptoPad.NONE, padSize, myKey, myIV);
            
            return aes.encrypt(text);
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    public static String decrypt(String text){
        
        try {
            //Aqui ciframos el mensaje enviado al servidor
            final LSLAESCrypto aes = new LSLAESCrypto(LSLAESCrypto.LSLAESCryptoMode.CFB,
                    LSLAESCrypto.LSLAESCryptoPad.NONE, padSize, myKey, myIV);
            
            return aes.decrypt(text);
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
}
