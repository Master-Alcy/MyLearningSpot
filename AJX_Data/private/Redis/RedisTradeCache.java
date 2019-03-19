package com.riskval.tradeserver.client.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.riskval.core.DataSizeConstants;
import com.riskval.resultzip.proto.TradeMessage;
import com.riskval.tradeserver.client.Trade;
import com.riskval.tradeserver.client.TradeServerClient;
import com.riskval.util.Logger;
import com.riskval.util.ServiceLogBuilder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

// ===================================================================
//                        Jedis Test Object
// ===================================================================
class CustomTrade implements Serializable {
	private static final long serialVersionUID = 1234L;
	private String name;
	private int age;

	CustomTrade(String aName, int anAge) {
		this.name = aName;
		this.age = anAge;
	}

	public void something() {
		System.out.println("This is doing something crazy");
	}

	@Override
	public String toString() {
		return "CustomTrade: Name is " + name + ", age is " + age;
	}
} // End of CustomTrade

//===================================================================
//                       My Own Printer
//===================================================================
/** my personal printer */
class MyPrinter {
	/** @param stdout Hold default out */
	private static final PrintStream stdout = System.out;

	/**
	 * This is printing to both console and console.log Note that when print to file
	 * this can't handle newline
	 */
	public static synchronized void ptln(Object str) {
		try {
			System.setOut(stdout);
			System.out.println(str + ""); // Error occurs

			System.setOut(
					new PrintStream(new FileOutputStream("C:\\Users\\Jingxuan\\Desktop\\console.log", true), true));
			System.out.println((new Timestamp(System.currentTimeMillis()) + "\t >>> " + str));

		} catch (Exception e) {
			System.out.println("Error. Wrong Print Type.");
			System.out.println(e.getMessage());
			return;
		}
	}
}

/**
 * @author Jingxuan Ai
 * @director Jingfeng Created on Mar/15/2019
 */
public class RedisTradeCache {

	// ===================================================================
	// Helper for java operates Redis
	// ===================================================================
	private static class JedisPoolManager {
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
			JedisPoolConfig config = new JedisPoolConfig();

			config.setMaxTotal(TestSettings.maxTotal);
			config.setMaxIdle(TestSettings.maxIdle);
			config.setMinIdle(TestSettings.minIdle);
			config.setMaxWaitMillis(TestSettings.maxWaitMillis);
			config.setTestOnBorrow(TestSettings.testOnBorrow);
			config.setTestOnReturn(TestSettings.testOnReturn);

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

//		public void close() {
//			pool.close(); // release resource to pool
//		}

		public void destroy() {
			pool.destroy(); // closing whole app
			logger.info("____________Pool is destroyed___________");
			//System.out.println("____________Pool is destroyed___________");
		}
	} // End this Manager

	// ===================================================================
	// For Jedis Test
	// ===================================================================
	private void jedisTest() {
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
			// System.out.println("Creating " + threadName + " at time: " + creationTime);
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
			// System.out.println("Creating " + threadName + " at time: " + creationTime);
		}

		public void run() {
			try {
				int num = (int) Math.floor(Math.random() * 10000);
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
	// Methods needed for Cache
	// ===================================================================
	private static byte[] serialize(CustomTrade trade) throws IOException {
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

	private static CustomTrade deserialize(byte[] byteArray) throws ClassNotFoundException, IOException {
		ObjectInputStream ois = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
			ois = new ObjectInputStream(bais);

			return (CustomTrade) ois.readObject();

		} finally {
			ois.close();
		}
	}

	private void writeToRedis(CustomTrade trade, String id) throws IOException {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getManager().getResource();
			// jedis.slaveofNoOne();
			byte[] byteArray = serialize(trade);
			jedis.set(id.getBytes(), byteArray);

		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	private CustomTrade readFromRedis(String id) throws ClassNotFoundException, IOException {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getManager().getResource();
			// jedis.slaveof("127.0.0.1", 6379);
			byte[] byteArray = jedis.get(id.getBytes());
			return deserialize(byteArray);

		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	// ===================================================================
	// Methods to load Resources from RiskVal
	// ===================================================================
	private static Logger logger;
	private static TradeServerClient client;
	
	private void tradeRedisCacheTest() {
		logger = ServiceLogBuilder.newBuilder().setLogDirectory(new File("C:\\Users\\Jingxuan\\Desktop\\"))
				.setFilename("Redis-Cache").setLogToConsole(false).setMaxNumberOfLogFiles(100)
				.setSizePerLogFile(DataSizeConstants._10_MEGABYTES).build();
		logger.info("~~~~~~~~~~~~~Test Started~~~~~~~~~~~~~~");
		
		try {
			Trade sample = loadSampleTrade(191515);
			testTimeAndSpace(sample);
			
			logger.info("________________Finished testTimeAndSpace________________");
			System.out.println("Finished testTimeAndSpace");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisPoolManager.getManager().destroy();
		}
	}
	
	private void testTimeAndSpace(final Trade trade) {
		try {
			
			logger.info("---------------Test one write---------------");
			writeTradeToRedis(trade, "testTable1", trade.getTradeId());
			logger.info("---------------Done one write---------------");
			
			// 190k = 190000
			Trade[] tradeArr = new Trade[380000];
			for (int i = 0; i < 380000; i++) {
				tradeArr[i] = trade;
			}
			logger.info("---------------Test 380000 writes---------------");
			writeTradeToRedis(tradeArr, "testTable2");
			logger.info("---------------Done 380000 writes---------------");
			
			logger.info("---------------Test one read on small table---------------");
			readTradeFromRedis("testTable1", trade.getTradeId());
			logger.info("---------------Done one read on small table---------------");
			
			logger.info("---------------Test one read on large table---------------");
			readTradeFromRedis("testTable2", trade.getTradeId());
			logger.info("---------------Done one read on large table---------------");
			
			logger.info("---------------Test full read on large table---------------");
			readTradeFromRedis("testTable2");
			logger.info("---------------Done full read on large table---------------");	
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Trade loadSampleTrade(int tradeId) throws Exception {
		client = TradeServerClient.getSharedInstance();
		return client.loadTrade(tradeId);
	}

	public void writeTradeToRedis(Trade trade, String tableName, int tradeId) throws IOException {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getManager().getResource();
			// jedis.slaveofNoOne();
			ByteBuffer bb = ByteBuffer.allocate(4);
			bb.putInt(tradeId);	
			jedis.hset(tableName.getBytes(), bb.array(), serializeTrade(trade));
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public void writeTradeToRedis(Trade[] trades, String tableName) throws IOException {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getManager().getResource();
			// jedis.slaveofNoOne();
			for (int i = 0; i < trades.length; i++) {
				ByteBuffer bb = ByteBuffer.allocate(4);
				//bb.putInt(trades[i].getTradeId());
				
				//---------This line for test-------------
				bb.putInt(trades[i].getTradeId() + i);
				//---------This line for test-------------
				
				jedis.hset(tableName.getBytes(), bb.array(), serializeTrade(trades[i]));
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public Trade readTradeFromRedis(String tableName, int tradeId) throws ClassNotFoundException, IOException {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getManager().getResource();
			// jedis.slaveof("127.0.0.1", 6379);
			ByteBuffer bb = ByteBuffer.allocate(4);
			bb.putInt(tradeId);	
			byte[] byteArray = jedis.hget(tableName.getBytes(), bb.array());
			return deserializeTrade(byteArray);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public HashMap<Integer, Trade> readTradeFromRedis(String tableName) throws ClassNotFoundException, IOException {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getManager().getResource();
			// jedis.slaveof("127.0.0.1", 6379);
			
			System.out.println("Map Read Start at time: " + new Timestamp(System.currentTimeMillis()));		
			Map<byte[], byte[]> map = jedis.hgetAll(tableName.getBytes());
			System.out.println("Map Read Done at time: " + new Timestamp(System.currentTimeMillis()));
			
			// if size exceed 2^31, around 22 billion
			HashMap<Integer,Trade> result = new HashMap<Integer,Trade>((int) ((map.size() / 0.75) + 1));
			int count = 0;

			for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
				int tradeId = ByteBuffer.wrap(entry.getKey()).getInt();
                Trade trade = deserializeTrade(entry.getValue());
                result.put(tradeId, trade);
                count++;
			}
			System.out.println("We got " + count + " Trades.");
			return result;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Trade[] readTradeArrayFromRedis(String tableName) throws ClassNotFoundException, IOException {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getManager().getResource();
			// jedis.slaveof("127.0.0.1", 6379);
			
			System.out.println("Array Read Start at time: " + new Timestamp(System.currentTimeMillis()));
			List<byte[]> byteArrayList = jedis.hvals(tableName.getBytes());
			System.out.println("Array Read Done at time: " + new Timestamp(System.currentTimeMillis()));
			
			// if size exceed 2^31, around 22 billion
			Trade [] tradeArr = new Trade[byteArrayList.size()];
			int count = 0;
			for (int i = 0; i < byteArrayList.size(); i++) {
				tradeArr[i] = deserializeTrade(byteArrayList.get(i));
                count++;
			}
			System.out.println("We got " + count + " Trades.");
			return tradeArr;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Trade[] readTradeArrayFromRedis(String tableName, int[] tradeIdArray) throws ClassNotFoundException, IOException {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getManager().getResource();
			// jedis.slaveof("127.0.0.1", 6379);
			
			byte[][] query = new byte[tradeIdArray.length][];
	        for (int j = 0; j < tradeIdArray.length; j++) {
	        	ByteBuffer bb = ByteBuffer.allocate(4);
				bb.putInt(tradeIdArray[j]);
				query[j] = bb.array();
	        }
	        
			System.out.println("Array Range Read Start at time: " + new Timestamp(System.currentTimeMillis()));
			List<byte[]> byteArrayList = jedis.hmget(tableName.getBytes(), query);
			//List<byte[]> byteArrayList2 = jedis.hmget(tableName, tradeIdArray);
			System.out.println("Array Range Read Done at time: " + new Timestamp(System.currentTimeMillis()));
			
			// if size exceed 2^31, around 22 billion
			Trade [] tradeArr = new Trade[byteArrayList.size()];
			int count = 0;
			for (int i = 0; i < byteArrayList.size(); i++) {
				tradeArr[i] = deserializeTrade(byteArrayList.get(i));
                count++;
			}
			System.out.println("We got " + count + " Trades.");
			return tradeArr;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	private static byte[] serializeTrade(Trade trade) throws IOException {
		return trade.toTradeMessage().toByteArray();
	}

	private static Trade deserializeTrade(byte[] byteArray) throws ClassNotFoundException, IOException {
		return Trade.fromTradeMessage(TradeMessage.Trade.parseFrom(byteArray));
	}

	public static void main(String[] args) {
		RedisTradeCache rtc = new RedisTradeCache();
		try {
			//rtc.tradeRedisCacheTest();
			
			int[] query = new int[100000];
			for (int i = 0; i < 100000; i++) {
				query[i] = 191515 + i;
			}
			
			System.out.println("Start at time: " + new Timestamp(System.currentTimeMillis()));
			Trade[] result = rtc.readTradeArrayFromRedis("testTable2", query);
			System.out.println("Done at time: " + new Timestamp(System.currentTimeMillis()));
			System.out.println(result.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
