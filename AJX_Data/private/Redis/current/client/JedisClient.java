package com.riskval.tradeCache.client;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.riskval.analytics.RVDate;
import com.riskval.core.DataSizeConstants;
import com.riskval.resultzip.proto.TradeMessage;
import com.riskval.tradeserver.client.Trade;
import com.riskval.tradeserver.client.TradeQuery;
import com.riskval.tradeserver.client.TradeServerClient;
import com.riskval.util.Logger;
import com.riskval.util.ServiceLogBuilder;
import com.riskval.tradeCache.server.JedisServer.JedisPoolManager;

import redis.clients.jedis.Jedis;

public class JedisClient {
	private static Logger logger;
	private static TradeServerClient client;

	private Trade[][] loadSampleTrade() throws Exception {
		client = TradeServerClient.getSharedInstance();

		TradeQuery query1 = client.createTradeQueryLean(RVDate.today().toDate());
		Trade[] tradeToday = query1.execute(64000);

//		TradeQuery query2 = client.createTradeQueryLean(RVCalendarFactory.getInstance().getCalendar("Nope").prevWeekDay(RVDate.today()).toDate());
//		Trade[] tradeLastWeekDay = query2.execute(64000);

		Trade[][] result = new Trade[2][];
		result[0] = tradeToday;
		result[1] = null;

		return result;
	}

	private void tradeRedisCacheTest() {
		logger = ServiceLogBuilder.newBuilder().setLogDirectory(new File("C:\\Users\\Jingxuan\\Desktop\\"))
				.setFilename("Redis-Cache").setLogToConsole(false).setMaxNumberOfLogFiles(100)
				.setSizePerLogFile(DataSizeConstants._10_MEGABYTES).build();
		logger.info("~~~~~~~~~~~~~Test Started~~~~~~~~~~~~~~");

		try {
			// final Trade[][] samples = loadSampleTrade();

//			logger.info("++++++++++++++++testTimeAndSpace Started++++++++++++++++");
//			testTimeAndSpace(samples[0]);
//			logger.info("________________Finished testTimeAndSpace________________");

			logger.info("++++++++++++++++testConcurrentModule Started++++++++++++++++");
			testConcurrentModule(null);
			logger.info("________________Finished testConcurrentModule________________");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisPoolManager.getManager().destroy();
		}
		System.out.println("All Tests Finished");
	}

	private void testTimeAndSpace(final Trade[] trades) {
		try {
			// 190k = 190000

//			int[] query = new int[190000];
//			for (int i = 0; i < 190000; i++) {
//				query[i] = 191515 + i;
//			}

			logger.info("---------------Test one write---------------");
			writeTradeToRedis("testTable1", trades[0]);
			logger.info("---------------Done one write---------------");

			logger.info("---------------Test today's writes---------------");
			writeTradeToRedis("testTable2", trades);
			logger.info("---------------Done today's writes---------------");

//			logger.info("---------------Test one read on small table---------------");
//			readTradeFromRedis("testTable1", trade.getTradeId());
//			logger.info("---------------Done one read on small table---------------");
//
//			logger.info("---------------Test one read on large table---------------");
//			readTradeFromRedis("testTable2", trade.getTradeId());
//			logger.info("---------------Done one read on large table---------------");
//
//			logger.info("---------------Test full read 1 on large table---------------");
//			readTradeFromRedis("testTable2");
//			logger.info("---------------Done full read 1 on large table---------------");
//
//			logger.info("---------------Test full read 2 on large table---------------");
//			readTradeArrayFromRedis("testTable2");
//			logger.info("---------------Done full read 2 on large table---------------");
//
//			logger.info("---------------Test 100k read on large table---------------");
//			readTradeArrayFromRedis("testTable2", query);
//			logger.info("---------------Done 100k read on large table---------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ===================================================================
	// Production - Consumer Test Module TODO 3
	// ===================================================================
	private void testConcurrentModule(final Trade[] trades) {
		try {

//			ExecutorService es = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
//					new SynchronousQueue<Runnable>());
			ExecutorService es = Executors.newCachedThreadPool();

			// es.execute(new Writer(("W>_" + "1" + "_<W"), "testTable2", trades));
			for (int i = 0; i < 20; i++) {
				es.execute(new Reader(("R>_" + i + "_<R"), "testTable2"));
				// es.execute(new Writer(("W>_" + i + "_<W"), "testTable2", update));
			}
			es.shutdown();
			boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
			if (finished) {
				System.out.println("All done");
			} else {
				System.out.println("Not finished.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class Reader implements Runnable {
		private String threadName;
		// private int[] tradeIdArray;
		private String tableName;

		Reader(String name, String tableName) {
			this.threadName = name;
			this.tableName = tableName;
			logger.info("Creating Reader " + threadName);
		}

		public void run() {
			try {
				long start = System.currentTimeMillis();
				TradeMessage.Trade[] trades = readTradeArrayFromRedis("testTable2");
				logger.info("Finishing Reader " + threadName + " took " + (System.currentTimeMillis() - start)
						+ " to read " + trades.length);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private class Writer implements Runnable {
		private String threadName;
		private Trade[] trades;
		private String tableName;

		Writer(String name, String tableName, Trade[] trades) {
			this.threadName = name;
			this.tableName = tableName;
			this.trades = trades;
			logger.info("Creating Writer " + threadName);
		}

		public void run() {
			try {
				long start = System.currentTimeMillis();
				writeTradeToRedis(tableName, trades);
				logger.info("Finishing Writer " + threadName + " took " + (System.currentTimeMillis() - start)
						+ " to read " + trades.length);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	// ===================================================================
	// Redis Cache Utils TODO 4
	// ===================================================================
	public void writeTradeToRedis(String tableName, Trade trade) throws IOException {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getManager().getResource();
			// jedis.slaveofNoOne();

			String tradeId = new StringBuilder().append(trade.getTradeId()).append(".").append(trade.getMajorVersion())
					.append(".").append(trade.getMinorVersion()).toString();

			jedis.hset(tableName.getBytes(), tradeId.getBytes(), serializeTrade(trade));
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void writeTradeToRedis(String tableName, Trade[] trades) throws IOException {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getManager().getResource();
			// jedis.slaveofNoOne();

			for (int i = 0; i < trades.length; i++) {
				String tradeId = new StringBuilder().append(trades[i].getTradeId()).append(".")
						.append(trades[i].getMajorVersion()).append(".").append(trades[i].getMinorVersion()).toString();

				jedis.hset(tableName.getBytes(), tradeId.getBytes(), serializeTrade(trades[i]));
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public TradeMessage.Trade readTradeFromRedis(String tableName, String tradeId)
			throws ClassNotFoundException, IOException {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getManager().getResource();
			// jedis.slaveof("127.0.0.1", 6379);

			byte[] byteArray = jedis.hget(tableName.getBytes(), tradeId.getBytes());

			return deserializeTrade(byteArray);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public HashMap<String, TradeMessage.Trade> readTradeHashFromRedis(String tableName)
			throws ClassNotFoundException, IOException {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getManager().getResource();
			// jedis.slaveof("127.0.0.1", 6379);

			long start = System.currentTimeMillis();
			Map<byte[], byte[]> map = jedis.hgetAll(tableName.getBytes());
			System.out.println("Redis: hgetAll, tooks " + (System.currentTimeMillis() - start));

			HashMap<String, TradeMessage.Trade> result = new HashMap<String, TradeMessage.Trade>(
					(int) ((map.size() / 0.75) + 1));
			for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
				String tradeId = entry.getKey().toString();
				TradeMessage.Trade trade = deserializeTrade(entry.getValue());
				result.put(tradeId, trade);
			}

			return result;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public TradeMessage.Trade[] readTradeArrayFromRedis(String tableName) throws ClassNotFoundException, IOException {
		Jedis jedis = null;
		try {
			jedis = JedisPoolManager.getManager().getResource();
			// jedis.slaveof("127.0.0.1", 6379);

			long start = System.currentTimeMillis();
			List<byte[]> byteArrayList = jedis.hvals(tableName.getBytes());
			System.out.println("Redis: hvals, tooks " + (System.currentTimeMillis() - start));

			TradeMessage.Trade[] tradeArr = new TradeMessage.Trade[byteArrayList.size()];
			for (int i = 0; i < byteArrayList.size(); i++) {
				tradeArr[i] = deserializeTrade(byteArrayList.get(i));
			}

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

	private static TradeMessage.Trade deserializeTrade(byte[] byteArray) throws ClassNotFoundException, IOException {
		return TradeMessage.Trade.parseFrom(byteArray);
	}

}
