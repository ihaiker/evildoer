package la.renzhen.basis.crypt;


import la.renzhen.basis.codes.Base64;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author zhouhaichao(a)2008.sina.com
 * @version 1.0 &amp;　16:41　2016/1/31
 */
public class RSACrypt implements Crypt {
    public static final String RSA_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxalpPw7/4fu9D2wjLRFb61LzgdJ7HNmQwtsfr2ntkq/N8yumgKUUdNhZLyFAOStWhBszRqrYC4gcc/wDCXTlL+YsBqM39SjaGZqdx9IN7IA+aNp9hcsI3J7rjbmF+lkMZv3itK45lupSmoAL84AFJpvz2f8dfVFf9Le9Q1gAlPpVdV/vOVn1xbc1Yqg1Z5tUm5MQGMLF7/92QxlU4DlDACJ6Vvnfu57mxECJ1qFyXPX6wyTMZpkX6SdYSrFAUZRfnIxrzVOR9BloIN5Mql3gAmBXH1Jl5A1ahdnz5RRsKI7CqCY/ad2z3bKM2nstwCPam02fcNZfrGRmxUF1dYC3WwIDAQAB";
    public static final String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDFqWk/Dv/h+70PbCMtEVvrUvOB0nsc2ZDC2x+vae2Sr83zK6aApRR02FkvIUA5K1aEGzNGqtgLiBxz/AMJdOUv5iwGozf1KNoZmp3H0g3sgD5o2n2FywjcnuuNuYX6WQxm/eK0rjmW6lKagAvzgAUmm/PZ/x19UV/0t71DWACU+lV1X+85WfXFtzViqDVnm1SbkxAYwsXv/3ZDGVTgOUMAInpW+d+7nubEQInWoXJc9frDJMxmmRfpJ1hKsUBRlF+cjGvNU5H0GWgg3kyqXeACYFcfUmXkDVqF2fPlFGwojsKoJj9p3bPdsozaey3AI9qbTZ9w1l+sZGbFQXV1gLdbAgMBAAECggEARRs62bojyl2aNwcmsT4NdhP1YOE2+9oxG39C77BISG3AwpDzUKzmnwd4/tpQOuyk/eEIp19Z04ZN9FsasLa23m1J3TQ5fzQ+NI9zNIaDKbmYmE4zbpCS05eYYV+IsQNrK6L+HQ+AwPvBz5SPGR4cLuh9dC0WoVbPBp8qw53D7lGOHzGJeYVIqH4gBjDKuSAwZT0QpXXFg5AQLn1xy9uHi/M7XekfVIpnPkddkHdleCx+mPRZqRUENAfhaomH66rtA39z3hrNpwu/k2JETYkWhjFEVT7hF4Q8IBXzoH/qjN33ysNpNVu7bXBQT28bkZRrR5+i4JuoYS8BuA3ObhKnkQKBgQDp6WXJ2nvz3MO+EdTn3DhZZlpJOYSuz7qyLILk6ajYAwaBnT6wPFTNYnV5kNc4ww98RC1+AE4KYgSnkEazJ5fK1ywMHktFnNp8O5YUN+iiP8+aQ4miU25HPmdteyGqJ3yNoYXavZ5RIu3LY3cRMBT7RFln3Gr+O0JW5o38cc6AAwKBgQDYU7Q9+77pbt6RPniA3JGFWn/7Um2p+erfkR9vCym2x2eS3MkgWc7bBQIissH4FtswngP6+rnfI2g2ifX4a3Ah+l0Ek0h6FWDVt2LtPvKNtW26UQzsvjmE/cBbdgI7QLGfe3rAtHXPbUQ3dmtTLakI7Ij0IKF9edrIAaeRgZ9nyQKBgQDHwUDi24llHQ+wXowCIYehmQ3bYJpVqyJVjDuP/5boOdUxlTZ2zF7jMoT99tpFKBcuWQ68fsgmh8RP0J7/2f0ABTchdjFz1lqjI0OsKn6pKHNC2xBLppITl1A+J5v9MlFkph6oSaxMv9ZxDZUeAYC5f4oWbx6T08l4atWwUwQ1PwKBgDR5MVprq2aCPziqF57iYeYgCrVSzEkT1zY9xIsyAP9WJTiJl7viLPwRne/+vnBIUwja+4owsU+ADjJUirakkQGc8l0+wLDkaKXOow97WminXqN4L5NpnRg2WSeWW3o1+h/+WstNutoC3i456lS12a/ReHMHS0TdfwVO6W+xKpKJAoGAHZy8p9Ds3NRa/7I21wWYU3gpR6y2P1ZWkqH7SfCTf4+k5Ee7CHxnag8gD0mEMb8EC1WfmPXowiNJIBWmv0Jegy3oTsU0ZI668wlKeM3E2hiAOanvSoKDRrWsRiKsZOB0fyplOKtnBltS0BPlDF5mcZap8sW7n2CPrv9EQaVKxao=";

    private byte[] privateBytes;
    private byte[] publicBytes;

    private Cipher privateCipher, publicCipher;

    /**
     * @param publicKey  RSA公钥文件字节数组
     * @param privateKey RSA密钥文件字节数组
     */
    public RSACrypt(byte[] publicKey, byte[] privateKey) {
        this.publicBytes = publicKey;
        this.privateBytes = privateKey;

        try {
            this.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param publicKey  RSA公钥文件Base64码
     * @param privateKey RSA密钥文件Base64码
     */
    public RSACrypt(String publicKey, String privateKey) {
        publicBytes = Base64.decode2bytes(publicKey);
        privateBytes = Base64.decode2bytes(privateKey);

        try {
            this.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param publicKey  私钥文件流
     * @param privateKey 公钥文件流
     * @throws IOException e
     */
    public RSACrypt(InputStream publicKey, InputStream privateKey) throws IOException {
        privateBytes = new byte[privateKey.available()];
        privateKey.read(privateBytes);
        publicBytes = new byte[publicKey.available()];
        publicKey.read(publicBytes);

        try {
            this.init();
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    protected void init() throws Exception {

        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey pkPublic = kf.generatePublic(publicKeySpec);
        publicCipher = Cipher.getInstance("RSA");
        publicCipher.init(Cipher.ENCRYPT_MODE, pkPublic);


        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        privateCipher = Cipher.getInstance("RSA");
        privateCipher.init(Cipher.DECRYPT_MODE, privateKey);
    }

    @Override
    public String encrypt(String data) {
        return Base64.encode(encrypt(data.getBytes()));
    }

    @Override
    public byte[] encrypt(byte[] data) {
        try {
            return publicCipher.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String decrypt(String data) {
        return new String(decrypt(Base64.decode2bytes(data)));
    }

    @Override
    public byte[] decrypt(byte[] data) {
        try {
            return privateCipher.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }
}
