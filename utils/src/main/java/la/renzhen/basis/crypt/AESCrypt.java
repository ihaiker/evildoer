package la.renzhen.basis.crypt;

import com.google.common.base.Charsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.spec.KeySpec;

public class AESCrypt extends AbstractCrypt {

    //生成密钥需要迭代的次数，不用太多，废性能
    static int ITERATION_COUNT = 7;
    static int KEY_LENGTH = 128;
    static byte[] SALT = {0, 7, 2, 3, 4, 5, 6, 7, 8, 1, 0xA, 0xB, 0xE, 0xD, 0xE, 0xF}; //需要转16进制

    public AESCrypt(String password) {
        this(password, Charsets.UTF_8);
    }

    public AESCrypt(String password, Charset charset) {
        super(password, null, charset);
    }

    @Override
    protected Cipher getCipher() throws Exception {
        return Cipher.getInstance("AES/ECB/PKCS5Padding");
    }

    @Override
    protected Key generatorKey(String secretKey) throws Exception {
        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), SALT, ITERATION_COUNT, KEY_LENGTH);
        SecretKey sk = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec);
        return new SecretKeySpec(sk.getEncoded(), "AES");
    }
}
