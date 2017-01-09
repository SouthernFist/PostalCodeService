import com.southernskillz.domain.Location;
import com.southernskillz.repo.PostalStoreFactory;
import com.southernskillz.repo.Store;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

/**
 * Created by michaelmorris on 2017-01-08.
 */
public class Rubbish {



    public void randomOverhead(){

        long time = 0;

        for (int i=0; i<10; i++){

            time = (int) (Math.random()*1000);
            System.out.println(time);
        }
    }

    @Test
    public void ordered(){

        PostalStoreFactory factory = PostalStoreFactory.getInstance();

        Location result = null;
        boolean shouldCache = false;
        for (Store store : factory.getStores()){

            result = store.get("M6K3R8");
            shouldCache = !store.isCacheStore();
            if (result != null) {
                break;
            }
        }

        if (shouldCache && result != null){
            factory.getCache().put("M6K3R8", result);
        }

        assertTrue(factory.getCache().get("M6K3R8") != null);
    }


    public void jedis(){

        Jedis instance = new Jedis("192.168.99.100", 32768);
        System.out.println(instance.ping());
        instance.set("M6K3R9", "{}");


    }
}
