package com.southernskillz.repo;

import com.southernskillz.domain.Location;

/**
 * Created by michaelmorris on 2017-01-09.
 */
public interface Store {


    public Location get(String id);
    public void put(String id, Location location);
    public Location getDefaultCity(String country);
    public boolean isCacheStore();
}
