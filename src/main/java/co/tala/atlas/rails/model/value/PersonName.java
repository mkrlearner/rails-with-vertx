package co.tala.atlas.rails.model.value;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@ToString
public class PersonName {
    @NotNull
    @Size(max = 40)
    private String firstName;

    @Size(max = 40)
    private String middleName;

    @NotNull
    @Size(max = 40)
    private String lastName;

    public PersonName(String _firstName, String _middleName, String _lastName) {
        if (_firstName == null) throw new IllegalStateException("firstName should not be null");
        if (_lastName == null) throw new IllegalStateException("lastName should not be null");
        firstName = _firstName;
        middleName = _middleName;
        lastName = _lastName;
    }

    public PersonName(String _firstName, String _lastName) {
        this(_firstName, null, _lastName);
    }
}
