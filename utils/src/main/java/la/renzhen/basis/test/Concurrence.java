package la.renzhen.basis.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 用于测试并发问题的测试咧<p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 29/01/2018 8:22 PM
 */
public class Concurrence {
    /**
     * 运行线程池
     */
    ExecutorService service;

    /**
     * 运行的测试类
     */
    List<Callable<Object>> runnable;

    int concurrency;

    int mode;

    public Concurrence(int concurrency) {
        this.concurrency = concurrency;
        service = Executors.newFixedThreadPool(concurrency);
        runnable = new ArrayList<>(concurrency);
    }

    /**
     * 相同接口的竞争测试
     *
     * @param runnable
     */
    public void one(Callable<Object> runnable) {
        this.runnable.add(runnable);
        mode = 1;
    }

    /**
     * 不同接口测试咧
     *
     * @param runnable 运行测试类
     */
    public void multiple(Callable<Object>... runnable) {
        mode = 2;
        assert runnable.length == concurrency;
        for (Callable<Object> callable : runnable) {
            this.runnable.add(callable);
        }
    }

    public List<Object> test() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        List<Future<Object>> results = new ArrayList<>();
        if (mode == 1) {//单接口测试
            for (int i = 0; i < this.concurrency; i++) {
                Future<Object> future = service.submit(() -> {
                    try {
                        latch.await();
                        return this.runnable.get(0).call();
                    } catch (Throwable e) {
                        return e;
                    }
                });
                results.add(future);
            }
        } else {
            this.runnable.forEach(run -> {
                Future<Object> future = service.submit(() -> {
                    try {
                        latch.await();
                        return run.call();
                    } catch (Throwable e) {
                        return e;
                    }
                });
                results.add(future);
            });
        }
        latch.countDown();

        try {
            return results.stream().map(f -> {
                try {
                    return f.get();
                } catch (Exception e) {
                    return e;
                }
            }).collect(Collectors.toList());
        } finally {
            service.shutdownNow();
        }
    }
}