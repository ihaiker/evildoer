package la.renzhen.basis.auto;

import java.io.Serializable;

/**
 * @author zhouhaichao(a)2008.sina.com
 * @version 1.0 &amp; 13:19　2016/1/31
 */
@AutoFactory
public class AGet implements Get,Serializable{
    @Override
    public int get() {
        return 'A';
    }
}
