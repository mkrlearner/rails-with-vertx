package co.tala.atlas.rails;

import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = { "co.tala" })
public class VertxSpringApplication {

  @Autowired
  private MainVerticle mainVerticle;

  public static void main(final String[] args) {
    SpringApplication.run(VertxSpringApplication.class, args);
  }

  @PostConstruct
  public void deployVerticle() {
    final Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(mainVerticle);
  }
}
