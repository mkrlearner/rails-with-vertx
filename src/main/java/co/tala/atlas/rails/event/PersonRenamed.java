package co.tala.atlas.rails.event;

import co.tala.atlas.rails.model.Person;
import co.tala.atlas.rails.model.value.PersonId;
import co.tala.atlas.rails.model.value.PersonName;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PersonRenamed extends DomainEvent {
    private PersonId personId;
    private PersonName personName;

    public PersonRenamed(Object source, PersonId _personId, PersonName _personName) {
        super(source);
        this.occurredOn = LocalDateTime.now();
        personId = _personId;
        personName = _personName;
    }
}
