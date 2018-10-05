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
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonVerticle extends AbstractVerticle {
    public static final String GET_ALL_PEOPLE = "get.people.all";
    public static final String GET_A_PERSON = "get.person.by.id";

    @Autowired
    private Gson gson;

    @Autowired
    private PersonService personService;

    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus().<String>consumer(GET_ALL_PEOPLE).handler(getAllPeopleService());
        vertx.eventBus().<Long>consumer(GET_A_PERSON).handler(getPerson());
    }

    private Handler<Message<Long>> getPerson() {
        return msg -> vertx.<String>executeBlocking(future -> {
            Long personId = msg.body();
            Person person = personService.get(personId);
            future.complete(gson.toJson(person));
        }, result -> {
            if (result.succeeded()) {
                msg.reply(result.result());
            } else {
                msg.reply(result.cause()
                    .toString());
            }
        });
    }

    private Handler<Message<String>> getAllPeopleService() {
        return msg -> vertx.<String>executeBlocking(future -> {
            List<Person> all = personService.getAll();
            String people = gson.toJson(all);
            future.complete(people);
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
