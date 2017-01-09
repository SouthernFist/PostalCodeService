package com.southernskillz.launch;

import com.southernskillz.domain.LatLong;
import com.southernskillz.domain.Location;
import com.southernskillz.repo.LocalStore;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Created by michaelmorris on 2017-01-05.
 */
public class BaseLauncher extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(BaseLauncher.class);

    public void start(){

        DeploymentOptions options = new DeploymentOptions();
        options.setInstances(4);
        vertx.deployVerticle("com.southernskillz.verticle.RestVerticle",options, (AsyncResult<String> result) ->{
            if (result.failed()){
                logger.info("Deploy Failed");
            }else {
                logger.info("Deploy Suceeded");
            }
        });

        options = new DeploymentOptions();
        options.setWorker(true).setWorkerPoolSize(400);
        vertx.deployVerticle("com.southernskillz.verticle.StoreVerticle",options, (AsyncResult<String> result) ->{
            if (result.failed()){
                logger.info("Deploy Failed");
            }else {
                logger.info("Deploy Suceeded");
            }
        });
    }

    public static void main(String... args){


        Vertx vertx = Vertx.vertx();
        DeploymentOptions options = new DeploymentOptions();
        options.setInstances(4);
        vertx.deployVerticle("com.southernskillz.verticle.RestVerticle",options, (AsyncResult<String> result) ->{
            if (result.failed()){
                logger.info("Deploy Failed");
            }else {
                logger.info("Deploy Suceeded");
            }
        });
    }
}
