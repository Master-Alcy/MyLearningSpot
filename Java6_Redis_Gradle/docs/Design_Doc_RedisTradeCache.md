# Redis with Java

**Author:** *Jingxuan Ai*
**Director:** *Jingfeng*
**Start Date:** 3/15/2019

## Requirement V1.0 (3/15)

TradeServerClient => src => com.riskval.tradeserver.client.test => RedisTradeCacheRedisson

1. Query today's **Trade** (and last WeekDay's)
2. Cache it to Redis
3. Create 10 clients that try to access the Redis Cache

### Thoughts

1. Redis Java Client Choice:
   1. Redisson vs Jedis: <https://stackoverflow.com/questions/42250951/redisson-vs-jedis-for-redis>
   2. Choose Redisson for it's enhanced support in multi-threading
   3. Jedis is too light weight, and require user to design themselves.
2. For now, care about today's Trade Data
3. Need to think about that form of data is cached
4. Method would vary if No.2 changed