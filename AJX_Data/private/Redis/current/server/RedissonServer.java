package com.riskval.tradeCache.server;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import com.riskval.analytics.RVCalendarFactory;
import com.riskval.analytics.RVDate;
import com.riskval.core.DataSizeConstants;
import com.riskval.tradeserver.client.Trade;
import com.riskval.tradeserver.client.TradeQuery;
import com.riskval.tradeserver.client.TradeServerClient;
import com.riskval.util.Logger;
import com.riskval.util.ServiceLogBuilder;

/**
 * A faster server and should use less resource when lots of reading happened
 * @author Jingxuan
 */
public class RedissonServer {
	private static Logger logger;
	private static RedissonClient redisson;
	private static RReadWriteLock rwlock;
	private static RMap<String, byte[]> rmapToday;
	private static RMap<String, byte[]> rmapLastday;
	
	static {
		logger = ServiceLogBuilder.newBuilder().setLogDirectory(new File("C:\\Users\\Jingxuan\\Desktop\\"))
				.setFilename("New-Server-RCache").setLogToConsole(true).setMaxNumberOfLogFiles(100)
				.setSizePerLogFile(DataSizeConstants._10_MEGABYTES).build();
		
		String configPath = "E:\\ajx\\MyLearningSpot\\Java6_Redis_Gradle\\config\\singleNodeConfig.json";
		try {
			redisson = Redisson.create(Config.fromJSON(new File(configPath)));
			
			rwlock = redisson.getReadWriteLock("RVTradesLock");
			rmapToday = redisson.getMap("RVToday");
			rmapLastday = redisson.getMap("RVLastday");
			
		} catch (IOException e) {
			logger.info("Error about " + e.getMessage());
		}
	}
	
	/**
	 * load trade array data by RVDate
	 * @param RVDate
	 * @return Trade[]
	 */
	private Trade[] loadByRVDate(RVDate day) {
		try {
			TradeServerClient client = TradeServerClient.getSharedInstance();
			TradeQuery query = client.createTradeQueryLean(day.toDate());
			Trade[] trades = query.execute(64000);
			return trades;
		} catch (Exception e) {
			logger.info("Error about " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Write today's trade data into database as HashMap with tradeId.MajorVersion.MinorVersion
	 */
	public void prepareToday() {
		Trade[] trades = loadByRVDate(RVDate.today());
		writeAllToday(trades);
		logger.info("Today's trades wrote to 'RVToday'");
	}

	/**
	 * Write yesterday's trade data into database as HashMap with tradeId.MajorVersion.MinorVersion
	 */
	public void prepareLastday() {
		Trade[] trades = loadByRVDate(RVCalendarFactory.getInstance().getCalendar("Nope").prevWeekDay(RVDate.today()));
		writeAllLastday(trades);
		logger.info("Lastday's trades wrote to 'RVLastday'");
	}
	
	/**
	 * Write one Trade into Redis
	 * @param date 0 means today, 1 means lastday, else is unsupported and would throw IOException
	 * @param trade Trade Object that's going to write.
	 */
	public void writeOne(int date, Trade trade) { 	// maybe do something like this
		try {
			String tradeId = new StringBuilder().append(trade.getTradeId()).append(".").append(trade.getMajorVersion())
					.append(".").append(trade.getMinorVersion()).toString();
			byte[] byteArray = serializeTrade(trade);
			
			rwlock.writeLock().lock();
			
			if (date == 0) {
				rmapToday.fastPut(tradeId, byteArray);
			} else if (date == 1) {
				rmapLastday.fastPut(tradeId, byteArray);
			} else {
				throw new IOException("Not supported.");
			}
			
//			// if-else should be faster in this case.
//			switch (date) {
//			case 0:
//				rmapToday.fastPut(tradeId, byteArray);
//				return;
//			case 1:
//				rmapLastday.fastPut(tradeId, byteArray);
//				return;
//			default:
//				throw new IOException("Not supported.");
//			}
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			rwlock.writeLock().unlock();
			logger.info("Wrote to 'RVToday'");
		}
	}
	
	/**
	 * Write one Trade into Today's Redis cache
	 * @param trade Trade Object that's going to write.
	 */
	public void writeOneToday(Trade trade) {
		try {
			String tradeId = new StringBuilder().append(trade.getTradeId()).append(".").append(trade.getMajorVersion())
					.append(".").append(trade.getMinorVersion()).toString();
			byte[] byteArray = serializeTrade(trade);
			
			rwlock.writeLock().lock();
			
			rmapToday.fastPut(tradeId, byteArray);
			
			logger.info("Wrote to 'RVToday'");
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			rwlock.writeLock().unlock();
		}
	}
	
	/**
	 * Write one Trade into Lastday's Redis cache
	 * @param trade Trade Object that's going to write.
	 */
	public void writeOneLastday(Trade trade) {
		try {
			String tradeId = new StringBuilder().append(trade.getTradeId()).append(".").append(trade.getMajorVersion())
					.append(".").append(trade.getMinorVersion()).toString();
			byte[] byteArray = serializeTrade(trade);
			
			rwlock.writeLock().lock();
			
			rmapLastday.fastPut(tradeId, byteArray);
			
			logger.info("Wrote to 'RVLastday'");
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			rwlock.writeLock().unlock();
		}
	}

	/**
	 * Write all Trade array into Today's Redis cache
	 * @param trades Trade array
	 */
	public void writeAllToday(Trade[] trades) {
		try {
			logger.info("Writelock-LOCK");
			rwlock.writeLock().lock();
			
			long time = System.currentTimeMillis();
			for (int i = 0, N = trades.length; i < N; i++) {
				String tradeId = new StringBuilder().append(trades[i].getTradeId()).append(".")
						.append(trades[i].getMajorVersion()).append(".").append(trades[i].getMinorVersion()).toString();
				byte[] byteArray = serializeTrade(trades[i]);

				rmapToday.fastPut(tradeId, byteArray);
			}
			logger.info("It takes: " + (System.currentTimeMillis() - time) + " to writeAllToday on Server");
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			rwlock.writeLock().unlock();
			logger.info("Writelock-UNLOCK");
		}
	}
	
	/**
	 * Write all Trade array into Lastday's Redis cache
	 * @param trades Trade array
	 */
	public void writeAllLastday(Trade[] trades) {
		try {
			logger.info("Writelock-LOCK");
			rwlock.writeLock().lock();
			
			long time = System.currentTimeMillis();
			for (int i = 0, N = trades.length; i < N; i++) {
				String tradeId = new StringBuilder().append(trades[i].getTradeId()).append(".")
						.append(trades[i].getMajorVersion()).append(".").append(trades[i].getMinorVersion()).toString();
				byte[] byteArray = serializeTrade(trades[i]);

				rmapLastday.fastPut(tradeId, byteArray);
			}
			logger.info("It takes: " + (System.currentTimeMillis() - time) + " to writeAllLastday on Server");
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			rwlock.writeLock().unlock();
			logger.info("Writelock-UNLOCK");
		}
	}
	
	/**
	 * Read one Trade from Today's Redis cache
	 * @param tradeId tradeId.major.minor
	 * @return byte[] of that Trade Object
	 */
	public byte[] readOneToday(String tradeId) {
		try {
			rwlock.readLock().lock();

			logger.info("Read one from 'RVToday'");
			return rmapToday.get(tradeId);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		} finally {
			rwlock.readLock().unlock();
		}
	}
	
	/**
	 * Read one Trade from Lastday's Redis cache
	 * @param tradeId tradeId.major.minor
	 * @return byte[] of that Trade Object
	 */
	public byte[] readOneLastday(String tradeId) {
		try {
			rwlock.readLock().lock();

			logger.info("Read one from 'RVLastday'");
			return rmapLastday.get(tradeId);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		} finally {
			rwlock.readLock().unlock();
		}
	}
	
	/**
	 * Read all Trades from Today's Redis cache
	 * @return Map of tradeId.major.minor and byte[] of that Trade Object
	 */
	public Map<String, byte[]> readAllToday() {
		try {
			logger.info("Readlock-LOCK");
			rwlock.readLock().lock();
			
			long time = System.currentTimeMillis();
			Map<String, byte[]> map = new HashMap<String, byte[]>((int) (rmapToday.size() / 0.75) + 1);
			map.putAll(rmapToday);
			
			logger.info("It takes: " + (System.currentTimeMillis() - time) + " to readAllToday on Server");	
			return map;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		} finally {
			rwlock.readLock().unlock();
			logger.info("Readlock-UNLOCK");
		}
	}
	
	/**
	 * Read all Trades from Lastday's Redis cache
	 * @return Map of tradeId.major.minor and byte[] of that Trade Object
	 */
	public Map<String, byte[]> readAllLastday() {
		try {
			logger.info("Readlock-LOCK");
			rwlock.readLock().lock();
			
			long time = System.currentTimeMillis();
			Map<String, byte[]> map = new HashMap<String, byte[]>((int) (rmapLastday.size() / 0.75) + 1);
			map.putAll(rmapLastday);
			
			logger.info("It takes: " + (System.currentTimeMillis() - time) + " to readAllLastday on Server");
			return map;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		} finally {
			rwlock.readLock().unlock();
			logger.info("Readlock-UNLOCK");
		}
	}

	/**
	 * Serialize Trade Object into byte[]
	 * @param trade
	 * @return byte[]
	 * @throws IOException
	 */
	private static byte[] serializeTrade(Trade trade) throws IOException {
		return trade.toTradeMessage().toByteArray();
	}
}
