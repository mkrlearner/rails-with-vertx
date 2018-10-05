package co.tala.atlas.rails.model;

import co.tala.atlas.rails.model.value.PersonName;
import co.tala.atlas.rails.model.value.PersonId;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "people")
public class Person {
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

    public void rename(Person p) {
        this.setName(p.getName());
    }
}
