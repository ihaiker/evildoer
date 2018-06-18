package la.renzhen.basis.crypt;

import com.google.common.base.Charsets;
import la.renzhen.basis.codes.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.Charset;
import java.security.Key;

public abstract class AbstractCrypt implements Crypt {

    Charset charset;
    Key key;
    Cipher encryptCipher, decryptCipher;

    public AbstractCrypt(String password) {
        this(password, "XCryptV!", Charsets.UTF_8);
    }

    public AbstractCrypt(String password, String ivParameter) {
        this(password, ivParameter, Charsets.UTF_8);
    }

    public AbstractCrypt(String password, String ivParameter, Charset charset) {
        this.charset = charset;
        try {
            this.key = generatorKey(password);
            this.encryptCipher = cipher(ivParameter, Cipher.ENCRYPT_MODE);
            this.decryptCipher = cipher(ivParameter, Cipher.DECRYPT_MODE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private Cipher cipher(String ivParamter, int mode) throws Exception {
        Cipher cipher = getCipher();
        if (ivParamter == null) {
            cipher.init(mode, key);
        } else {
            IvParameterSpec ips = new IvParameterSpec(ivParamter.getBytes());
            cipher.init(mode, key, ips);
        }
        return cipher;
    }

    protected abstract Cipher getCipher() throws Exception;

    protected abstract Key generatorKey(String secretKey) throws Exception;

    @Override
    public String encrypt(String data) {
        byte[] encryptData = encrypt(data.getBytes(charset));
        return Base64.encode(encryptData);
    }

    @Override
    public byte[] encrypt(byte[] data) {
        try {
            return encryptCipher.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String decrypt(String data) {
        return new String(decrypt(Base64.decode(data.getBytes())), charset);
    }

    @Override
    public byte[] decrypt(byte[] data) {
        try {
            return decryptCipher.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }
}
