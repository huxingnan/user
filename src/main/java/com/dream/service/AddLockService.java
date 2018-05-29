package com.dream.service;

/**
 * @author huxingnan
 * @date 2018/5/21 13:33
 */
public interface AddLockService {
    /**
     * 加锁
     * @param redisKey key
     * @param i i秒之后失效
     * @return 1 成功 -1 失败 -2 失败
     */
    int addLock(String redisKey, int i);

    /**
     * 清除锁
     * @param redisKey key
     */
    void clearLock(String redisKey);
}
