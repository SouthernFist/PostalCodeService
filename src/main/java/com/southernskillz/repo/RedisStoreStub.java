package com.southernskillz.repo;

import com.southernskillz.domain.LatLong;
import com.southernskillz.domain.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michaelmorris on 2017-01-09.
 */
public class RedisStoreStub implements Store {

    //simulated redis store
    private Map<String, Location> store = new HashMap<>();


    public RedisStoreStub(){

        init();
    }

    //Load default Cities
    private void init(){

        Location loc = new Location();

        loc.setLatLong(new LatLong(5,6));
        loc.setCity("San Fran");
        loc.setPostalZip("12345");
        store.put("US", loc);

        loc = new Location();
        loc.setPostalZip("M6K3R8");
        loc.setCity("Toronto");
        loc.setCountry("Canada");
        loc.setLatLong(new LatLong(4.4, 3.5));
        store.put("M6K3R8",loc);
    }

    @Override
    public Location get(String id) {

        try {
            //Simulate slowness
            Thread.sleep((int) (Math.random()*100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return store.get(id);
    }

    @Override
    public void put(String id, Location location) {

        store.put(id, location);
    }

    @Override
    public Location getDefaultCity(String country) {

        try {
            //Simulate slowness
            Thread.sleep((int) (Math.random()*100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return store.get(country);
    }

    @Override
    public boolean isCacheStore() {
        return false;
    }
}
