package cipher;

import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.params.KeyParameter;

public class DESCBCMAC {

    public DESCBCMAC() {
    }

    // Encrypts input and returns the result as an array of bytes
    public byte[] encode(byte[] key, byte[] input) {
        // The output block of the algorithm is always 32 bits
        byte[] output = new byte[4];
        KeyParameter keyParameter = new KeyParameter(key);
        DESEngine desEngine = new DESEngine();
        CBCBlockCipherMac cbcBlockCipherMac = new CBCBlockCipherMac(desEngine);
        cbcBlockCipherMac.init(keyParameter);
        cbcBlockCipherMac.update(input, 0, input.length);
        cbcBlockCipherMac.doFinal(output, 0);
        
        return output;
    }

}
