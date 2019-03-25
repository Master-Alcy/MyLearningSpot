package com.riskval.tradeCache.client;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.riskval.core.DataSizeConstants;
import com.riskval.resultzip.proto.TradeMessage;
import com.riskval.tradeCache.server.RedisonServer;
import com.riskval.util.ServiceLogBuilder;

import com.riskval.util.Logger;

public class RedissonClient {
	private static RedisonServer server;
	private static Logger logger;
	
	public RedissonClient() {
		logger = ServiceLogBuilder.newBuilder().setLogDirectory(new File("C:\\Users\\Jingxuan\\Desktop\\"))
				.setFilename("Client-RCache").setLogToConsole(true).setMaxNumberOfLogFiles(100)
				.setSizePerLogFile(DataSizeConstants._10_MEGABYTES).build();
		server = new RedisonServer();
	}
	
	public TradeMessage.Trade readOneToday(String tradeId) {
		try {
			// somehow get this Data
			TradeMessage.Trade trade = deserializeTrade(server.readOneToday(tradeId));
			return trade;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public TradeMessage.Trade readOneLastday(String tradeId) {
		try {
			// somehow get this Data
			TradeMessage.Trade trade = deserializeTrade(server.readOneLastday(tradeId));
			return trade;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<String, TradeMessage.Trade> readAllToday() {
		long time = System.currentTimeMillis();
		
		Map<String, byte[]> serverMap = server.readAllToday();
		Map<String, TradeMessage.Trade> map = new HashMap<String, TradeMessage.Trade>((int) (serverMap.size() / 0.75) + 1);
		
		try {
			for (Entry<String, byte[]> entry : serverMap.entrySet()) {
				map.put(entry.getKey(), deserializeTrade(entry.getValue()));	
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		logger.info("It takes: " + (System.currentTimeMillis() - time) + " to readAllToday on Client");
		return map;
	}
	
	public Map<String, TradeMessage.Trade> readAllLastDay() {
		Map<String, byte[]> serverMap = server.readAllLastday();
		Map<String, TradeMessage.Trade> map = new HashMap<String, TradeMessage.Trade>((int) (serverMap.size() / 0.75) + 1);
		
		try {
			for (Entry<String, byte[]> entry : serverMap.entrySet()) {
				map.put(entry.getKey(), deserializeTrade(entry.getValue()));	
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		return map;
	}
	
	private static TradeMessage.Trade deserializeTrade(byte[] byteArray) throws ClassNotFoundException, IOException {
		return TradeMessage.Trade.parseFrom(byteArray);
	}
}
