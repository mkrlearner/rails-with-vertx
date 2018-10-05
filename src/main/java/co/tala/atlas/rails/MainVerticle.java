package co.tala.atlas.rails;

import co.tala.atlas.rails.verticle.PersonVerticle;
import com.google.gson.Gson;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MainVerticle extends AbstractVerticle {

    @Autowired
    private Gson gson;

    private void getAllPeople(RoutingContext routingContext) {
        vertx.eventBus()
            .<String>send(PersonVerticle.GET_ALL_PEOPLE, "", result -> {
                if (result.succeeded()) {
                    routingContext.response()
                        .putHeader("content-type", "application/json")
                        .setStatusCode(200)
                        .end(result.result()
                            .body());
                } else {
                    routingContext.response()
                        .setStatusCode(500)
                        .end();
                }
            });
    }

    private void getAPerson(RoutingContext routingContext) {
        String personId = routingContext.request().getParam("id");
        vertx.eventBus()
            .<String>send(PersonVerticle.GET_A_PERSON, personId, result -> {
                if (result.succeeded()) {
                    routingContext.response()
                        .putHeader("content-type", "application/json")
                        .setStatusCode(200)
                        .end(result.result()
                            .body());
                } else {
                    routingContext.response()
                        .setStatusCode(500)
                        .end();
                }
            });
    }

    private void renameAPerson(RoutingContext routingContext) {
        Map<String, String> requestDetails = new HashMap<>();
        requestDetails.put("personId", routingContext.request().getParam("id"));
        requestDetails.put("personName", routingContext.getBodyAsString());

        vertx.eventBus()
            .<String>send(PersonVerticle.RENAME_A_PERSON, gson.toJson(requestDetails), result -> {
                if (result.succeeded()) {
                    routingContext.response()
                        .putHeader("content-type", "application/json")
                        .setStatusCode(200)
                        .end(result.result()
                            .body());
                } else {
                    routingContext.response()
                        .setStatusCode(500)
                        .end();
                }
            });
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        super.start();

        Router router = Router.router(vertx);
        router.get("/api/v1/people").handler(this::getAllPeople);
        router.get("/api/v1/people/:id").handler(this::getAPerson);
        router.route().handler(BodyHandler.create());
        router.put("/api/v1/people/:id/name").handler(this::renameAPerson);

        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(config().getInteger("http.port", 8080));
    }
}
