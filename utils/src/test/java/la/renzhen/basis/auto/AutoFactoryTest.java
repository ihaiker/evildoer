package la.renzhen.basis.auto;

import org.junit.Test;

/**
 * @author zhouhaichao(a)2008.sina.com
 * @version 1.0 &amp; 14:08　2016/1/31
 */
public class AutoFactoryTest {

    @Test
    public void testAuto(){
        Get get = GetFactory.get(GetFactory.Worker.A);

        System.out.println(get.get());
    }
}
