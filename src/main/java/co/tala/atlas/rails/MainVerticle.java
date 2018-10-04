package co.tala.atlas.rails;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class MainVerticle extends AbstractVerticle {

  private void sayHello(RoutingContext routingContext) {
    routingContext.response()
      .putHeader("content-type", "application/json")
      .setStatusCode(200)
      .end("Hello, World!!!!!");
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
