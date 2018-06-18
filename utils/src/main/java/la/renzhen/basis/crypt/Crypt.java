package la.renzhen.basis.crypt;

/**
 * 机密解密算法汇总
 *
 * @author zhouhaichao(a)2008.sina.com
 * @version 1.0 &amp; 22:52　2016/1/30
 */
public interface Crypt {

    /**
     * 加密
     *
     * @param data 明文数据
     * @return 密文
     */
    public String encrypt(String data) ;

    /**
     * 加密
     *
     * @param data 明文数据
     * @return 秘文
     */
    public byte[] encrypt(byte[] data);


    /**
     * 解密
     *
     * @param data 秘文
     * @return 明文
     */
    public String decrypt(String data);

    /**
     * 解密
     *
     * @param data 秘文数据
     * @return 明文
     */
    public byte[] decrypt(byte[] data);


}
