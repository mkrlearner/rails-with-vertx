package co.tala.atlas.rails;

import co.tala.atlas.rails.verticle.PersonVerticle;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@EnableJpaRepositories("co.tala.atlas.rails.repository")
@EntityScan("co.tala.atlas.rails.model")
@SpringBootApplication
@Configuration
@ComponentScan(basePackages = { "co.tala" })
public class VertxSpringApplication {

    @Autowired
    private MainVerticle mainVerticle;

    @Autowired
    private PersonVerticle personVerticle;

    public static void main(final String[] args) {
        SpringApplication.run(VertxSpringApplication.class, args);
    }

    @PostConstruct
    public void deployVerticle() {
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(mainVerticle);
        vertx.deployVerticle(personVerticle);
    }
}
