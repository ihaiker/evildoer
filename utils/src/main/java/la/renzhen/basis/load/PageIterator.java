package la.renzhen.basis.load;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * 数据动态加载，主要解决大批量加载分页内存问题 <p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 25/07/2018 8:30 PM
 */
public class PageIterator<T> implements Iterator<T>, Closeable {

    int page = 0;
    Function<Integer, List<T>> loader;
    Runnable closeFn;

    List<T> data = new ArrayList<>();

    public PageIterator(Function<Integer, List<T>> loader) {
        this(loader, () -> {
        });
    }

    public PageIterator(Function<Integer, List<T>> loader, Runnable closeFn) {
        this.loader = loader;
        this.closeFn = closeFn;
    }

    @Override
    public boolean hasNext() {
        if (data.isEmpty()) {
            data = loader.apply(++page);
            if (data == null) {
                data = Collections.EMPTY_LIST;
            }
        }
        return !data.isEmpty();
    }

    @Override
    public T next() {
        return data.remove(0);
    }

    @Override
    public void remove() {
        throw new RuntimeException("not support");
    }

    @Override
    public void close() throws IOException {
        data.clear();
    }
}
