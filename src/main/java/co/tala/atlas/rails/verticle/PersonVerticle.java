package co.tala.atlas.rails.verticle;

import co.tala.atlas.rails.model.Person;
import co.tala.atlas.rails.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonVerticle extends AbstractVerticle {
    public static final String GET_ALL_PEOPLE = "get.people.all";

    @Autowired
    private Gson gson;

    @Autowired
    private PersonService personService;

    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus()
            .<String>consumer(GET_ALL_PEOPLE)
            .handler(getAllPeopleService(personService));
    }

    private Handler<Message<String>> getAllPeopleService(PersonService service) {
        return msg -> vertx.<String>executeBlocking(future -> {
            try {
                List<Person> all = service.getAll();
                String people = gson.toJson(all);
                future.complete(people);
            } catch (Exception e) {
                System.out.println("Failed to serialize result");
                future.fail(e);
            }
        }, result -> {
            if (result.succeeded()) {
                msg.reply(result.result());
            } else {
                msg.reply(result.cause()
                    .toString());
            }
        });
    }
}
