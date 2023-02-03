package repository.cache;

import com.google.gson.Gson;
import java.io.FileInputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import model.resource.RentableItem;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import repository.RentableItemRepository;

public class RentableItemsCache extends RentableItemRepository {

    private final Gson gson = new Gson();
    private JedisPool jedisPool;
    private Jedis jedis;


    public RentableItemsCache() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/java/repository/cache/redis.properties"));
            jedisPool = new JedisPool(properties.getProperty("redis.host"),
                    Integer.parseInt(properties.getProperty("redis.port")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RentableItem add(RentableItem rentableItem) {

        try {
            jedis = jedisPool.getResource();
            String key = String.valueOf(rentableItem.getUuid());
            String value = gson.toJson(rentableItem);
            jedis.set(key, value);
            jedis.expire(key, 60);
            return super.add(rentableItem);
        } catch (Exception e) {
            releaseJedis(jedis);
            return super.add(rentableItem);
        } finally {
            releaseJedis(jedis);
        }
    }

    private void releaseJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    @Override
    public Optional<RentableItem> findByID(UUID id) {
        try {
            jedis = jedisPool.getResource();
            String clientJson = jedis.get(String.valueOf(id));

            if (clientJson != null) {
                return Optional.ofNullable(gson.fromJson(clientJson, RentableItem.class));
            } else {
                return super.findByID(id);
            }

        } catch (Exception e) {
            releaseJedis(jedis);
            return super.findByID(id);
        } finally {
            releaseJedis(jedis);
        }
    }

    @Override
    public void remove(RentableItem rentableItem) {

        try {
            jedis = jedisPool.getResource();
            jedis.del(String.valueOf(rentableItem.getUuid()));
            super.remove(rentableItem);
        } catch (Exception e) {
            releaseJedis(jedis);
            throw new RuntimeException(e);
        } finally {
            releaseJedis(jedis);
        }
    }

    @Override
    public boolean update(RentableItem rentableItem) {
        try {
            jedis = jedisPool.getResource();
            String key = String.valueOf(rentableItem.getUuid());
            String value = gson.toJson(rentableItem);
            jedis.set(key, value);
            jedis.expire(key, 60);
            return super.update(rentableItem);
        } catch (Exception e) {
            releaseJedis(jedis);
            return super.update(rentableItem);
        } finally {
            releaseJedis(jedis);
        }
    }
}
