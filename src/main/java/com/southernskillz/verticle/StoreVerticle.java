package com.southernskillz.verticle;

import com.southernskillz.repo.PostalStoreFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;

/**
 * Created by michaelmorris on 2017-01-08.
 */
public class StoreVerticle extends AbstractVerticle {

    @Override
    public void start(){

        vertx.eventBus().consumer("default.city").handler(message -> {

            String id = (String) message.body();
            message.reply(Json.encodePrettily(PostalStoreFactory.getInstance().findDefaultCity(id)));
        });
    }
}
