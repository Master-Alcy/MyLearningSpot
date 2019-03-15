# Redis with Java

**Author:** *Jingxuan Ai*
**Director:** *Jingfeng*
**Start Date:** 3/15/2019

## Requirement V1.0 (3/15)

TradeServerClient => src => com.riskval.tradeserver.client.test => RedisTradeCache

1. Query today's **Trade** (and last WeekDay's)
2. Cache it to Redis
3. Create 10 clients that try to access the Redis Cache

### Thoughts

1. For now, care about today's Trade Data
2. Need to think about that form of data is cached
3. Method would vary if No.2 changed