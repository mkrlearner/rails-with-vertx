package co.tala.atlas.rails;

import co.tala.atlas.rails.verticle.PersonVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class MainVerticle extends AbstractVerticle {

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
        Long personId = Long.parseLong(routingContext.request().getParam("id"));
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

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        super.start();

        Router router = Router.router(vertx);
        router.get("/api/v1/people").handler(this::getAllPeople);
        router.get("/api/v1/people/:id").handler(this::getAPerson);

        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(config().getInteger("http.port", 8080));
    }
}
