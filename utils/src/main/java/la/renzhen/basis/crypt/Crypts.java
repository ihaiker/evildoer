package la.renzhen.basis.crypt;

/**
 * @author zhouhaichao(a)2008.sina.com
 * @version 1.0 &amp; 15:22　2016/1/31
 */
public class Crypts {
    public static final Crypt DES3 = new Des3Crypt("Des3 Crypt Key .", "XCryptV1");
    public static final Crypt AES = new AESCrypt("XCenter Aes Crypt Key .");
    public static final Crypt RSA = new RSACrypt(RSACrypt.RSA_PUBLIC_KEY, RSACrypt.RSA_PRIVATE_KEY);
}
