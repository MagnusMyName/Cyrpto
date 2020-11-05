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

    // Inicializa atributos y lanza el efecto avalancha
    public AvalancheToKey(int muestras) {
        descbcmac = new DESCBCMAC();
        // Input aleatorio de 128 bytes
        input = new byte[128];
        new Random().nextBytes(input);
        // Histograma. 33 elementos porque la distancia de Hamming
        // podra tomar 33 posibles valores (el bloque de salida tiene 32 bits)
        histogram = new Histogram(33, muestras);
        startAvalanche(muestras);
    }

    // Repite el efecto avalancha n veces modificando la entrada en 1 bit aleatorio
    public void startAvalanche(int n) {
        for (int i = 0; i < n; i++){
            byte[] key1 = createRandomKey();
            byte[] key2 = flipRandomBit(key1);
            if (key2.length < 8){
                byte[] key3 = new byte[8];
                for(int j = 0; j < key2.length; j++){
                    key3[j] = key2[j];
                }
                avalanche(key1, key3);
            }else{
                avalanche(key1, key2);
            }
        }
    }

    public byte[] createRandomKey(){
        // Crea una clave aleatoria de 64 bits
        byte[] key = new byte[8];
        new Random().nextBytes(key);
        return key;
    }

    // Complementa un bit aleatorio en un array de bytes y devuelve el array de bytes
    // con el bit cambiado
    public byte[] flipRandomBit(byte[] bytes){
        byte[] res = bytes.clone();
        int randomBit = ThreadLocalRandom.current().nextInt(0, 7);
        int randomByte = ThreadLocalRandom.current().nextInt(0, 7);
        res[randomByte] ^= 1 << randomBit;
        return res;
    }


    // Añade al histograma los valores de efecto avalancha y probabilidad para
    // un caso de estudio
    public void avalanche(byte[] key1, byte[] key2) {
        byte[] output1 = descbcmac.encode(key1, input);
        byte[] output2 = descbcmac.encode(key2, input);
        int hD = getHammingDistance(output1, output2);
        histogram.addData(hD);
    }

    // Calcula la distancia de Hamming para un caso de estudio
    public int getHammingDistance(byte[] a, byte[] b){
        int res = 0;
        byte[] xnor = new byte[a.length];
        for (int i = 0; i < xnor.length; i++)
            xnor[i] = (byte) ((a[i] & b[i]) | (~a[i] & ~b[i]));
        for (int j = 0; j < xnor.length; j++){
            for (int k = 0; k < 8; k++){
                if ((xnor[j] & (1 << k)) == 0)
                    res++;
            }
        }
        return res;
    }

    // Metodos para mostrar por pantalla los valores del histograma
    public void printHistogram(){
        histogram.print();
    }
}
