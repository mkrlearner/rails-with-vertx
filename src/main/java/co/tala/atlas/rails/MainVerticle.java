package co.tala.atlas.rails;

import co.tala.atlas.rails.verticle.PersonVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class MainVerticle extends AbstractVerticle {

    private void sayHello(RoutingContext routingContext) {
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

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        super.start();

        Router router = Router.router(vertx);
        router.get("/api/hello")
            .handler(this::sayHello);

        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(config().getInteger("http.port", 8080));
    }
}
