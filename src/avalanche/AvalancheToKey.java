package avalanche;

import cipher.DESCBCMAC;
import stats.Histogram;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by victor on 24/06/17.
 */
public class AvalancheToKey {

    private DESCBCMAC descbcmac;
    private Histogram histogram;
    private byte[] input;

    // Initialize attributes and launch the avalanche effect
    public AvalancheToKey(int muestras) {
        descbcmac = new DESCBCMAC();
        // Input aleatorio de 128 bytes
        input = new byte[128];
        new Random().nextBytes(input);
        // Histogram. 33 elements because the Hamming distance
        // it could take 33 possible values ​​(the output block has 32 bits)
        histogram = new Histogram(33, muestras);
        startAvalanche(muestras);
    }

    // Repeat the avalanche effect n times modifying the input by 1 random bit
    public void startAvalanche(int n) {
        for (int i = 0; i < n; i++) {
            byte[] key1 = createRandomKey();
            byte[] key2 = flipRandomBit(key1);
            if (key2.length < 8) {
                byte[] key3 = new byte[8];
                for (int j = 0; j < key2.length; j++) {
                    key3[j] = key2[j];
                }
                avalanche(key1, key3);
            } else {
                avalanche(key1, key2);
            }
        }
    }

    public byte[] createRandomKey() {
        // Create a random 64-bit key
        byte[] key = new byte[8];
        new Random().nextBytes(key);
        return key;
    }

    // Complements a random bit in an array of bytes and returns the array of bytes
    // with bit changed
    public byte[] flipRandomBit(byte[] bytes) {
        byte[] res = bytes.clone();
        int randomBit = ThreadLocalRandom.current().nextInt(0, 7);
        int randomByte = ThreadLocalRandom.current().nextInt(0, 7);
        res[randomByte] ^= 1 << randomBit;
        return res;
    }

    // Add the avalanche and probability effect values ​​to the histogram for
    // a case study
    public void avalanche(byte[] key1, byte[] key2) {
        byte[] output1 = descbcmac.encode(key1, input);
        byte[] output2 = descbcmac.encode(key2, input);
        int hD = getHammingDistance(output1, output2);
        histogram.addData(hD);
    }

    // Calculate Hamming distance for a case study
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

    //Methods for displaying histogram values ​​on the screen
    public void printHistogram() {
        histogram.print();
    }
}
