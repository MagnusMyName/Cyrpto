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
    public static String pathPlain = "C:\\Users\\SONY\\Desktop\\Plain.txt";
    public static String pathEncryptDES = "C:\\Users\\SONY\\Desktop\\enkrpsi.txt";
    public static String pathEncrypt3DES = "C:\\Users\\SONY\\Desktop\\enkripsie3des.txt";
    public static String pathEncryptDESede = "C:\\Users\\SONY\\Desktop\\enkrpsidesede.txt";

    public static void main(String[] args) {
        System.out.println(getHammingDistance(pathPlain.getBytes(), pathEncryptDESede.getBytes()));
    }

    public static int getHammingDistance(byte[] a, byte[] b) {
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

//    public void inputan(byte[] input1, byte[] input2) throws FileNotFoundException, IOException {
//
//        byte[] output1 = null;
//        byte[] output2 = null;
//        int hD = getHammingDistance(output1, output2);
//    }
    public void printHistogram() {
        histogram.print();
    }
}
