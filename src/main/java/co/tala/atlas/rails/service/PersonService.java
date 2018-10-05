package co.tala.atlas.rails.service;

import co.tala.atlas.rails.model.Person;
import co.tala.atlas.rails.model.value.PersonId;
import co.tala.atlas.rails.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person get(Long id) {
        return personRepository.findById(new PersonId(id)).orElse(null);
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }

    public void destroy(Long id) {
        personRepository.deleteById(new PersonId(id));
    }

    public void update(Long id, Person p) {
        if (p == null) return;
        Person person = get(id);
        if (person == null) return;
        person.rename(p);
        personRepository.save(person);
    }
}
