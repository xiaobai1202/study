import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class JedisTest {

    private Jedis jedis;

    @BeforeEach
    public void setUp() {
        jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("123456");
        jedis.select(0);
    }

    @Test
    public void testRedisSetString() {
        String result = jedis.set("key1", "value1");
        System.out.println(result);
        String key1 = jedis.get("key1");
        System.out.println("get key1 =>" + key1);
    }

    @AfterEach
    public void tearDown() {
        if (jedis != null) {
            jedis.close();
        }
    }
}
