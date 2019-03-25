package com.riskval.tradeCache.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;

import com.riskval.core.DataSizeConstants;
import com.riskval.util.Logger;
import com.riskval.util.ServiceLogBuilder;
import com.riskval.tradeCache.client.RedissonClient;

public class StressTestRedisson {
	private static Logger logger = ServiceLogBuilder.newBuilder()
			.setLogDirectory(new File("C:\\Users\\Jingxuan\\Desktop\\")).setFilename("Test-RCache")
			.setLogToConsole(false).setMaxNumberOfLogFiles(100).setSizePerLogFile(DataSizeConstants._10_MEGABYTES)
			.build();
	
	private static RedissonClient client = new RedissonClient();
	//private static RedisonServer server = new RedisonServer();
	
	@Test
	public void testConcurrentRedisson() {
		try {
			//server.prepareToday();
			
			List<Thread> threads = new ArrayList<Thread>();

			for (int i = 0; i < 10; i++) {
				threads.add(new Thread(new Runnable() {
					@Override
					public void run() {
						client.readAllToday();
					}
				}, "Reader-" + i));
			}
			
			logger.info("Start");
			threads.forEach((o) -> {
				o.start();
			});
			
			threads.forEach((o) -> {
				try {
					o.join(); // wait this thread to die
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			logger.info("Done");

//			for (Thread t : threads) {
//				t.start();
//			}
//			for (Thread t : threads) {
//				try {
//					t.join(); // wait this thread to die
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
}
