package org.ymail.utils;

import lombok.NonNull;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
@Component
public class ThreadPool {
    /**
     * 构建公有线程池，节省资源
     */
    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 20, 2, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(2), new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(r);
            t.setName("coreThread");
            return t;
        }
    }, new ThreadPoolExecutor.AbortPolicy());

    public static void getThread(Runnable c){
        pool.submit(c);
    }
}
