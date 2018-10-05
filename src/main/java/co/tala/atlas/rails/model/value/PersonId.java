package co.tala.atlas.rails.model.value;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
@ToString
public class PersonId implements Serializable {
    private Long id;
}
