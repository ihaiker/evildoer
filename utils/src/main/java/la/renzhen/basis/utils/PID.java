package la.renzhen.basis.utils;

import java.lang.management.ManagementFactory;

/**
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 1.0 &amp; 2016/3/23 16:59
 */
public class PID {
    public static String get() {
        return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    }
}
