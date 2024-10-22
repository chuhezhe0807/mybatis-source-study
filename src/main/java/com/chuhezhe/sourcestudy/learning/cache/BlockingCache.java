package com.chuhezhe.sourcestudy.learning.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheException;

/**
 * 阻塞式缓存
 *   阻塞式缓存和阻塞队列不同，阻塞队列的特点是：
 *      1、调用enQueue入队方法时，如果队列已满，则阻塞等待
 *      2、调用deQueue出队方法时，如果队列为空，则阻塞等待
 *   阻塞式缓存的特点：
 *      1、调用get方法获取同一个key时，需要进行阻塞等待（阻塞式缓存装饰器使用CountDownLatch实现了get方法的阻塞）
 *
 *
 */
public class BlockingCache implements Cache {

    private long timeout; // 超时时间
    private final Cache delegate;
    private final ConcurrentHashMap<Object, CountDownLatch> locks;

    public BlockingCache(Cache delegate) {
        this.delegate = delegate;
        this.locks = new ConcurrentHashMap<>();
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public int getSize() {
        return delegate.getSize();
    }

    // 添加缓存，从DB查询之后会执行的一个操作(调用过 getObject、acquireLock 方法，locks中存有key对应的CountDownLatch)，
    // 不可以直接调用此方法，否则会在 releaseLock 方法中抛出异常
    @Override
    public void putObject(Object key, Object value) {
        try {
            delegate.putObject(key, value);
        } finally {
            releaseLock(key);
        }
    }

    @Override
    public Object getObject(Object key) {
        // 获取锁，让其等待
        acquireLock(key);
        // 可能有IO操作
        Object value = delegate.getObject(key);
        if (value != null) {
            // 如果获取了对应的value，解锁
            releaseLock(key);
        }
        return value;
    }

    @Override
    public Object removeObject(Object key) {
        // despite its name, this method is called only to release locks
        releaseLock(key);
        return null;
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    // 针对多个线程同时访问同一个key的防止缓存击穿处理
    private void acquireLock(Object key) {
        // 创建一个 CountDownLatch 步长为1
        CountDownLatch newLatch = new CountDownLatch(1);
        while (true) {
            // putIfAbsent 如果key对应的obj不存在，则使用newLatch并返回null，若存在则修改，并返回之前的值
            CountDownLatch latch = locks.putIfAbsent(key, newLatch);
            if (latch == null) {
                break;
            }
            try {
                if (timeout > 0) {
                    boolean acquired = latch.await(timeout, TimeUnit.MILLISECONDS);
                    if (!acquired) {
                        throw new CacheException(
                                "Couldn't get a lock in " + timeout + " for the key " + key + " at the cache " + delegate.getId());
                    }
                } else { // 没有设置超时时间，就一直等待
                    // 使用的前一个线程的latch在等待
                    latch.await();
                }
            } catch (InterruptedException e) {
                throw new CacheException("Got interrupted while trying to acquire lock for key " + key, e);
            }
        }
    }

    private void releaseLock(Object key) {
        // 从locks中移除key对应的 CountDownLatch
        CountDownLatch latch = locks.remove(key);
        if (latch == null) { // 检测到试图释放未得到的锁，正常流程来说不应该发生。除非主动调用 putObject 前未向locks中存放key对应的CountDownLatch
            throw new IllegalStateException("Detected an attempt at releasing unacquired lock. This should never happen.");
        }
        // 执行完成这个latch(步长为1)
        latch.countDown();
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
