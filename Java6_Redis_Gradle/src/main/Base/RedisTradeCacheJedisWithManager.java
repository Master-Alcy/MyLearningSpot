package Base;

import redis.clients.jedis.Jedis;

import java.io.*;
import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RedisTradeCacheJedisWithManager {

    public static void main(String[] args) {
        //Base.MyLogger.ptln("Test manager=================================");
        RedisTradeCacheJedisWithManager redisManager = new RedisTradeCacheJedisWithManager();
        redisManager.hashGetRedisTest();
    }

    private void hashGetRedisTest() {
        System.out.println("Start with time: " + new Timestamp(System.currentTimeMillis()));
        Jedis jedis = null;
        try {
            jedis = JedisPoolManager.getManager().getResource();

            Map<byte[], byte[]> map = jedis.hgetAll("HashData".getBytes());
            System.out.println("GotAll at time: " + new Timestamp(System.currentTimeMillis()));

            int count = 0;
            for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
                String key = new String(entry.getKey(), "UTF-8");
                CustomTrade value = deserialize(entry.getValue());

                if (key.equals("Nope")) {
                    System.out.println("Nope");
                }
                if (value.toString().equals("Nay")) {
                    System.out.println("Nay");
                }
                count++;
                //Base.MyLogger.ptln("Field: " + key + " With: " + value.toString());
            }
            System.out.println("Counted " + count + " Objects.");
            System.out.println("Done at time: " + new Timestamp(System.currentTimeMillis()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            JedisPoolManager.getManager().destroy();
        }
    }

    private void hashSetRedisTest() {
        System.out.println("Start with time: " + new Timestamp(System.currentTimeMillis()));
        Jedis jedis = null;
        try {
            jedis = JedisPoolManager.getManager().getResource();

            for (int i = 0; i < 200000; i++) {
                jedis.hset("HashData".getBytes(),
                        ("Test" + i).getBytes(),
                        serialize(
                                new CustomTrade(
                                        ("Trade" + i),
                                        ((int) Math.floor(Math.random() * 10000))
                                )
                        )
                );
            }
            System.out.println("Done at time: " + new Timestamp(System.currentTimeMillis()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            JedisPoolManager.getManager().destroy();
        }
    }

    // ===================================================================
    // ===================================================================
    public void jedisTest() {
        MyLogger.ptln("+++++++++Custom Test++++++++++++++");
        CustomTrade day1 = new CustomTrade("day1", 111);

        try {

            writeToRedis(day1, "id_2");
            CustomTrade get1 = readFromRedis("id_2");
            get1.something();
            MyLogger.ptln(get1.toString());

            MyLogger.ptln("My Multi Testing ~~~~~~~~~~~~~~~~~~~~~~~");
            ExecutorService es = Executors.newCachedThreadPool();
            CustomTrade get2 = readFromRedis("id_2");
            MyLogger.ptln("Old data: " + get2.toString());

            for (int i = 0; i < 20; i++) {
                es.execute(new RunnableTestWriter("writer_" + i, "id_2"));
                es.execute(new RunnableTestReader("reader_" + i, "id_2"));
            }
            es.shutdown();
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);

            if (finished) {
                MyLogger.ptln("All done");
            } else {
                MyLogger.ptln("Not finished.");
            }

        } catch (Exception e) {
            MyLogger.ptln("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // closing app
            JedisPoolManager.getManager().destroy();
        }
    }

    private class RunnableTestReader implements Runnable {
        private String threadName;
        private String redisId;
        private long creationTime;

        RunnableTestReader(String name, String id) {
            this.threadName = name;
            this.redisId = id;
            this.creationTime = System.currentTimeMillis();
            //System.out.println("Creating " + threadName + " at time: " + creationTime);
        }

        public void run() {
            try {
                CustomTrade trade = readFromRedis(redisId);
                MyLogger.ptln(trade.toString());
            } catch (Exception e) {
                MyLogger.ptln(e.getMessage());
                e.printStackTrace();
            }
//            System.out.println("Thread " + this.threadName + " exiting after: "
//                    + (System.currentTimeMillis() - creationTime));
        }
    }

    private class RunnableTestWriter implements Runnable {
        private String threadName;
        private String redisId;
        private long creationTime;

        RunnableTestWriter(String name, String id) {
            this.threadName = name;
            this.redisId = id;
            this.creationTime = System.currentTimeMillis();
            //System.out.println("Creating " + threadName + " at time: " + creationTime);
        }

        public void run() {
            try {
                int num = (int) Math.floor(Math.random() * 10000);
                CustomTrade newTrade = new CustomTrade("NewTrade", num);
                writeToRedis(newTrade, redisId);
                MyLogger.ptln("Finished Writing this: " + num);
            } catch (Exception e) {
                MyLogger.ptln(e.getMessage());
                e.printStackTrace();
            }
//            System.out.println("Thread " + this.threadName + " exiting after: "
//                    + (System.currentTimeMillis() - creationTime));
        }
    }

    // ===================================================================
    // ===================================================================
    public static byte[] serialize(CustomTrade trade) throws IOException {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(trade);
            oos.flush();
            return baos.toByteArray();

        } finally {
            oos.close();
            baos.close();
        }
    }

    public static CustomTrade deserialize(byte[] byteArray)
            throws ClassNotFoundException, IOException {
        ObjectInputStream ois = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
            ois = new ObjectInputStream(bais);
            return (CustomTrade) ois.readObject();

        } finally {
            ois.close();
        }
    }

    public static void writeToRedis(CustomTrade trade, String id) throws IOException {
        Jedis jedis = null;
        try {
            jedis = JedisPoolManager.getManager().getResource();
            //jedis.slaveofNoOne();
            byte[] byteArray = serialize(trade);
            jedis.set(id.getBytes(), byteArray);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static CustomTrade readFromRedis(String id) throws ClassNotFoundException, IOException {
        Jedis jedis = null;
        try {
            jedis = JedisPoolManager.getManager().getResource();
            //jedis.slaveof("127.0.0.1", 6379);
            byte[] byteArray = jedis.get(id.getBytes());
            return deserialize(byteArray);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}