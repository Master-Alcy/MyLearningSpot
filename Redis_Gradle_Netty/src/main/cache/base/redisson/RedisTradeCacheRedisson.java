package cache.base.redisson;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;

/**
 * After some research, I found that Redisson is mostly for distributed systems,
 * which needs Pro version.
 */
public class RedisTradeCacheRedisson {

    public static void main(String[] args) {
        System.out.println("Init=================================");
        RedisTradeCacheRedisson rtc = new RedisTradeCacheRedisson();
        rtc.javaRedisServerProcessor();
    }

    public void javaRedisServerProcessor() {
        RedissonClient redisson = null;
        try {
            String configPath = "config\\singleNodeConfig.json";
            Config config = Config.fromJSON(new File(configPath));
            redisson = Redisson.create(config);

            Test(redisson);

        } catch (IOException ex) {
            System.err.println("Something went wrong.");
            ex.printStackTrace();
        } finally {
            redisson.shutdown();
        }
    }

    private void Test(RedissonClient redisson) {
        //get redis's key-value pair，key's existence doesn't matter
        RBucket<String> keyObject = redisson.getBucket("key");
        //if exists, set value to "value"
        //if not exists, set value to "value"
        keyObject.set("value");
    }
}
