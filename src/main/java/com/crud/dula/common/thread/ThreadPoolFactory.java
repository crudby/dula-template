package com.crud.dula.common.thread;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 线程池工厂类，用于创建和获取具有指定名称的线程池实例。
 *
 * @author crud
 * @date 2023/9/15
 */
public class ThreadPoolFactory {

    // 存储所有创建的线程池实例，以线程池名称为键
    private static final Map<String, ExecutorService> EXECUTOR_SERVICE_MAP = new ConcurrentHashMap<>();

    // 系统默认线程池名称
    private static final String SYS_THREAD_POOL = "custom-system";

    // 核心线程池大小，等于系统可用处理器数量
    private static final Integer CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    // 最大线程池大小，为核心线程池大小的两倍
    private static final Integer MAXIMUM_POOL_SIZE = CORE_POOL_SIZE << 1;

    // 线程的闲置时间，单位为分钟
    private static final Integer KEEP_ALIVE_TIME = 60;

    // 线程闲置时间的单位
    private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;

    // 队列的最大大小
    private static final Integer QUEUE_SIZE = 2000;

    /**
     * 获取系统默认线程池实例。
     *
     * @return 返回具有系统默认名称的线程池实例。
     */
    public static ExecutorService getInstance() {
        return getInstance(SYS_THREAD_POOL);
    }

    /**
     * 根据指定名称获取线程池实例。
     *
     * @param name 线程池的名称。
     * @return 返回具有指定名称的线程池实例。
     */
    public static ExecutorService getInstance(String name) {
        return getInstance(name, CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, QUEUE_SIZE);
    }

    /**
     * 根据提供的参数创建或获取具有指定名称的线程池实例。
     *
     * @param name            线程池名称。
     * @param corePoolSize    核心线程池大小。
     * @param maximumPoolSize 最大线程池大小。
     * @param keepAliveTime   线程的闲置时间。
     * @param unit            线程闲置时间的单位。
     * @param queueSize       队列的最大大小。
     * @return 返回具有指定配置的线程池实例。
     */
    public static ExecutorService getInstance(String name, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, int queueSize) {
        // 如果尚未创建具有指定名称的线程池
        if (!EXECUTOR_SERVICE_MAP.containsKey(name)) {
            synchronized (ThreadPoolFactory.class) {
                // 双重检查锁定，确保仅创建一个线程池实例
                if (!EXECUTOR_SERVICE_MAP.containsKey(name)) {
                    // 创建线程工厂，用于定制线程名称前缀
                    BasicThreadFactory factory = new BasicThreadFactory.Builder().namingPattern(name + "-%d").build();
                    // 创建并添加新的线程池实例到映射中
                    ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<>(queueSize), factory);
                    EXECUTOR_SERVICE_MAP.put(name, executorService);
                }
            }
        }
        // 返回指定名称的线程池实例
        return EXECUTOR_SERVICE_MAP.get(name);
    }

}

