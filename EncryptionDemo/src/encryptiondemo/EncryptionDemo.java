package encryptiondemo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Encryption/decryption demo.
 *
 * @author skorkmaz
 */
public class EncryptionDemo {

    private static final String ENCRYPTION_METHOD = "AES";

    public static void main(String[] args) {
        try {
            KeyGenerator keygenerator = KeyGenerator.getInstance(ENCRYPTION_METHOD);
            SecretKey myDesKey = keygenerator.generateKey();

            Cipher desCipher = Cipher.getInstance(ENCRYPTION_METHOD);

            String originalStr = "No body can see me.";
            System.out.println("Original string : " + originalStr);

            byte[] originalStrBytes = originalStr.getBytes("UTF8");

            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            byte[] encryptedStrBytes = desCipher.doFinal(originalStrBytes);

            System.out.println("Encrypted string: " + new String(encryptedStrBytes));

            desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
            byte[] decryptedStrBytes = desCipher.doFinal(encryptedStrBytes);

            String decryptedStr = new String(decryptedStrBytes);
            System.out.println("Decrypted string: " + decryptedStr);
            if (decryptedStr.equals(originalStr)) {
                System.out.println("PASS");
            } else {
                System.out.println("FAIL");
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println(e);
        }
    }

}
