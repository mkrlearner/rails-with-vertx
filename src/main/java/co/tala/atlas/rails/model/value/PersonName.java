package co.tala.atlas.rails.model.value;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@ToString
public class PersonName {
    private String firstName, middleName, lastName;

    public PersonName(String _firstName, String _middleName, String _lastName) {
        if (_firstName == null) throw new IllegalStateException("firstName should not be null");
        if (_lastName == null) throw new IllegalStateException("lastName should not be null");
        firstName = _firstName;
        middleName = _middleName;
        lastName = _lastName;
    }

    public PersonName copy() {
        return new PersonName(firstName, middleName, lastName);
    }

}
