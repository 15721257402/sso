package com.tritonsfs.cac.sso.util;

import com.tritonsfs.cac.util.common.string.StringUtils;
import org.springframework.data.redis.core.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisUtil {


    private static RedisTemplate redisTemplate;

    public static void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    /**
     * 根据key获取redis值
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 放入redis
     *
     * @param key
     * @param value
     */
    public static void set(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 放入redis并设置失效时间，单位秒
     *
     * @param key
     * @param value
     * @param timeout
     */
    public static void setWithTime(Object key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 将数据放入缓存中  并发相同key无法同时写入  并且设置key失效时间  设置失败删除key
     * @param key
     * @param value
     * @param timeout
     */
    public static boolean setIfAbsent(Object key, Object value, long timeout) {
        boolean absentFlage = redisTemplate.opsForValue().setIfAbsent(key, value);
        if(absentFlage){
            boolean expireFlage=redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            if(!expireFlage){
                redisTemplate.delete(key);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 存放指定下标的数据进list
     * @description setList
     * @author 佛祖保佑后的最
     * @param [key,index,value]
     * @return void
     * @time 2018/4/10
     */
    public static void setList(Object key, long index, Object value) {
        redisTemplate.opsForList().set(key,index,value);
    }

    /**
     * 从右边插入数据进list
     * @description rightPushList
     * @author 佛祖保佑后的最
     * @param [key,value]
     * @return void
     * @time 2018/4/10
     */
    public static void rightPushList(Object key, Object value) {
        redisTemplate.opsForList().rightPush(key,value);
    }

    /**
     * 获得从左到右指定下标范围内的list
     * start=0&&end=-1时取出所有
     * @description range
     * @author 佛祖保佑后的最
     * @param [key, start, end]
     * @return java.util.List<java.lang.Object>
     * @time 2018/4/11
     */
    public static List<Object> range(Object key, long start, long end) {
        return redisTemplate.opsForList().range(key,start,end);
    }

    /**
     * 存放hash进redis
     * @description setList
     * @author 佛祖保佑后的最
     * @param [key,index,value]
     * @return void
     * @time 2018/4/10
     */
    public static void setHash(Object key, Object field, Object value) {
        redisTemplate.opsForHash().put(key,field,value);
    }

    /**
     * 根据key和field取出对应的值
     * @description getHash
     * @author 佛祖保佑后的最
     * @param [key, hashKey]
     * @return void
     * @time 2018/4/11
     */
    public static Object getHash(Object key, Object field) {
        return redisTemplate.opsForHash().get(key,field);
    }

    /**
     * 设置失效时间，单位秒
     *
     * @param key
     * @param timeout
     * @return
     */
    public static boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }
    
    /**
     * 设置失效时间，单位天
     *
     * @param key
     * @param timeout
     * @return
     */
    public static boolean expireDay(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.DAYS);
    }

    /**
     * 根据key获取失效时间，单位秒
     *
     * @param key
     * @return
     */
    public static long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key值是否存在
     *
     * @param key
     * @return
     */

    public static boolean hashKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据key删除redis值
     *
     * @param key
     */
    public static void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            }
        } else {
            redisTemplate.delete(Arrays.asList(key));
        }
    }

    public static void deleteKeyLike(String patternBefore,String key,String patternAfter) {
        if(!StringUtils.isEmpty(key)){
            if(!StringUtils.isEmpty(patternBefore)){
                key=patternBefore+key;
            }
            if(!StringUtils.isEmpty(patternAfter)){
                key=key+patternAfter;
            }
            Set keys = redisTemplate.keys(key);
            redisTemplate.delete(keys);
        }

    }

    /**
     * redis值递增,long型
     *
     * @param key
     * @param delta
     * @return
     */
    public static long incrementLong(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * redis值递增,double型
     *
     * @param key
     * @param delta
     * @return
     */
    public static double incrementDouble(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 获取HashOperations
     *
     * @return
     */
    public static HashOperations getHashOp() {
        return redisTemplate.opsForHash();
    }

    /**
     * 获取ClusterOperations
     *
     * @return
     */
    public static ClusterOperations getClusterOp() {
        return redisTemplate.opsForCluster();
    }

    /**
     * 获取HyperLogLogOperations
     *
     * @return
     */
    public static HyperLogLogOperations getHyperOp() {
        return redisTemplate.opsForHyperLogLog();
    }

    /**
     * 获取ListOperations
     *
     * @return
     */
    public static ListOperations getListOp() {
        return redisTemplate.opsForList();
    }

    /**
     * 获取SetOperations
     *
     * @return
     */
    public static SetOperations getSetOp() {
        return redisTemplate.opsForSet();
    }

    /**
     * 获取ValueOperations
     *
     * @return
     */
    public static ValueOperations getValueOp() {
        return redisTemplate.opsForValue();
    }

    /**
     * 获取ZSetOperations
     *
     * @return
     */
    public static ZSetOperations getZSetOp() {
        return redisTemplate.opsForZSet();
    }

}
