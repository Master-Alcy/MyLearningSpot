public class Producer {

    private static final int max_write_times = 100;
    private int curr_times;

    Producer(int curr) {
        this.curr_times = curr;
    }

    public synchronized void produced(int needNum) {

    }

    public class RunnableProducer implements Runnable {
        private String threadName;
        private String redisId;
        private long creationTime;

        RunnableProducer(String name, String id) {
            this.threadName = name;
            this.redisId = id;
            this.creationTime = System.currentTimeMillis();
            //System.out.println("Creating " + threadName + " at time: " + creationTime);
        }

        public void run() {
            try {
                CustomTrade trade = RedisTradeCacheJedisWithManager.readFromRedis(redisId);
                MyLogger.ptln(trade.toString());
            } catch (Exception e) {
                MyLogger.ptln(e.getMessage());
                e.printStackTrace();
            }
//            System.out.println("Thread " + this.threadName + " exiting after: "
//                    + (System.currentTimeMillis() - creationTime));
        }
    }
}
