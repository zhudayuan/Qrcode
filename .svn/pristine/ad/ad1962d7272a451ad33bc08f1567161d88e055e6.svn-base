package com.maywidehb.qrpush.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
public class RedisConfig {

    private static Logger logger = Logger.getLogger(RedisConfig.class);

    public static JedisPool jpool = null;
    private static JedisPoolConfig config = getRedisConfig();

    static {
        try{
            if(jpool == null){
//                jpool = new JedisPool(config,CF.redis.host,CF.redis.port,CF.redis.timeout);
                jpool = new JedisPool(config,CF.redis.host,CF.redis.port,CF.redis.timeout,CF.redis.password);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static Jedis getJedis(){
        try{
            if(jpool != null){
                return jpool.getResource();
            }else{
                jpool = new JedisPool(config,CF.redis.host,CF.redis.port,CF.redis.timeout,CF.redis.password);
                return jpool.getResource();
            }
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static void closeJedis(Jedis jedis){
        try{
            if(jedis != null){
                jedis.close();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
//    public static void returnResource(Jedis jedis){
//        try{
//            if(jpool != null){
//                jpool.returnBrokenResource(jedis);
//            }
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//    }
    public static JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(CF.redis.minIdle);
        config.setMaxIdle(CF.redis.maxIdle);
        config.setMaxWaitMillis(CF.redis.maxWait);
        config.setMaxTotal(CF.redis.maxTotal);
        config.setTestOnBorrow(true);
        return config;
    }


}
