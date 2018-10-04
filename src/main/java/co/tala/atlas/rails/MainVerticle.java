package co.tala.atlas.rails;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8080, http -> {
      if (http.succeeded()) {
        startFuture.complete();
        System.out.println("HTTP server started on http://localhost:8080");
      } else {
        startFuture.fail(http.cause());
      }
    });
  }

  public static void main(final String[] args) {
    Launcher.executeCommand("run", MainVerticle.class.getName());
  }
}
