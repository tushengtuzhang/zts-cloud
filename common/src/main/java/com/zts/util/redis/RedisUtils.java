package com.zts.util.redis;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangtusheng
 */
@Component
public class RedisUtils<K, T> {

    @Resource
    private RedisTemplate redisTemplate;

    public T get(K key) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    public void save(K key, T value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    public void save(K key, T value, long offset) {
        redisTemplate.boundValueOps(key).set(value, offset);
    }

    public void save(K key, T value, long timeout, TimeUnit unit) {
        redisTemplate.boundValueOps(key).set(value, timeout, unit);
    }

    public void delete(K key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param timeout
     * @param unit
     */
    public void expire(K key, long timeout, TimeUnit unit) {
        redisTemplate.boundValueOps(key).expire(timeout, unit);
    }

    public Object keys(String patten) {
        return redisTemplate.keys(patten + "*");
    }

    public <T> void saveList(String key, List<T> dataList) {
        ListOperations operations = cacheList(key, dataList);
    }

    public <K, T> void saveList(K key, List<T> dataList, long timeout, TimeUnit unit) {
        ListOperations operations = cacheList(key, dataList);
        operations.getOperations().expire(key, timeout, unit);
    }

    private <K, T> ListOperations cacheList(K key, List<T> dataList) {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                listOperation.rightPush(key, dataList.get(i));
            }
        }
        return listOperation;
    }

    public <K> void deleteList(K key) {
        ListOperations listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);
        listOperation.remove(key, size, null);
    }

    public <K, T> List<T> getList(K key) {
        List<T> dataList = new ArrayList<T>();
        ListOperations<K, T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for (int i = 0; i < size; i++) {
            dataList.add((T) listOperation.leftPop(key));
        }

        return dataList;
    }

    public <K, T> void saveSet(K key, Set<T> dataSet) {
        cacheSet(key, dataSet);
    }

    public <K, T> void saveSet(K key, Set<T> dataSet, long timeout, TimeUnit timeUnit) {
        BoundSetOperations operations = cacheSet(key, dataSet);
        operations.getOperations().expire(key, timeout, timeUnit);
    }

    private <K, T> BoundSetOperations<K, T> cacheSet(K key, Set<T> dataSet) {
        BoundSetOperations<K, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    public <K> void deleteSet(K key) {
        BoundSetOperations setOperation = redisTemplate.boundSetOps(key);
        setOperation.remove(setOperation.members());
    }

    public <K, T> Set<T> getSet(K key) {
        Set<T> dataSet = new HashSet<T>();
        BoundSetOperations<K, T> operation = redisTemplate.boundSetOps(key);
        Long size = operation.size();
        for (int i = 0; i < size; i++) {
            dataSet.add(operation.pop());
        }
        return dataSet;
    }
}
