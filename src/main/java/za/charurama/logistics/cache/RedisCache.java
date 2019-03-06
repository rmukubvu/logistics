package za.charurama.logistics.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by robson on 2017/03/04.
 */
public class RedisCache {

    private Jedis _jedis;
    private static final String CHANNEL_REDIS = "chimbetu";
    private ObjectMapper mapper = new ObjectMapper();

    public RedisCache(){
        _jedis = new Jedis("localhost");
    }

    public <T> void setItem(String key, T value){
        set(key, value, 0);
    }

    public <T> void setItem(String key, T value, int expireSeconds){
        set(key, value, expireSeconds);
    }

    private <T> void set(String key, T value, int expireSeconds) {
        try {
            _jedis.set(key,mapper.writeValueAsString(value));
            if (expireSeconds > 0)
                _jedis.expire(key,expireSeconds);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public <T> void refreshValue(String key,T value){
        if ( _jedis.exists(key) )
            removeFromCache(key);
        setItem(key,value,0);
    }

    private String get(String key) {
       if ( !_jedis.exists(key) ) return null;
        return _jedis.get(key);
    }

    public <T> T getItem(String key,Class<T> responseClass){
        String jsonArray = get(key);
        try {
            if (jsonArray != null)
                return mapper.readValue(jsonArray, responseClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T[] getItems(String key,Class<T[]> responseClass){
        String jsonArray = get(key);
        try {
            return mapper.readValue(jsonArray, responseClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> getItemsAsList(String key, Class<T[]> responseClass){
        String jsonArray = get(key);
        try {
            if (jsonArray != null) {
                T[] array = mapper.readValue(jsonArray, responseClass);
                return Arrays.asList(array);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setAreaCoordinates(String key, double longitude, double latitude, String member){
        try {
            _jedis.geoadd(key, longitude, latitude, member);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //public void getArea

    public void removeFromCache(String key) {
        _jedis.del(key);
    }

    public void sendMessage(String message){
        _jedis.publish(CHANNEL_REDIS,message);
    }

    public void dispose() {
       if (_jedis != null)
           _jedis.close();
    }
}
