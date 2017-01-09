package com.southernskillz.repo;

import com.southernskillz.domain.Location;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by michaelmorris on 2017-01-09.
 */
public class PostalStoreFactory {


    private static PostalStoreFactory instance = new PostalStoreFactory();
    private LinkedList<Store> storeList = new LinkedList<>();

    private PostalStoreFactory(){

        init();
    }

    private void init(){

        storeList.add(LocalStore.getInstance());
        storeList.add(new RedisStoreStub());
    }
    public static PostalStoreFactory getInstance(){

        return instance;
    }

    public List<Store> getStores(){

        return storeList;
    }

    public Store getCache(){

        //Find the store that is marked as the cache.
        return storeList.stream().findFirst().filter(store -> store.isCacheStore()).get();
    }

    public Location findLocation(String postalCode){

        Location result = null;
        boolean shouldCache = false;
        for (Store store : this.getStores()){

            result = store.get(postalCode);
            shouldCache = !store.isCacheStore();
            if (result != null) {
                break;
            }
        }

        if (shouldCache && result != null){
            this.getCache().put(postalCode, result);
        }
        return result;
    }

    public Location findDefaultCity(String country){

        return this.getCache().getDefaultCity(country);
    }

}
