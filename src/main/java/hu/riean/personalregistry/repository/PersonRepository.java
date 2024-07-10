package hu.riean.personalregistry.repository;

import hu.riean.personalregistry.modell.Person;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author riean
 */
public interface  PersonRepository extends CrudRepository<Person, Long> {
    
    Person findById(long id);
}
