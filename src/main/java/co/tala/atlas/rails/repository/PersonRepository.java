package co.tala.atlas.rails.repository;

import co.tala.atlas.rails.model.Person;
import co.tala.atlas.rails.model.value.PersonId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, PersonId> {
    default Person withId(PersonId personId) {
        return findById(personId).orElse(null);
    };
}
