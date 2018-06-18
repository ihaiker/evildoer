package la.renzhen.basis.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 2017/9/28 下午2:06
 */
public class Fn {

    /**
     * 如果a获取失败，使用b获取，最后提交给消费者消费（a失败）
     *
     * @param t
     * @param a
     * @param b
     * @param consumers
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R ifGetNull(T t, Function<T, R> a, Function<T, R> b, Consumer<R>... consumers) {
        R r = a.apply(t);
        if (r == null) {
            r = b.apply(t);
            for (Consumer<R> consumer : consumers) {
                consumer.accept(r);
            }
        }
        return r;
    }

    public static <T, R> R ifNullC(T a, T b, Function<T, R> convert) {
        T c = Optional.ofNullable(a).orElse(b);
        R r = convert.apply(c);
        return r;
    }

    public static <Key, Value> Map<Key, Value> map(Key key, Value val, Object... obj) {
        Map<Key, Value> map = new HashMap<>();
        map.put(key, val);
        for (int i = 0; i < obj.length; i += 2) {
            map.put((Key) obj[i], (Value) obj[i + 1]);
        }
        return map;
    }

    public static <T> T ifNull(T a, T... b) {
        if (a == null) {
            for (T t : b) {
                if (t != null) {
                    return t;
                }
            }
        }
        return a;
    }

    public static String ifEmpty(String a, String... b) {
        if (a == null || "".equals(a.trim())) {
            for (String t : b) {
                if (t != null && !"".equals(t.trim())) {
                    return t;
                }
            }
        }
        return a;
    }

    /**
     * @param map
     * @param key    键
     * @param defVal
     * @param valFn  值得二次处理
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> V mapGetSet(Map<K, V> map, K key, V defVal, Function<V, V> valFn, Consumer<V>... consumers) {
        V val = map.getOrDefault(key, defVal);
        V newVal = valFn.apply(val);
        map.put(key, newVal);
        for (Consumer<V> consumer : consumers) {
            consumer.accept(newVal);
        }
        return newVal;
    }

    /**
     * 合并两个map
     *
     * @param r1
     * @param r2
     * @param function
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> merge(Map<K, V> r1, Map<K, V> r2, BiFunction<V, V, V> function) {
        Map<K, V> map = new HashMap<K, V>();
        for (Map.Entry<K, V> entry : r1.entrySet()) {
            K k = entry.getKey();
            V v1 = entry.getValue();
            V v2 = r2.get(k);
            V v3;
            if (v2 == null) {
                v3 = v1;
            } else {
                v3 = function.apply(v1, v2);
            }
            map.put(k, v3);
            r2.remove(k);
        }
        for (Map.Entry<K, V> entry : r2.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
