package za.charurama.logistics.common;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class LoginCipher {

    public static String decrpty(String enc){
        try
        {
            String key = "fire129745St$145"; // 128 bit key
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            // now convert the string to byte array
            // for decryption
            byte[] bb = new byte[enc.length()];
            for (int i=0; i<enc.length(); i++) {
                bb[i] = (byte) enc.charAt(i);
            }

            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(bb));
            return decrypted;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String encrypt(String text)
    {
        try
        {
            String key = "fire129745St$145"; // 128 bit key
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b: encrypted) {
                sb.append((char)b);
            }
            // the encrypted String
            String enc = sb.toString();
            return enc;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
