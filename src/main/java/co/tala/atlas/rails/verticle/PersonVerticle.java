package co.tala.atlas.rails.verticle;

import co.tala.atlas.rails.model.value.PersonName;
import co.tala.atlas.rails.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class PersonVerticle extends BaseVerticle {
    public static final String GET_ALL_PEOPLE = "get.people.all";
    public static final String GET_A_PERSON = "get.person.by.id";
    public static final String RENAME_A_PERSON = "person.rename";

    @Autowired
    private PersonService personService;

    @Override
    List<Pair<String, Function<String, Object>>> getChannelHandlerMap() {
        return Arrays.asList(
            Pair.of(GET_ALL_PEOPLE, getAllPeopleService()),
            Pair.of(GET_A_PERSON, getPerson()),
            Pair.of(RENAME_A_PERSON, renamePerson())
        );
    }

    private Function<String, Object> renamePerson() {
        return msgBody -> {
            Map<String, String> map = gson.fromJson(msgBody, Map.class);
            Long personId = Long.parseLong(map.get("personId"));
            PersonName personName = gson.fromJson(map.get("personName"), PersonName.class);
            return personService.rename(personId, personName);
        };
    }

    private Function<String, Object> getPerson() {
        return msgBody -> Optional.of(msgBody)
            .map(Long::parseLong)
            .map(personService::get)
            .orElse(null);
    }

    private Function<String, Object> getAllPeopleService() {
        return __ -> personService.getAll();
    }
}
