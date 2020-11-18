package DES;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SONY
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Chiper2 {

    public static void encryptDecrypt(String key, int chiperMode, File in, File out)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, IOException {
        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());

        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = skf.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        if (chiperMode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, SecureRandom.getInstance("SHA1PRNG"));
            CipherInputStream cis = new CipherInputStream((fis), cipher);
            write(cis, fos);
        } else if (chiperMode == Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, SecureRandom.getInstance("SHA1PRNG"));
            CipherOutputStream cos = new CipherOutputStream((fos), cipher);
            write(fis, cos);
        }
    }

    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[64];
        int numOfByteRead;
        while ((numOfByteRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, numOfByteRead);
        }
        out.close();
        in.close();

    }

    public static void main(String[] args) {
        File encrypted = new File("C:\\Users\\SONY\\Desktop\\Plain.txt");
        File decrypted = new File("C:\\Users\\SONY\\Desktop\\enkrpsi.txt");

        try {
            encryptDecrypt("12345678", Cipher.ENCRYPT_MODE, encrypted, decrypted);
            System.out.println("enkripsi selesai");
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
                | IOException e) {
            e.printStackTrace();
        }
    }
}
