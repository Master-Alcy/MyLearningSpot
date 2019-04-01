package com.riskval.tradeCache.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import com.riskval.core.DataSizeConstants;
import com.riskval.util.Logger;
import com.riskval.util.ServiceLogBuilder;
import com.riskval.tradeCache.client.RedissonClient;
import com.riskval.tradeCache.server.RedissonServer;

public class StressTestRedisson {
	private static Logger logger = ServiceLogBuilder.newBuilder()
			.setLogDirectory(new File("C:\\Users\\Jingxuan\\Desktop\\")).setFilename("Test-RCache")
			.setLogToConsole(false).setMaxNumberOfLogFiles(100).setSizePerLogFile(DataSizeConstants._10_MEGABYTES)
			.build();
	
	private static RedissonClient client = new RedissonClient();
	private static RedissonServer server = new RedissonServer();
	
	/*
	@Test
	public void testRedissonServer() {
		logger.info("Start 1");
		try {		
			List<Thread> threads = new ArrayList<Thread>();
			
			threads.add(new Thread(new Runnable() {
				@Override
				public void run() {
					server.prepareLastday();
				}
			}, "Writer-1"));
			
			for (int i = 0; i < 10; i++) {
				threads.add(new Thread(new Runnable() {
					@Override
					public void run() {
						try { // wait for writer to lock
							TimeUnit.SECONDS.sleep(4L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						client.readAllToday();
					}
				}, "Reader-" + i));	
			}
			
			threads.forEach((o) -> { o.start(); });
			
			threads.forEach((o) -> {
				try {
					o.join(); // wait this thread to die
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			
			logger.info("Done 1");	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}*/
	
	@Test
	public void testNewRedissonServer() {
		logger.info("Start 2");
		try {		
			List<Thread> threads = new ArrayList<Thread>();
			
			threads.add(new Thread(new Runnable() {
				@Override
				public void run() {
					server.prepareLastday();
				}
			}, "Writer-1"));
			
			for (int i = 0; i < 10; i++) {
				threads.add(new Thread(new Runnable() {
					@Override
					public void run() {
//						try { // wait for writer to lock
//							TimeUnit.SECONDS.sleep(4L);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
						client.readAllToday();
					}
				}, "Reader-" + i));
			} // end loop
			
			threads.forEach((o) -> { o.start(); });
			
			threads.forEach((o) -> {
				try {
					o.join(); // wait this thread to die
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			
			logger.info("Done 2");	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
}
