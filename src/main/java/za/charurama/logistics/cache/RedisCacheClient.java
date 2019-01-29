package za.charurama.logistics.cache;

public class RedisCacheClient {
    private static RedisCacheClient instance;
    private RedisCache cache;

    private RedisCacheClient() {
        cache = new RedisCache();
    }

    public static synchronized RedisCacheClient getInstance() {
        if (instance == null) {
            instance = new RedisCacheClient();
        }
        return instance;
    }

    public RedisCache getCache() {
        return cache;
    }

}
