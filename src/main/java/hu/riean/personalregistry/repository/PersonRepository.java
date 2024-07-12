package hu.riean.personalregistry.repository;

import hu.riean.personalregistry.modell.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author riean
 */
public interface  PersonRepository extends CrudRepository<Person, Long> {
    
    Page<Person> findByLastNameContainingIgnoreCaseOrderByLastNameAscFirstNameAsc(String fragment, PageRequest pageRequest);
}
