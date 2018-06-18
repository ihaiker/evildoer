package la.renzhen.basis;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 模拟元祖实现<p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 2017/9/28 下午3:50
 */
public interface Tuple {

    @Data
    public class Pair<A, B> {
        protected A a;
        protected B b;
    }

    @Data
    public class Triplet<A, B, C> {
        protected A a;
        protected B b;
        protected C c;
    }

    @Data
    public class Quartet<A, B, C, D> {
        protected A a;
        protected B b;
        protected C c;
        protected D d;
    }

    public class T {
        public static <A, B> Pair<A, B> pair(A a, B b) {
            Pair<A, B> pair = new Pair<>();
            pair.a = a;
            pair.b = b;
            return pair;
        }

        public static <A, B, C> Triplet<A, B, C> triplet(A a, B b, C c) {
            Triplet<A, B, C> triplet = new Triplet<>();
            triplet.a = a;
            triplet.b = b;
            triplet.c = c;
            return triplet;
        }

        public static <A, B, C, D> Quartet<A, B, C, D> quartet(A a, B b, C c, D d) {
            Quartet<A, B, C, D> quartet = new Quartet<>();
            quartet.a = a;
            quartet.b = b;
            quartet.c = c;
            quartet.d = d;
            return quartet;
        }
    }
}