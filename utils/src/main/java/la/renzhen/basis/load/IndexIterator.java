package la.renzhen.basis.load;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * 数据动态加载，主要解决大批量加载分页内存问题 <p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 25/07/2018 8:30 PM
 */
public class IndexIterator<Index, Out> implements Iterator<Out>, Closeable {

    Index index = null;

    Function<Index, List<Out>> loader;
    Function<Out, Index> lastIndexFn;
    Runnable closeFn;

    List<Out> data = new ArrayList<>();

    public IndexIterator(Function<Index, List<Out>> loader) {
        this(loader, null, () -> {
        });
    }

    public IndexIterator(Function<Index, List<Out>> loader, Function<Out, Index> lastIndex) {
        this(loader, lastIndex, () -> {
        });
    }

    public IndexIterator(Function<Index, List<Out>> loader, Runnable closeFn) {
        this(loader, null, closeFn);
    }

    public IndexIterator(Function<Index, List<Out>> loader, Function<Out, Index> lastIndex, Runnable closeFn) {
        this.loader = loader;
        this.closeFn = closeFn;
        this.lastIndexFn = lastIndex;
    }

    @Override
    public boolean hasNext() {
        if (data.isEmpty()) {
            data = loader.apply(index);
            if (data == null || data.isEmpty()) {
                return false;
            }
        }
        return !data.isEmpty();
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    @Override
    public Out next() {
        Out out = data.remove(0);
        if (lastIndexFn != null) {
            setIndex(lastIndexFn.apply(out));
        }
        return out;
    }

    @Override
    public void remove() {
        throw new RuntimeException("not support");
    }

    @Override
    public void close() throws IOException {
        data.clear();
        if (closeFn != null) {
            closeFn.run();
        }
    }
}
