package TripleDes;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

class Decryptor {

    private static String outputFilePath = "C:\\Users\\SONY\\Desktop\\denkripsi3des.txt";

    public static void main(String[] args) {
        FileOutputStream fos = null;
        File file = new File(outputFilePath);
        String keyString = "012345678";
        String algorithm = "DESede";
        try {
            File inputFileNAme = new File("C:\\Users\\SONY\\Desktop\\enkripsie3des.txt");
            FileInputStream fileInputStream = new FileInputStream(inputFileNAme);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
            SecretKey secretKey = getKey(keyString);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            ObjectInputStream objectInputStream = new ObjectInputStream(new CipherInputStream(fileInputStream, cipher));
            System.out.println(objectInputStream.available());
            fileOutputStream.write((byte[]) objectInputStream.readObject());
            fileOutputStream.flush();
            fileOutputStream.close();
            fileInputStream.close();
            objectInputStream.close();
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
