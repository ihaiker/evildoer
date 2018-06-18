package la.renzhen.basis.auto;

import java.lang.annotation.*;

/**
 * 自动产生工厂BEAN<p>
 * 例如：有一个接口
 *<blockquote><pre>
 * public interface Get {
 *     public int get();
 * }
 * \@AutoFactory
 * public class AGet implements Get,Serializable{
 *     public int get() {
 *         return 'A';
 *     }
 * }
 * \@AutoFactory
 * public class BGet implements Get {
 *     public int get() {
 *         return 'B';
 *     }
 * }
 * </pre></blockquote>
 * 会自动生成代码：
 *<blockquote><pre>
 * public class GetFactory {
 *   public static Get get(Worker worker) {
 *     switch(worker){
 *          case A : return new AGet();
 *          case B : return new BGet();
 *         } return new AGet();
 *   }
 *
 *   enum Worker {
 *     A,
 *
 *     B
 *   }
 * }
 *</pre></blockquote>
 * @author zhouhaichao(a)2008.sina.com
 * @version 1.0 &amp; 2016/1/31
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoFactory {
    Class<?> value() default Void.class;
    boolean def() default false;
}
