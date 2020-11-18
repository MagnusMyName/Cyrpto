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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import stats.Histogram;

public class Chiper {

    private Histogram histogram;
    public byte[] bit;
    public String key = "12345678";
    public String pathPlain = "C:\\Users\\SONY\\Desktop\\Plain.txt";
    public String pathEncrypt = "C:\\Users\\SONY\\Desktop\\enkrpsi.txt";

    public Chiper() {
        String text = readFileAsString(pathPlain);
        startAvalanche(2000, text.getBytes());
        System.out.println("Enkripsi selesai");
    }

    public byte[] encryptDecrypt(String key, int chiperMode, FileInputStream in)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, IOException {

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
//        File output = new File(pathEncrypt);
//        FileOutputStream fos = new FileOutputStream(output);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = skf.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        if (chiperMode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, SecureRandom.getInstance("SHA1PRNG"));
            CipherInputStream cis = new CipherInputStream((in), cipher);
//            write(cis, fos);
            return cis.toString().getBytes();
        }
//        else if (chiperMode == Cipher.DECRYPT_MODE) {
//            cipher.init(Cipher.DECRYPT_MODE, secretKey, SecureRandom.getInstance("SHA1PRNG"));
//            CipherOutputStream cos = new CipherOutputStream((fos), cipher);
//            write(fis, cos);
//            return cos.toString().getBytes();
//        }
        return null;
    }

    private void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[64];
        int numOfByteRead;
        while ((numOfByteRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, numOfByteRead);
        }
        out.close();
        in.close();

    }

    public void startAvalanche(int n, byte[] plainByte) {
        for (int i = 0; i < n; i++) {
            byte[] input1 = plainByte;

            byte[] input2 = flipRandomBit(input1);

            System.out.println("");
            avalanche(input1, input2);
        }
    }

    //Complements a random bit in an array of bytes and returns the array of bytes
    // with bit changed
    public byte[] flipRandomBit(byte[] bytes) {
        byte[] res = bytes.clone();
        int randomBit = ThreadLocalRandom.current().nextInt(0, 7);
        int randomByte = ThreadLocalRandom.current().nextInt(0, 7);
        res[randomByte] ^= 1 << randomBit;

        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\SONY\\Desktop\\random.txt")) {

            byte[] mybytes = res;

            fos.write(mybytes);
        } catch (IOException e) {

        }
        return res;
    }

    // AñAdd the avalanche effect and probability values ​​to the histogram for
    // a case study
    public void avalanche(byte[] input1, byte[] input2) {
//        byte[] output1 = descbcmac.encode(key, input1);
//        byte[] output2 = descbcmac.encode(key, input2);
        byte[] output1 = null;
        byte[] output2 = null;
        try {
            File inFile = new File(pathPlain);
            File raFile = new File("C:\\Users\\SONY\\Desktop\\random.txt");

            FileInputStream fis = new FileInputStream(inFile);
            FileInputStream fos = new FileInputStream(raFile);

            output1 = encryptDecrypt(key, Cipher.ENCRYPT_MODE, fis);
            output2 = encryptDecrypt(key, Cipher.ENCRYPT_MODE, fos);
            System.out.println(output1.length);
            System.out.println(output2.length);
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
                | IOException e) {
            e.printStackTrace();
        }
        int hD = getHammingDistance(output1, output2);
//        System.out.println(hD);
//        histogram.addData(hD);
    }

    // Calculate Hamming distance for a case study
    public int getHammingDistance(byte[] a, byte[] b) {
//        System.out.println("");
//        System.out.println(a.length);
//        System.out.println(b.length);
//        System.out.println("");
        int res = 0;
        byte[] xnor = new byte[a.length];
        for (int i = 0; i < xnor.length; i++) {
            xnor[i] = (byte) ((a[i] & b[i]) | (~a[i] & ~b[i]));
        }
        for (int j = 0; j < xnor.length; j++) {
            for (int k = 0; k < 8; k++) {
                if ((xnor[j] & (1 << k)) == 0) {
                    res++;
                }
            }
        }
        return res;
    }

    // Methods for displaying histogram values ​​on the screen
    public void printHistogram() {
        histogram.print();
    }

    public String readFileAsString(String fileName) {
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
