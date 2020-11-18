/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DES;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import stats.Histogram;

/**
 *
 * @author SONY
 */
public class avalanche {

    private Histogram histogram;
    public String pathPlain = "C:\\Users\\SONY\\Desktop\\Plain.txt";
    public String pathEncryptDES = "C:\\Users\\SONY\\Desktop\\enkrpsi.txt";
    public String pathEncrypt3DES = "C:\\Users\\SONY\\Desktop\\enkripsie3des.txt";
    public String pathPlain2 = "C:\\Users\\SONY\\Desktop\\Plain2.txt";
//    public String pathEncryptDESede = "C:\\Users\\SONY\\Desktop\\enkrpsidesede.txt";

    public avalanche() {
        String text = readFileAsString(pathPlain);
        String text2 = readFileAsString(pathEncryptDES);
        String text3 = readFileAsString(pathEncrypt3DES);
//        String text4 = readFileAsString(pathEncryptDESede);
        String text5 = readFileAsString(pathPlain2);
//        System.out.println("Plain to DES Byte Change : " + getHammingDistance(text.getBytes(), text2.getBytes()));
//        System.out.println("Plain to 3DES Byte Change : " + getHammingDistance(text.getBytes(), text3.getBytes()));
         System.out.println("Plain to DES Byte Change : " + getHammingDistance(text5.getBytes(), text2.getBytes()));
        System.out.println("Plain to 3DES Byte Change : " + getHammingDistance(text5.getBytes(), text3.getBytes()));
//          System.out.println("Plain to DESede Byte Change : " + getHammingDistance(text.getBytes(), text4.getBytes()));

    }

    public int getHammingDistance(byte[] a, byte[] b) {
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
//    public void inputan(byte[] input1, byte[] input2) throws FileNotFoundException, IOException {
//
//        byte[] output1 = null;
//        byte[] output2 = null;
//        int hD = getHammingDistance(output1, output2);
//    }

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
