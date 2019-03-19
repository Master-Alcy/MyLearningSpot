package Module;

import Base.*;

import java.sql.Timestamp;

import static Base.RedisTradeCacheJedisWithManager.writeToRedis;

public class Producer {

    private final int baseTradeId = 191515;
    private int currTradeId;
    private String name;

    Producer(String name) {
        this.name = name;
    }

    public synchronized void produced(int needNum) {

    }

    private class Writer implements Runnable {
        private String threadName;
        private CustomTrade trade;
        private String key;

        Writer(String name, String key, CustomTrade trade) {
            this.threadName = name;
            this.key = key;
            this.trade = trade;
            MyLogger.ptln("Creating Writer _>>_" + threadName + "_<<_ At: "
                    + new Timestamp(System.currentTimeMillis()));
        }

        public void run() {
            try {
                writeToRedis(trade, key);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            MyLogger.ptln("Finishing Writer _>>_" + threadName + "_<<_ At: "
                    + new Timestamp(System.currentTimeMillis()));
        }
    }
}
