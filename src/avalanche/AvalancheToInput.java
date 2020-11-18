package avalanche;

import DES.Chiper;
import cipher.DESCBCMAC;
import stats.Histogram;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AvalancheToInput {


    private DESCBCMAC descbcmac;
    private Histogram histogram;
    private byte[] key;

    // Initialize attributes and launch the avalanche effect
    public AvalancheToInput(int sample) {
        descbcmac = new DESCBCMAC();
        // 64-bit random key
        key = new byte[8];
        new Random().nextBytes(key);
        // Histogram. 33 elements because the Hamming distance
        // it could take 33 possible values ​​(the output block has 32 bits)
        histogram = new Histogram(33, sample);
        startAvalanche(sample);
    }

    // Repeat the avalanche effect n times modifying the input by 1 random bit
    public void startAvalanche(int n) {
        for (int i = 0; i < n; i++) {
            byte[] input1 = createRandomInput();
            byte[] input2 = flipRandomBit(input1);
            avalanche(input1, input2);
        }
    }

    public byte[] createRandomInput() {
        // Creates a random 128-byte entry because
        // the algorithm does not specify input block size
        byte[] input = new byte[128];
        new Random().nextBytes(input);
        return input;
    }

    //Complements a random bit in an array of bytes and returns the array of bytes
    // with bit changed
    public byte[] flipRandomBit(byte[] bytes) {
        byte[] res = bytes.clone();
        int randomBit = ThreadLocalRandom.current().nextInt(0, 7);
        int randomByte = ThreadLocalRandom.current().nextInt(0, 7);
        res[randomByte] ^= 1 << randomBit;
        return res;
    }

    // AñAdd the avalanche effect and probability values ​​to the histogram for
    // a case study
    public void avalanche(byte[] input1, byte[] input2) {
        byte[] output1 = descbcmac.encode(key, input1);
        byte[] output2 = descbcmac.encode(key, input2);
        int hD = getHammingDistance(output1, output2);
        histogram.addData(hD);
    }

    // Calculate Hamming distance for a case study
    public int getHammingDistance(byte[] a, byte[] b) {
        int res = 0;
//         System.out.println("");
////        System.out.println(a.length);
////        System.out.println(b.length);
//        System.out.println("");
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

}
