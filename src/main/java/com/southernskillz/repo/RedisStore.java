package com.southernskillz.repo;

import com.southernskillz.domain.Location;
import redis.clients.jedis.Jedis;

/**
 * Created by michaelmorris on 2017-01-09.
 */
public class RedisStore implements Store {

    public void init(){

        Jedis jedis = new Jedis();

    }

    @Override
    public Location get(String id) {
        return null;
    }

    @Override
    public void put(String id, Location location) {

    }

    @Override
    public Location getDefaultCity(String country) {
        return null;
    }

    @Override
    public boolean isCacheStore() {
        return false;
    }
}
