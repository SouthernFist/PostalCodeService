package com.southernskillz.repo;

import com.southernskillz.domain.LatLong;
import com.southernskillz.domain.Location;

import java.util.HashMap;

/**
 * Created by michaelmorris on 2017-01-06.
 */
public class LocalStore implements Store{

    private static LocalStore ls = new LocalStore();
    private HashMap<String, Location> store = new HashMap<>();

    private LocalStore(){

        init();
    }

    //Load default Cities
    private void init(){

        Location loc = new Location();

        loc.setLatLong(new LatLong(5,6));
        loc.setCity("Florida");
        loc.setPostalZip("12345");
        store.put("US", loc);

        loc = new Location();
        loc.setPostalZip("M6K3R9");
        loc.setCity("Toronto");
        loc.setCountry("Canada");
        loc.setLatLong(new LatLong(4.5, 3.5));
        store.put("M6K3R9",loc);
    }

    public static LocalStore getInstance(){

        return ls;
    }

    public Location get(String locationId){

        return store.get(locationId);
    }

    public void put(String locationId, Location location){

        store.put(locationId, location);
    }

    public Location getDefaultCity(String country){

        return ls.get(country);
    }

    @Override
    public boolean isCacheStore() {
        return true;
    }

}
