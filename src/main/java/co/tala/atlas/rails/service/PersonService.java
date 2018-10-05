package co.tala.atlas.rails.service;

import co.tala.atlas.rails.event.PersonRenamed;
import co.tala.atlas.rails.model.Person;
import co.tala.atlas.rails.model.value.PersonId;
import co.tala.atlas.rails.model.value.PersonName;
import co.tala.atlas.rails.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person get(Long id) {
        return personRepository.withId(new PersonId(id));
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }

    public void destroy(Long id) {
        personRepository.deleteById(new PersonId(id));
    }

    @Transactional
    public Person rename(Long id, PersonName name) {
        if (name == null) return null;
        Person person = get(id);
        if (person == null) return null;
        person.rename(name);
        return personRepository.save(person);
    }

    @EventListener
    public void onApplicationEvent(PersonRenamed renamedEvent) {
    }
}
