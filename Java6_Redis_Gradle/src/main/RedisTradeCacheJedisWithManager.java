import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
 
public class RedisTradeCacheJedisWithManager {

    public static void main(String[] args) {
        System.out.println("Test manager=================================");
        RedisTradeCacheJedisWithManager redisManager = new RedisTradeCacheJedisWithManager();
        redisManager.jedisTest();
    }

    public void jedisTest() {
        System.out.println("+++++++++Custom Test++++++++++++++");
        CustomTrade day1 = new CustomTrade("day1", 111);

        try {

            writeToRedis(day1, "id_2");
            CustomTrade get1 = readFromRedis("id_2");
            get1.something();
            System.out.println(get1.toString());

            System.out.println("My Multi Testing ~~~~~~~~~~~~~~~~~~~~~~~");
            ExecutorService es = Executors.newCachedThreadPool();
            CustomTrade get2 = readFromRedis("id_2");
            System.out.println("Old data: " + get2.toString());

            for (int i = 0; i < 20; i++) {
                es.execute(new RunnableTestWriter("writer_" + i, "id_2"));
                es.execute(new RunnableTestReader("reader_" + i, "id_2"));
            }
            es.shutdown();
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);

            if (finished) {
                System.out.println("All done");
            } else {
                System.out.println("Not finished.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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
                System.out.println(trade.toString());
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
                int num =  (int)Math.floor(Math.random() * 10000);
                CustomTrade newTrade = new CustomTrade("NewTrade", num);
                writeToRedis(newTrade, redisId);
                System.out.println("Finished Writing this: " + num);
            } catch (Exception e) {
                System.out.println(e.getMessage());
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

    public void writeToRedis(CustomTrade trade, String id) throws IOException {
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

    public CustomTrade readFromRedis(String id) throws ClassNotFoundException, IOException {
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