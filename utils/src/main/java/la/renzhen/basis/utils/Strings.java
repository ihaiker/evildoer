package la.renzhen.basis.utils;

import com.google.common.base.Ascii;
import com.google.common.base.CaseFormat;

import java.util.Random;

/**
 * String工具类<p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 17/06/2018 10:06 PM
 */
public class Strings {
    private static final char[] EFFECTIVE_CHARACTER;
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    static {
        EFFECTIVE_CHARACTER = new char[26 + 26 + 10];
        int n = 0;
        for (char i = 'a'; i <= 'z'; i++) {
            EFFECTIVE_CHARACTER[n++] = i;
        }
        for (char i = 'A'; i <= 'Z'; i++) {
            EFFECTIVE_CHARACTER[n++] = i;
        }
        for (char i = '0'; i <= '9'; i++) {
            EFFECTIVE_CHARACTER[n++] = i;
        }
    }

    /**
     * 产生一个长度固定的字符串
     *
     * @param length 随机字符传的长度
     * @return 随机字符串
     */
    public static String random(int length) {
        return random(EFFECTIVE_CHARACTER, length);
    }

    /**
     * 使用给定字符生生一个长度固定的字符串
     *
     * @param seed   指定字符
     * @param length 指定生成长度
     * @return 随机字符串
     */
    public static String random(char[] seed, int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(seed[RANDOM.nextInt(seed.length)]);
        }
        return sb.toString();
    }

    public static String firstCharToUpper(String word) {
        return word.isEmpty() ? word : (new StringBuilder(word.length())).append(Ascii.toUpperCase(word.charAt(0))).append(word.substring(1)).toString();
    }

    public static String firstCharToLower(String word) {
        return word.isEmpty() ? word : (new StringBuilder(word.length())).append(Ascii.toLowerCase(word.charAt(0))).append(word.substring(1)).toString();
    }

    /**
     * 驼峰命名变成下划线命名
     *
     * @param name 名称
     * @return 下划线名称
     */
    public static String underScore(String name) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }

    /**
     * 返驼峰命名
     *
     * @param bb 下划线命名
     * @return 驼峰命名法
     */
    public static String unUnderScore(String bb) {
        return CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE).reverse().convert(bb);
    }

    public static boolean isNullOrEmpty(String str){
        return str == null || "".equals(str.trim());
    }

    /**
     * @param s1 判定字符串
     * @param s2 为空后返回字符串
     * @return string 第一个为空返回第二个
     */
    public static String or(String s1, String s2) {
        if (s1 == null || "".equals(s1.trim())) {
            return s2;
        }
        return s1;
    }

    /**
     * 修剪字符串，如果字符串含有空格修剪，如果null返回空字符串
     *
     * @param a 需要修剪的字符串
     * @return 返回修剪结果
     */
    public static String empty(String a) {
        if (a == null) {
            return "";
        }
        return a.trim();
    }

    /**
     * 安全截取操作<p>
     *
     * @param str   需要截取的字符串
     * @param start 截取开始字段
     * @param end   截取结束字段
     * @return 截取的字符串
     */
    public static String sub(String str, int start, int end) {
        if (str == null || "".equals(str)) {
            return "";
        }
        int length = str.length();
        if (start > length - 1) {
            return "";
        }
        if (end == 0 || end > length) {
            end = length - 1;
        } else if (end < 0) {
            end = length + end;
        }

        if (start >= end) {
            return "";
        }

        return str.substring(start, end);
    }
}
