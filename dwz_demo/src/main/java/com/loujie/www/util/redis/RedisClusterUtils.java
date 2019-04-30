package com.loujie.www.util.redis;

import redis.clients.jedis.JedisCluster;

import java.util.List;

public class RedisClusterUtils {

    // 获取redis连接
    private static final JedisCluster jedisCluster = RedisClusterResource.getJedisCluster();

    public static class STRING {
        /**
         * 设置key-value到redis中
         *
         * @param key   键
         * @param value 值
         * @return
         */
        public static boolean set(String key, String value) {
            String result = jedisCluster.set(key, value);
            return "Ok".equalsIgnoreCase(result) ? true : false;
        }

        /**
         * 设置key-value到redis中
         *
         * @param key     键
         * @param value   值
         * @param seconds 秒
         * @return
         */
        public static boolean set(String key, String value, int seconds) {
            String result = jedisCluster.set(key, value);
            jedisCluster.expire(key, seconds);
            return "Ok".equalsIgnoreCase(result) ? true : false;
        }

        /**
         * 设置key-value到redis中,不过对象是一个List,然后里面会一个一个的遍历,这样可以减少连接池的关闭
         *
         * @param <T>
         * @param callbackKey 生成键的对象
         * @param values      值列表
         * @param seconds     键的有效时长
         * @return
         */
        public static <T> boolean set(ICallbackKey<T> callbackKey, List<T> values, int seconds) {
            for (T itemT : values) {
                String key = callbackKey.getKey(itemT);
                jedisCluster.set(key, callbackKey.getPojoToObjectService().toString(itemT));
                jedisCluster.expire(key, seconds);
            }
            return true;
        }

        /**
         * 获取redis中键的值
         *
         * @param key 键
         * @return
         */
        public static String get(String key) {
            return jedisCluster.get(key);
        }

        /**
         * 对指定的键递增计数
         *
         * @param key
         * @return 返回计数后的结果
         */
        public static Long incr(final String key) {
            return jedisCluster.incr(key);
        }
    }

    public static class HASH {

        public static boolean hdel(String key, String field) {
            jedisCluster.hdel(key, field);
            return true;
        }

        public static boolean hset(String key, String field, String value) {
            jedisCluster.hset(key, field, value);
            return true;
        }

        public static String hget(String key, String field) {
            return jedisCluster.hget(key, field);
        }

    }

    public static class KEYS {
        /**
         * 删除一个键
         *
         * @param key
         * @return
         */
        public static boolean del(String key) {
            jedisCluster.del(key);
            return true;
        }

        /**
         * Redis缓存中是否存在指定的键?
         *
         * @param key
         * @return
         */
        public static boolean exists(final String key) {
            return jedisCluster.exists(key);
        }

        /**
         * 对指定的键设定过期时间
         *
         * @param key
         * @param seconds 过期时长(以秒为单位)
         * @return 返回计数后的结果
         */
        public static Long expire(final String key, final int seconds) {
            return jedisCluster.expire(key, seconds);
        }

        /**
         * 获取某一个键值的时效
         *
         * @param key 键
         * @return
         */
        public static Long ttl(final String key) {
            return jedisCluster.ttl(key);
        }

    }

}
