package com.southernskillz.verticle;

import com.southernskillz.domain.Location;
import com.southernskillz.repo.LocalStore;
import com.southernskillz.repo.PostalStoreFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Created by michaelmorris on 2017-01-05.
 */
public class RestVerticle extends AbstractVerticle {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private LocalStore ls = LocalStore.getInstance();
    private PostalStoreFactory factory = PostalStoreFactory.getInstance();

    public void start(){

        Router router = Router.router(vertx);

        router.mountSubRouter("/api", apiRouter());
        router.route().failureHandler(errorHandler());
        router.route().handler(staticHandler());
        router.route().handler(BodyHandler.create());

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

    public Router apiRouter(){

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.route().consumes("application/json");
        router.route().produces("application/json");

        //Begin: Route Definitions
        router.get("/lb_healthcheck").handler((RoutingContext context) ->
                context.response().setStatusCode(200).end());

        router.get("/postal-zip/:id").handler((RoutingContext context) ->

                vertx.executeBlocking(
                        future -> {
                            Location loc = factory.findLocation(context.pathParam("id"));
                            future.complete(loc);
                        }
                , false,
                        result -> {
                          if (result.succeeded()){
                              context.response().end(Json.encodePrettily(result.result()));
                          }
                        }));


        router.get("/postal-zip/default/:country").handler((RoutingContext context) -> {

            vertx.executeBlocking(future -> {

                Location loc = factory.findDefaultCity(context.pathParam("country"));
                future.complete(loc);
            },false, result ->{

                if (result.succeeded()){
                    context.response().end(Json.encodePrettily(result.result()));
                }else {
                    context.response().setStatusCode(404).end();
                }
            });

        });

        router.get("/postal-zip/default/block/:country").handler((RoutingContext context) -> {
            Location loc = factory.findDefaultCity(context.pathParam("country"));
            context.response().end(Json.encodePrettily(loc));
        });

        router.get("/postal-zip/default/verticle/:country").handler((RoutingContext context) -> {
            vertx.eventBus()
                    .send("default.city", context.pathParam("country"), result -> {
                      if (result.succeeded()){
                          context.response().end((String) result.result().body());
                      }else{
                          context.response().end();
                      }
                    });
        });
        //End: Route Definitions

        return router;
    }

    public ErrorHandler errorHandler(){

        return ErrorHandler.create();
    }

    public StaticHandler staticHandler(){

        return StaticHandler.create();
    }
}
