import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

public class RedisTradeCacheJedis {

    public static void main(String[] args) {
        System.out.println("Init=================================");
        RedisTradeCacheJedis rtc = new RedisTradeCacheJedis();
        rtc.javaRedisServerProcessor();
    }

    public void javaRedisServerProcessor() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(20); // maximum active connections
        poolConfig.setMaxIdle(10);  // maximum idle connections

        JedisPool pool = new JedisPool(poolConfig, "localhost");

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            /// ... do stuff here ... for example
            jedis.set("foo", "bar");
            String foobar = jedis.get("foo");
            jedis.zadd("sose", 0, "car");
            jedis.zadd("sose", 0, "bike");
            Set<String> sose = jedis.zrange("sose", 0, -1);

        } finally {
            // You have to close jedis object. If you don't close then
            // it doesn't release back to pool and you can't get a new
            // resource from pool.
            if (jedis != null) {
                jedis.close();
                System.out.println("This instance closed");
            }
        }

/// ... when closing your application:
        pool.close();
        System.out.println("Whole pool closed");
    }
}
