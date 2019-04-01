package com.riskval.tradeCache.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.redisson.Redisson;
import org.redisson.api.RMap;
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

public class RedisonServer {
	private static Logger logger;
	private static RedissonClient redisson;
	private static ReentrantReadWriteLock lockRW = new ReentrantReadWriteLock();
	
	public RedisonServer() {
		logger = ServiceLogBuilder.newBuilder().setLogDirectory(new File("C:\\Users\\Jingxuan\\Desktop\\"))
				.setFilename("Server-RCache").setLogToConsole(true).setMaxNumberOfLogFiles(100)
				.setSizePerLogFile(DataSizeConstants._10_MEGABYTES).build();

		// this could load from elsewhere
		String configPath = "E:\\ajx\\MyLearningSpot\\Java6_Redis_Gradle\\config\\singleNodeConfig.json";
		loadRedisson(configPath);
		
//		try {
//			runServer(); // ??
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	private void runServer() throws IOException {
		ServerSocket serverSocket = new ServerSocket(5555);

		while (true) {
			Socket socket = null;
			try {
				// socket object to receive incomming client requests
				socket = serverSocket.accept();
				logger.info("A new client is connected: " + socket);

				// obtaining input and out streams
				DataInputStream dataIn = new DataInputStream(socket.getInputStream());
				DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
				
				//Thread t = new ClientHandler(socket, dataIn, dataOut);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void loadRedisson(String path) {
		try {
			redisson = Redisson.create(Config.fromJSON(new File(path)));
		} catch (IOException e) {
			logger.info("Error about " + e.getMessage());
		}
	}
	
//	private void closeServer() {
//		redisson.shutdown();
//	}

	public void prepareToday() {
		Trade[] trades = loadByRVDate(RVDate.today());
		writeAllToday(trades);
		logger.info("Today's trades wrote to 'RVToday'");
	}

	public void prepareLastday() {
		Trade[] trades = loadByRVDate(RVCalendarFactory.getInstance().getCalendar("Nope").prevWeekDay(RVDate.today()));
		writeAllLastday(trades);
		logger.info("Lastday's trades wrote to 'RVLastday'");
	}

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
	
	public void writeOneToday(Trade trade) {
		writeOne("RVToday", trade);
		logger.info("Wrote to 'RVToday'");
	}
	
	public void writeOneLastday(Trade trade) {
		writeOne("RVLastday", trade);
		logger.info("Wrote to 'RVLastday'");
	}

	private void writeOne(String table, Trade trade) {
		try {
			String tradeId = new StringBuilder().append(trade.getTradeId()).append(".").append(trade.getMajorVersion())
					.append(".").append(trade.getMinorVersion()).toString();
			byte[] byteArray = serializeTrade(trade);
			lockRW.writeLock().lock();
			RMap<String, byte[]> rmap = redisson.getMap(table);

			rmap.fastPut(tradeId, byteArray);
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			lockRW.writeLock().unlock();
		}
	}
	
	public void writeAllToday(Trade[] trades) {
		writeAll("RVToday", trades);
		logger.info("Wrote all to 'RVToday'");
	}
	
	public void writeAllLastday(Trade[] trades) {
		writeAll("RVLastday", trades);
		logger.info("Wrote all to 'RVLastday'");
	}

	private void writeAll(String table, Trade[] trades) {
		try {
			lockRW.writeLock().lock();
			RMap<String, byte[]> rmap = redisson.getMap(table);

			// this could be break into different threads to speed up
			for (int i = 0, N = trades.length; i < N; i++) {
				String tradeId = new StringBuilder().append(trades[i].getTradeId()).append(".")
						.append(trades[i].getMajorVersion()).append(".").append(trades[i].getMinorVersion()).toString();
				byte[] byteArray = serializeTrade(trades[i]);

				rmap.fastPut(tradeId, byteArray);
			}
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			lockRW.writeLock().unlock();
		}
	}

	
	public byte[] readOneToday(String tradeId) {
		byte[] byarr = readOne("RVToday", tradeId);
		logger.info("Read one from 'RVToday'");
		return byarr;
	}
	
	public byte[] readOneLastday(String tradeId) {
		byte[] byarr = readOne("RVLastday", tradeId);
		logger.info("Read one from 'RVLastday'");
		return byarr;
	}
	
	private byte[] readOne(String table, String tradeId) {
		try {
			lockRW.readLock().lock();

			RMap<String, byte[]> rmap = redisson.getMap(table);
			return rmap.get(tradeId);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		} finally {
			lockRW.readLock().unlock();
		}
	}
	
	public Map<String, byte[]> readAllToday() {
		Map<String, byte[]> map = readAll("RVToday");
		logger.info("Read all from 'RVToday'");
		return map;
	}
	
	public Map<String, byte[]> readAllLastday() {
		Map<String, byte[]> map = readAll("RVLastday");
		logger.info("Read all from 'RVLastday'");
		return map;
	}

	private Map<String, byte[]> readAll(String table) {
		try {
			logger.info("Readlock-LOCK");
			lockRW.readLock().lock();
			
			long time = System.currentTimeMillis();
			RMap<String, byte[]> rmap = redisson.getMap(table);
			Map<String, byte[]> map = new HashMap<String, byte[]>((int) (rmap.size() / 0.75) + 1);
			map.putAll(rmap);
			logger.info("It takes: " + (System.currentTimeMillis() - time) + " to readAll on Server");
			
			return map;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		} finally {
			lockRW.readLock().unlock();
			logger.info("Readlock-UNLOCK");
		}
	}

	private static byte[] serializeTrade(Trade trade) throws IOException {
		return trade.toTradeMessage().toByteArray();
	}
	
	public static void main(String[] args) {
		RedisonServer rs = new RedisonServer();
		
		
	}
}
