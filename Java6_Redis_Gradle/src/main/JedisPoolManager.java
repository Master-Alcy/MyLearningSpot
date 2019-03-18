import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolManager {
    // volatile make CPU memory sync for all CPUs
    private volatile static JedisPoolManager manager;
    private final JedisPool pool;

    private class TestSettings {
        final static int maxTotal = 20;
        final static int maxIdle = 10;
        final static int minIdle = 1;
        final static long maxWaitMillis = 10000; // 10s
        final static boolean testOnBorrow = true;
        final static boolean testOnReturn = true;
        final static String host = "127.0.0.1";
        final static int port = 6379;
    }

    private JedisPoolManager() {
        System.currentTimeMillis();

        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(TestSettings.maxTotal);
        config.setMaxIdle(TestSettings.maxIdle);
        config.setMinIdle(TestSettings.minIdle);
        config.setMaxWaitMillis(TestSettings.maxWaitMillis);
        config.setTestOnBorrow(TestSettings.testOnBorrow);
        config.setTestOnReturn(TestSettings.testOnBorrow);

        System.out.println("------------Init Jedis Pool------------");
        System.out.println("host: " + TestSettings.host + ", port: " + TestSettings.port);

        pool = new JedisPool(config, TestSettings.host, TestSettings.port);
    }

    public static JedisPoolManager getManager() {
        if (manager == null) {
            synchronized (JedisPoolManager.class) {
                if (manager == null) {
                    manager = new JedisPoolManager();
                }
            }
        }
        return manager;
    }

    public Jedis getResource() {
        return pool.getResource();
    }

    public void close() {
        pool.close(); // release resource to pool
    }

    public void destroy() {
        pool.destroy(); // closing whole app
        System.out.println("____________Pool is destroyed___________");
    }
}