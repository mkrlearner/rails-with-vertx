package co.tala.atlas.rails.model;

import co.tala.atlas.rails.event.PersonRenamed;
import co.tala.atlas.rails.model.value.PersonId;
import co.tala.atlas.rails.model.value.PersonName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;

@Data
@Entity
@Table(name = "people")
public class Person extends AbstractAggregateRoot {

    @EmbeddedId
    @AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "id"))
    })
    @JsonUnwrapped
    private PersonId personId;

    @Embedded
    @AttributeOverrides(value = {
        @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
        @AttributeOverride(name = "middleName", column = @Column(name = "middle_name")),
        @AttributeOverride(name = "lastName", column = @Column(name = "last_name"))
    })
    @JsonUnwrapped
    private PersonName name;

    public void rename(PersonName p) {
        this.setName(p.copy());
        registerEvent(new PersonRenamed(this, this.getPersonId(), this.getName()));
    }
}
