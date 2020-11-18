package TripleDes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {

    private static String inputFilePath = "C:\\Users\\SONY\\Desktop\\Plain2.txt";

    public static void main(String[] args) {
        FileOutputStream fos = null;
        File file = new File(inputFilePath);
        String keyString = "123456781122334412345678";

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] fileByteArray = new byte[fileInputStream.available()];
            fileInputStream.read(fileByteArray);
            for (byte b : fileByteArray) {
                System.out.println(b);
            }
            SecretKey secretKey = getKey(keyString);
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, SecureRandom.getInstance("SHA1PRNG"));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new CipherOutputStream(
                    new FileOutputStream("C:\\Users\\SONY\\Desktop\\enkripsie3des.txt"), cipher));
            objectOutputStream.writeObject(fileByteArray);
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SecretKey getKey(String message) throws Exception {
        String messageToUpperCase = message.toUpperCase();
        byte[] digestOfPassword = messageToUpperCase.getBytes();
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        return key;
    }
}
