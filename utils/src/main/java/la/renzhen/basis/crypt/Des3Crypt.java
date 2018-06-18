package la.renzhen.basis.crypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.Charset;
import java.security.Key;

/**
 * 加密Key必须大于 {@code DESedeKeySpec.DES_EDE_KEY_LEN}
 * IvParamter 必须为8位
 */
public class Des3Crypt extends AbstractCrypt {

    public Des3Crypt(String password, String ivParameter) {
        super(password, ivParameter);
    }

    public Des3Crypt(String password, String ivParameter, Charset charset) {
        super(password, ivParameter, charset);
    }

    @Override
    protected Cipher getCipher() throws Exception {
        return Cipher.getInstance("desede/CBC/PKCS5Padding");
    }

    @Override
    protected Key generatorKey(String secretKey) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        return keyFactory.generateSecret(spec);
    }


}
